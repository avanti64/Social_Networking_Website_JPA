package userDao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Person;
import model.Phone;
import model.PhoneTypeTb;

public class serviceDao {


    private static final String PERSISTENCE_UNIT_NAME = "ws1";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	
	public int save(Person person, int PhoneType, String phoneNo) {
		EntityManager em = factory.createEntityManager();
		
		
		em.getTransaction().begin();
		Query query = em.createNativeQuery("select COUNT(user_name) from person where user_name=?1");
        long count= (Long)query.setParameter(1, person.getUserName()).getSingleResult();
        System.out.println("Count is" + count);
        if (count == 0){
        em.persist(person);
        Phone phone = new Phone();
       phone.setPerson(person);
       phone.setPhoneNumber(phoneNo);
       System.out.println("Phone no in count"+ phone.getPhoneNumber());
        PhoneTypeTb phnType = new PhoneTypeTb();
       phnType.setId(PhoneType);
       System.out.println("Phonetyp" + phnType.getId());
       phone.setPhoneTypeTb(phnType);
       em.persist(phone);
		em.getTransaction().commit();
		em.close();
		return 1;

	}
        else {
        	System.out.println("USer doesnt exist");
        	return 0;
        }
	}
	
	public static void main(String[] args) {
		serviceDao dao = new serviceDao();
	/*	List<UserCredential> cred = dao.load();
		System.out.println("Cred ************************:"+cred);
		if (cred.isEmpty())
			System.out.println("************ NULL *************** ");
		for(UserCredential c :cred)
			System.out.println( c); */  
	}
	}
