package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.persistence.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.*;

import model.Person;

/**
 * Servlet implementation class SuccessServlet
 */
@WebServlet("/SuccessServlet")
public class SuccessServlet extends HttpServlet {


	    // this injects the default entity manager factory
	    private static final String PERSISTENCE_UNIT_NAME = "ws1";
		private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    public void service ( HttpServletRequest req , HttpServletResponse resp) 
	        throws ServletException, IOException {
	    	HttpSession session = req.getSession();
	    	String user_name = new String();
	    	String user_id = new String();
	    	user_name = req.getParameter("user_name");
	        session.setAttribute("user_name", user_name);
	        
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			
		    // Aniket group query 
			
			Query query4 = em.createNativeQuery("select grp.group_name, grp.group_id from groups grp, group_person gp, person p"
					+ " where grp.group_id = gp.group_id and gp.person_id = p.person_id and user_name =?1");
			
			List<Object[]> obj1 =(List<Object[]>) query4.setParameter(1, user_name).getResultList();
			req.setAttribute("ObjectList", obj1);
	    	/* while(rs.next())
	    	{
               rs.getInt("group_id")
               rs.getString("group_name") %>"> <%=rs.getString("group_name")%>
	    	
	    	//System.out.println(rs.getInt("group_id"));
	    		System.out.println(rs.getString("group_name"));	
	    	} */
	    	
		em.getTransaction().commit();
		em.close();
		 RequestDispatcher rd = req.getRequestDispatcher("Successful_Login.jsp");
		 rd.forward(req, resp);
	    	
	    	//System.out.println ("got the pages successfully")
	            // we must close em so that provider can release resources.
	        }
	    

	    public void  init( ServletConfig config) throws ServletException {
	        super.init(config);
	    }
	    
	}
