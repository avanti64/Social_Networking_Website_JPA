package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import userDao.UserDao;


@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet 
{
	  private static final String PERSISTENCE_UNIT_NAME = "ws1";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
     private static final long serialVersionUID = 1L;
     UserDao dao = new UserDao();

	
	public LoginServlet() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String req1 = request.getParameter("subAdd");
		String req2 = request.getParameter("subReject");
		int j = 0;
		Map<Integer, String> chkbox = new HashMap<Integer, String>();
		String[] checkboxes = new String[1000];
		System.out.println("Inside login servlet");
		checkboxes = request.getParameterValues("requests");
		for (String c : checkboxes)
		{
			chkbox.put(j,c);
			j++;
			System.out.println("c = " + c);
		}
		
		if(req1 != null && req1.equals("Add"))
		{
			System.out.println("Calling add method....");
			dao.addUpdate(chkbox);
		}
		else
		{
		if(req2 != null && req2.equals("Reject"))
		{
			System.out.println("Calling reject method....");
			dao.rejectUpdate(chkbox);
		}
		else
		{
			
			System.out.println("Cannot do any operation");
		}
		}
	
		System.out.println("Out of if loop...");
		this.adminHomeRequest(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String req = request.getParameter("subLogin");
		if(req != null && req.equals("Login"))
		{
			this.loginRequest(request, response);
			
		}
		else
		{
			System.out.println("Cannot login");
		}
		
	}

	public void loginRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("This is login request");
		String userName = request.getParameter("txtUserName");
		String password = request.getParameter("txtPassword");
		HttpSession session1 = request.getSession();
		session1.setAttribute("username",userName);
        EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Query query4 = em.createNativeQuery("select grp.group_name, grp.group_id from groups grp, group_person gp, person p"
				+ " where grp.group_id = gp.group_id and gp.person_id = p.person_id and gp.flag='Approved' and user_name =?1");
		
		List<Object[]> obj1 =(List<Object[]>) query4.setParameter(1, userName).getResultList();
		System.out.println("Object " + obj1);
		request.setAttribute("ObjectList", obj1);
		System.out.println("Checking user name " + userName);
		request.setAttribute("user_name", userName);
		em.getTransaction().commit();
		em.close();
		
			if (userName!= null & password!= null)
			{
			
				dao.isvalidateUser(userName, password);	
			//this.demo.validateUser(demo);
			}
			else
			{
				System.out.println("Invalid username and password");
			}
		 
         if (dao.isvalidateUser(userName, password) != -1)
			{
			
				
				String name = request.getParameter("txtUserName");
				HttpSession session = request.getSession();
				session.setAttribute("chkuser",false);
         		session.setAttribute("person_id", dao.isvalidateUser(userName, password));
				if (dao.isRoleUSer(userName))
				{
				session.setAttribute("role", "User");
				RequestDispatcher rd = request.getRequestDispatcher("Successful_Login.jsp?user_name=" + name);
				rd.forward(request, response);
				}
				else
				{
					session.setAttribute("role", "Admin");
					session.setAttribute("name", name);
					request.setAttribute("role", "Admin");
					request.setAttribute("user_name",name );
                    this.adminHomeRequest(request, response);
				}
			}
			else
			{
				
				HttpSession session = request.getSession();
				session.setAttribute("chkuser",true);
				request.setAttribute("user_name", userName);
				RequestDispatcher rd = request.getRequestDispatcher("login_jsp.jsp?chkuser=" + session.getAttribute("chkuser"));
				rd.forward(request, response);
			}
	}
	
	public void adminHomeRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{   
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Query query_cnt = em.createNativeQuery("select count(group_id) from group_person where flag = 'Pending'");
		long count = (Long)query_cnt.getSingleResult();
		List<Object[]> obj1 =  dao.displayRequests();
		request.setAttribute("ObjList", obj1);
		request.setAttribute("count", count);
	    
		RequestDispatcher rd = request.getRequestDispatcher("Admin_Home.jsp");
		rd.forward(request, response);
	}
}
