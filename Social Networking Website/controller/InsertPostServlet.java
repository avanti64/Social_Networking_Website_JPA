package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.*;

import userDao.InsertDemo;
import userDao.UserDao;


@WebServlet("/insert")
public class InsertPostServlet extends HttpServlet 
{
private static final long serialVersionUID = 1L;
	int group_id;
	InsertDemo demo = new InsertDemo();
	
	public InsertPostServlet() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String req = request.getParameter("text");
		if(req != null)
		{
			System.out.println("Came in InsertPostServlet");
			this.insertRequest(request, response);
			
		}
		else
		{
			System.out.println("No data to insert");
		}
		
	}

	public void insertRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("The control comes to insertRequest function");
		String req = request.getParameter("text");
		UserDao dao= new UserDao ();
		System.out.println("Username from text box is " + req );
	   HttpSession session = request.getSession();
				System.out.println("The control comes here in servlet page ");
				int person_id = (int) session.getAttribute("person_id");
				System.out.println("The value from session page is : " + session.getAttribute("group_id"));
				group_id =  Integer.valueOf((String) session.getAttribute("group_id"));
				System.out.println("The control comes here in servlet page ---2 : ");
				dao.savePost(req, group_id, person_id);
				System.out.println("the group_id on insertpostservlet is : " + group_id );
				System.out.println("Person id is " + person_id);
				System.out.println("Group name is " + session.getAttribute("group_name"));
				response.sendRedirect("GroupServlet?group_id=" + session.getAttribute("group_id") + "&group_name=" +session.getAttribute("group_name"));
	}

}

