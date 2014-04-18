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

import userDao.InsertCommentDemo;
import userDao.UserDao;


@WebServlet("/comment")
public class CommentServlet extends HttpServlet 
{
private static final long serialVersionUID = 1L;
	int post_id;
	InsertCommentDemo demo = new InsertCommentDemo();
	
	public CommentServlet() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String req = request.getParameter("comment");
		if(req != null )
		{
			System.out.println("Came in CommentServlet");
			this.insertRequest(request, response);
			
		}
		else
		{
			System.out.println("No data to insert");
		}
		
	}

	
	public void insertRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("The control comes to insertCoommentRequest function");
		String req = request.getParameter("comment");
		System.out.println("The value from tezt box is :" + req);
		UserDao dao= new UserDao (); 
		System.out.println("Username from text box is " + req );
			HttpSession session = request.getSession();
				System.out.println("The control comes here in servlet page ");
				int person_id = (int) session.getAttribute("person_id");
				System.out.println("The value from session page is : " + session.getAttribute("post_id"));
				post_id =  Integer.valueOf((String) session.getAttribute("post_id"));
				System.out.println("The control comes here in servlet page ---2 : ");
				dao.saveComment(req, post_id, person_id);
				System.out.println("the group_id on insertpostservlet is : " );
				response.sendRedirect("GroupServlet?group_id=" + session.getAttribute("group_id") + "&group_name=" +session.getAttribute("group_name"));
			
		} 
	}



