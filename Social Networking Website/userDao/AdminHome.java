package userDao;

import java.sql.*;

public class AdminHome 
{


	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "test1";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root"; 
	String password = "";
	
	public int count;
	public String[] first_name = new String[1000];
	public String[] group_name = new String[1000];
	
	public Connection getConnection() throws SQLException 
	{
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
	
	public void displayRequests()
	{
		PreparedStatement ps;
		PreparedStatement ps1;
		String q1 = "select first_name, group_name from person p, groups grp, group_person gp where gp.group_id = grp.group_id and gp.person_id = p.person_id and gp.flag = 'Pending'";
		String q2 = "select count(group_id) from group_person where flag = 'Pending'";
		try
		{
			ps = getConnection().prepareStatement(q1);
			ps1 = getConnection().prepareStatement(q2);
			ResultSet rs1 = ps.executeQuery();
			ResultSet rs2 = ps1.executeQuery();
			int i = 0;
			rs2.next();
			count = rs2.getInt(1);
			System.out.println("count inside displayRequests method " + count);
			while (rs1.next())
			{
				
				first_name[i] = rs1.getString(1);
				group_name[i] = rs1.getString(2);
				System.out.println(first_name[i] + " " + group_name[i]);
				i++;
			}
			
			rs1.close();
			rs2.close();
			ps.close();
			ps1.close();
	    }
		catch (SQLException e) 
		{
			System.out.println("Cannot run the query 7");
			e.printStackTrace();
			
		}
	}
}
