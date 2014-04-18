package userDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import study.Study;
import model.Comment;
import model.Group;
import model.GroupPerson;
import model.Person;
import model.Post;

public class UserDao {
    
	private static final String PERSISTENCE_UNIT_NAME = "ws1";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	int count = 0;

	public boolean isRoleUSer(String username) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
	   
		Query roleQry = em.createNativeQuery("select role_name from person where user_name= ?1");
		String role =   (String) roleQry.setParameter(1, username).getSingleResult();
		System.out.println("Role of user is" + role);
		em.getTransaction().commit();
		em.close();
		if (role.equalsIgnoreCase("User"))
		{
			return true;
		}
		else {
			return false;
		}

		
	}
	
	
	public void savePost(String postValue, int group_id, int person_id) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Person person = em.find(Person.class, person_id);
        Group group = em.find(Group.class, group_id);
	    
	    // Aniket -> Insert query
	   Post post = new Post();
	   post.setPost(postValue);
	   post.setPerson(person);
	   post.setGroup(group);
	   em.persist(post);
	   System.out.println("Post val " + post);
       em.getTransaction().commit();
	   em.close();

		
	}
	
	public void updatePost(String postValue, int group_id, int person_id) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Person person = em.find(Person.class, person_id);
        Group group = em.find(Group.class, group_id);
	    
	    // Aniket -> Insert query
	   Post post = new Post();
	   post.setPost(postValue);
	   post.setPerson(person);
	   post.setGroup(group);
	   em.persist(post);
	   System.out.println("Post val " + post);
       em.getTransaction().commit();
	   em.close();

		
	}
	
	public void UpdatePostAdmin(String post, int user_id, int post_id) throws SQLException
	{

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Query query= em.createNativeQuery("UPDATE post set post =?1, person_id = ?2 WHERE id = ?3");
		query.setParameter(1, post);
		query.setParameter(2, user_id);
		query.setParameter(3, post_id);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}

	
	public void saveComment(String cmtVal, int post_id, int person_id) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Person person = em.find(Person.class, person_id);
		Post post = em.find(Post.class, post_id);
 
	    
	    // Aniket -> Insert query
	    Comment cmt = new Comment();
	    cmt.setComment(cmtVal);
	    cmt.setPerson(person);
	    cmt.setPost(post);
	   em.persist(cmt);
	   System.out.println("Post val " + cmt);
       em.getTransaction().commit();
	   em.close();

		
	}
	
	
	
	
	public int isvalidateUser (String username, String paswd){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Query query12 = em.createNativeQuery("select person_id from person where user_name =?1 and password= MD5(?2)");
		try{
		Object id[] = new Object[2];
		int p_id;
			//	id = (Object[])query12.setParameter(1, username).getSingleResult();
		p_id= (Integer) query12.setParameter(1, username).setParameter(2, paswd).getSingleResult();
	//	int personid= (Integer) id[0];
	//	String pwd = (String) id[1];
		
		System.out.println("personid is " + p_id);
	//	System.out.println("Pwd is " + pwd);
		em.getTransaction().commit();
		em.close();
			return p_id;
			}
		catch(NoResultException e)
		{
			return -1;
		}

	}
	
	public List<Object[]> displayRequests()
	{   
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Query query= em.createNativeQuery("select first_name, group_name, gp.group_id, gp.person_id from person p, groups grp, group_person gp where gp.group_id = grp.group_id and gp.person_id = p.person_id and gp.flag = 'Pending'");
		Query query_cnt = em.createNativeQuery("select count(group_id) from group_person where flag = 'Pending'");
	    long count = (Long)query_cnt.getSingleResult();
	    List<Object[]> obj1 =(List<Object[]>) query.getResultList();
	    em.getTransaction().commit();
	    em.close();
	    return obj1;
	}
	
	public void addUpdate(Map<Integer, String> chkbox)
	{
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		List <Object []> objs = displayRequests();
		List<Object []> objs1 = new ArrayList<Object[]> ();
		System.out.println("Inside addUpdate() method");
		PreparedStatement ps;
		String q1;
		int j = 0;
			System.out.println("Inside try of addUpdate() method");
			for (Integer key : chkbox.keySet()) 
			{
				int index = Integer.parseInt(chkbox.get(key));
				objs1.add(objs.get(index));
			}
			
			for (Object[] obj: objs1){
				System.out.println("Grp id is" + obj[2]);
				System.out.println("Person id is " + obj[3]);
				Person person = new Person();
				Group group = new Group();
				person = em.find(Person.class, obj[3]);
				group = em.find(Group.class, obj[2]);
				
				GroupPerson gp = new GroupPerson();
				//em.merge(arg0);
				Query query= em.createNativeQuery("update group_person set flag = 'Approved' where group_id = '" + obj[2] + "' and person_id = '" + obj[3] + "'");
				query.executeUpdate();
				 //upd_query.getResultList();
				
		
			}
			em.getTransaction().commit();
			em.close();
	       
			
		}
	
	
	public void rejectUpdate(Map<Integer, String> chkbox)
	{
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		List <Object []> objs = displayRequests();
		List<Object []> objs1 = new ArrayList<Object[]> ();
		System.out.println("Inside addUpdate() method");
		PreparedStatement ps;
		String q1;
		int j = 0;
			System.out.println("Inside try of addUpdate() method");
			for (Integer key : chkbox.keySet()) 
			{
				int index = Integer.parseInt(chkbox.get(key));
				objs1.add(objs.get(index));
			}
			
			for (Object[] obj: objs1){
				System.out.println("Grp id is" + obj[2]);
				System.out.println("Person id is " + obj[3]);
				Person person = new Person();
				Group group = new Group();
				person = em.find(Person.class, obj[3]);
				group = em.find(Group.class, obj[2]);
				
				GroupPerson gp = new GroupPerson();
				//em.merge(arg0);
				Query query= em.createNativeQuery("update group_person set flag = 'Rejected' where group_id = '" + obj[2] + "' and person_id = '" + obj[3] + "'");
				query.executeUpdate();
				 //upd_query.getResultList();
				
		
			}
			em.getTransaction().commit();
			em.close();
	       
			
		}
	
/*	public void rejectUpdate(Map<Integer, String> chkbox)
	{
		System.out.println("Inside rejectUpdate() method");
		PreparedStatement ps;
		String q1;
		int j = 0;
		try
		{
			System.out.println("Inside try of rejectUpdate() method");
			for (Integer key : chkbox.keySet()) 
			{
	            System.out.println("Key = " + key);
	            System.out.println("Value = " + chkbox.get(key));
	            j = Integer.parseInt(chkbox.get(key));
	            q1 = "Update group_person set flag = 'Rejected' where group_id = '" + group_id[j] + "' and person_id = '" + person_id[j] + "'";
	            ps = getConnection().prepareStatement(q1);
	            int i = ps.executeUpdate();
	            System.out.println("Updated " + i + " row as rejected");
	            ps.close();
	        }
			
		}
		catch (SQLException e) 
		{
			System.out.println("Cannot run the query");
			e.printStackTrace();
			
		}
	} */

	
	
	public static void main(String[] args) {
        UserDao dao = new UserDao();
        
        
       // System.out.println("This is validate user " + dao.isvalidateUser("avanti", "xyz"));
       // System.out.println("This is checking user role" + dao.isRoleUSer("avanti"));
		//List<Person> persons = dao.load();
		System.out.println("Person");

	}
}
