package controller;


import java.io.IOException;
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



/**
 * Servlet implementation class GroupServlet
 */
@WebServlet("/GroupServlet")
public class GroupServlet extends HttpServlet {
	 private static final String PERSISTENCE_UNIT_NAME = "ws1";
     private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
 	 private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String req = request.getParameter("subLogin");
		
		    System.out.println("HEre it is");
			this.loginRequest(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


        public void loginRequest(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
		UserDao dao = new UserDao();
		String group_id = request.getParameter("group_id");
      
        EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
        Query  query =em.createNativeQuery
             	("select id, post, p.person_id, group_id, first_name from post p, person per where p.group_id=?1 "
             			+ "and p.person_id = per.person_id");
		
		Map<Object[], List<Object[]>> map = new HashMap<Object[], List<Object[]>>();
		List<Object[]> obj = (List<Object[]>) query.setParameter(1, group_id).getResultList();
	    for (Object[] objs : obj){
	    	System.out.println("id is" + objs[0] + "Post is"+ objs[1] + "Person id of post is" + objs[2]);
	    	System.out.println("Post Name is " + objs[4]);
	    	Query query2 = em.createNativeQuery("select c.comment, c.id, c.person_id, p.first_name from comment c, person p where c.person_id = p.person_id and c.p_id =" + objs[0]);
	    	List<Object[]> obj2 = (List<Object[]>) query2.getResultList();
	    	map.put(objs, obj2);
	    	for (Object[] objs1 : obj2){
	    		System.out.println("Comments are " + objs1[0] + "Person id is" + objs1[1]);
	    		//Query cmt_person = em.createNativeQuery("select first_name from person where person_id= " + objs1[1]);
	    		//String cmt_name= (String) cmt_person.getSingleResult();
	    		System.out.println(" Cmnt Name is" + objs1[3]);
	    	}
	    }
	    
	    request.setAttribute("mapData", map);
        em.getTransaction().commit();
			em.close();
			RequestDispatcher rd = request.getRequestDispatcher("Group_page.jsp");
			rd.forward(request, response);
	    }}

