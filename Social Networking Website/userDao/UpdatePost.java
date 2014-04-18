package userDao;

import java.sql.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UpdatePost {
	
	private String post_to_update;
	private int person_id;
	private int post_id;
	
	  String url = "jdbc:mysql://localhost:3306/";
	  String dbName = "test1";
	  String driver = "com.mysql.jdbc.Driver";
	  String userName = "root"; 
	  String password = "";
	


	public void setUpdatePost(String post, int person_id, int post_id) 
	{
		this.post_to_update = post;
		this.person_id = person_id;
		this.post_id = post_id;
	}
	



	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url+dbName,userName,password);
		return conn;
	}

	
	public boolean UpdatePost(UpdatePost d,String post, int user_id, int post_id) throws SQLException
	{
		System.out.println("comes to InsertDemo");
		PreparedStatement ps;
		System.out.println("Made prepares statement" );
		try
		{
			
			System.out.println("now inserting the data");
			System.out.println("Now inserting the data in table post_id = "+ post_id + " "+ this.post_to_update + " " + this.person_id);
			
			String updateSQL = "UPDATE `test1`.`post` set `post` = ?,`person_id` = ? WHERE `id` = ?;";
			PreparedStatement preparedStatement = getConnection().prepareStatement(updateSQL);
			preparedStatement.setString(1, post);
			preparedStatement.setInt(2, user_id);
			preparedStatement.setInt(3,post_id);
			preparedStatement.executeUpdate();
	    }
		catch (SQLException e) 
		{
			System.out.println();
			System.out.println("Cannot run the query 4");
			e.printStackTrace();
			return false;
		}
		
		return false;

		}
}

