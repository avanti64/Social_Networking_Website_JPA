package controller;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class AdminGrpServlet
 */
@WebServlet("/AdminGrpServlet")
public class AdminGrpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String PERSISTENCE_UNIT_NAME = "ws1";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    UserDao dao = new UserDao();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminGrpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		this.loginRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.loginRequest(request, response);
	}
	
	
	public void loginRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("This is login request");
		HttpSession session1 = request.getSession();
		
		String userName = (String)session1.getAttribute("username");;
		String password = request.getParameter("txtPassword");
        EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Query query4 = em.createNativeQuery("select grp.group_name, grp.group_id from groups grp, group_person gp, person p"
				+ " where grp.group_id = gp.group_id and gp.person_id = p.person_id and user_name =?1");
		
		List<Object[]> obj1 =(List<Object[]>) query4.setParameter(1, userName).getResultList();
		System.out.println("Object " + obj1);
		request.setAttribute("ObjectList", obj1);
		System.out.println("Checking user name " + userName);
		request.setAttribute("user_name", userName);
		em.getTransaction().commit();
		em.close();
		RequestDispatcher rd = request.getRequestDispatcher("Admin_Grp.jsp");
		rd.forward(request, response);
	}
	

}
