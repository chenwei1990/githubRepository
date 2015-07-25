package mysqlTest;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Test {

	 public static void main(String[] args)
	  {
	    try
	    {
	      String url="jdbc:mysql://10.108.198.56/cwtest";
	      String user="root";
	      String pwd="111111";
	      
	      //加载驱动，这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
	     Class.forName("com.mysql.jdbc.Driver");
	      //建立到MySQL的连接
	       Connection conn = DriverManager.getConnection(url,user, pwd);
	      
	      //执行SQL语句
	      Statement stmt = conn.createStatement();//创建语句对象，用以执行sql语言
	      ResultSet rs = stmt.executeQuery("select * from student");
	     
	       //处理结果集
	      while (rs.next())
	      {
	        String name = rs.getString("name");
	        System.out.println(name);
	      }
	      rs.close();//关闭数据库
	      conn.close();
	    }
	    catch (Exception ex)
	    {
	      System.out.println("Error : " + ex.toString());
	    }
	  }

}
