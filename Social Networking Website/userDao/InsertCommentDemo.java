package userDao;

import java.sql.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class InsertCommentDemo {
	
	private String comment_to_insert;
	
	  String url = "jdbc:mysql://localhost:3306/";
	  String dbName = "test1";
	  String driver = "com.mysql.jdbc.Driver";
	  String userName = "root"; 
	  String password = "";
	


	public void setInsertCommentDemo(String post) 
	{
		this.comment_to_insert = post;
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

	
	public boolean InsertCommentDemo (InsertCommentDemo d, int post_id, int person_id) throws SQLException
	{
		//System.out.println("comes to InsertDemo");
		PreparedStatement ps;
		//System.out.println("Made prepares statement" );
		try
		{
			
			//System.out.println("now inserting the data");
			//System.out.println("Now inserting the data in table post_id = "+ post_id + " comment: "+ this.comment_to_insert);
			
			String insertSQL = "INSERT INTO comment (p_id, person_id, comment) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL);
			preparedStatement.setInt(1, post_id);
			preparedStatement.setInt(2, person_id);
			preparedStatement.setString(3, this.comment_to_insert);
			preparedStatement.executeUpdate();
	    }
		catch (SQLException e) 
		{
			//System.out.println();
			System.out.println("Cannot run the query 6");
			e.printStackTrace();
			return false;
		}
		
		return false;

		}
}

