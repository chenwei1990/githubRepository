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
	      
	      //������������һ��Ҳ��дΪ��Class.forName("com.mysql.jdbc.Driver");
	     Class.forName("com.mysql.jdbc.Driver");
	      //������MySQL������
	       Connection conn = DriverManager.getConnection(url,user, pwd);
	      
	      //ִ��SQL���
	      Statement stmt = conn.createStatement();//��������������ִ��sql����
	      ResultSet rs = stmt.executeQuery("select * from student");
	     
	       //��������
	      while (rs.next())
	      {
	        String name = rs.getString("name");
	        System.out.println(name);
	      }
	      rs.close();//�ر����ݿ�
	      conn.close();
	    }
	    catch (Exception ex)
	    {
	      System.out.println("Error : " + ex.toString());
	    }
	  }

}
