package com.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {
	
	private Selector selector = null;
	private ServerSocketChannel svSocketChannel = null;
	private int port = 8000;
	private Charset charset = Charset.forName("GBK");
	public EchoServer() throws IOException
	{
		selector = Selector.open();
		svSocketChannel = ServerSocketChannel.open();
		svSocketChannel.socket().setReuseAddress(true);
		svSocketChannel.bind(new InetSocketAddress(port));
		System.out.println("start the Server...");
	}
	private Object gate = new Object();
	public void accept(){
		for(;;)
		{
			try{
				SocketChannel socketChannel = svSocketChannel.accept();
				System.out.println("receive from the client:"+socketChannel.socket().getInetAddress()+
						":"+socketChannel.socket().getPort());
				socketChannel.configureBlocking(false);
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				synchronized(gate)
				{
					selector.wakeup();
					socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE,buffer);					
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void service() throws IOException{
		for(;;){
			synchronized(gate){}
			int n = selector.select();
			if(n==0) continue;
			Set readyKeys = selector.selectedKeys();
			Iterator it = readyKeys.iterator();
			while(it.hasNext()){
				SelectionKey key = null;
				key = (SelectionKey)it.next();
				it.remove();
				if(key.isReadable()){
					receive(key);
				}
				if(key.isWritable())
					send(key);
			}
		}
			
	}
	
	public void send(SelectionKey key) throws IOException{
		ByteBuffer buffer=(ByteBuffer)key.attachment();
		SocketChannel socketChannel = (SocketChannel)key.channel();
		buffer.flip();
		String data = decode(buffer);
		if(data.indexOf("\r\n")==-1)
			return;
		String outputData = data.substring(0,data.indexOf("\n")+1);
		System.out.println(outputData);
		ByteBuffer outputBuffer = encode("echo:"+outputData);
		while(outputBuffer.hasRemaining())
			socketChannel.write(outputBuffer);
		ByteBuffer temp = encode(outputData);
		buffer.position(temp.limit());
		buffer.compact();
		if(outputData.equals("byr\r\n"))
		{
			key.cancel();
			socketChannel.close();
			System.out.println("close the server");
		}
	}
	
	public void receive(SelectionKey key) throws IOException{
		ByteBuffer buffer=(ByteBuffer)key.attachment();
		SocketChannel socketChannel = (SocketChannel)key.channel();
		ByteBuffer readBuffer = ByteBuffer.allocate(32);
		socketChannel.read(readBuffer);
		readBuffer.flip();
		buffer.limit(buffer.capacity());
		buffer.put(readBuffer);
	}
	
	public String decode(ByteBuffer buffer){
		CharBuffer charBuffer = charset.decode(buffer);
		return charBuffer.toString();
	}
	
	public ByteBuffer encode(String str){
		return charset.encode(str);
	}
	public static void main(String[] args) throws IOException {
		final EchoServer server = new EchoServer();
		Thread accept = new Thread(){
			public void run()
			{
				server.accept();
			}
		};
		accept.start();
		server.service();
	}

}
