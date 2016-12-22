package edu.classm8web.junit;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import edu.classm8web.database.dto.M8;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.rs.resource.SchoolclassResource;
import edu.classm8web.rs.resource.UserResource;
import edu.classm8web.rs.result.Result;
import edu.classm8web.rs.result.SchoolclassResult;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSchoolclass {
	private static SchoolclassResource scr;
	private static UserResource ur;
	private static M8 m8;
	private static M8 m8dos;
	private static Schoolclass sc;
	private static final String PERSISTENCE_UNIT = "ClassM8Web";
	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeClass
	public static void setUpClass() {
        System.out.println("creating entity manager factory");
		scr = new SchoolclassResource();
		ur = new UserResource();
		sc = new Schoolclass();
		m8 = new M8();
		m8dos = new M8();
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	@Before
	public void setUp() {
        System.out.println("creating entity manager");
		em = emf.createEntityManager();
	}

	@After
	public void tearDown() throws Exception {
		try {
			System.out.println("tearDown() started, em=" + em);
			em.getTransaction().begin();
			em.flush();
			// logAutos();
			em.getTransaction().commit();
			em.close();
			System.out.println("tearDown() complete, em=" + em);
		}

		catch (Exception ex) {
			System.out.println("teardown failed");
			ex.printStackTrace();
			throw ex;
		}

	}
	
    @AfterClass
    public static void tearDownClass() {
        System.out.println("closing entity manager factory");
        emf.close();
    }
	

	@Test
	public void test1Insert() {
		System.out.println("Insert M8 and Schoolclass");
		m8.setFirstname("firstname");
		m8.setLastname("lastname");
		m8.setEmail("testingEmail34343434343434343434344343434");
		m8.setPassword("password123");
		ur.create(m8);
		
		sc.setName("TestBHIFS");
		sc.setRoom("1337");
		sc.setSchool("TestTL");
		
		Response res = scr.createSchoolclass(sc, m8.getId() + "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test2Get(){
		System.out.println("Get Schoolclass");
		Response res = scr.getClassbyUser(null, null, m8.getId() + "");
		SchoolclassResult result = (SchoolclassResult) res.getEntity();
		assertTrue(result.isSuccess());
	}

	@Test
	public void test3Update() {
		System.out.println("Update Schoolclass");
		sc.setName("updated");
		Response res = scr.updateSchoolclass(null, null, sc.getId() + "", sc);
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test4AddM8ToSchoolclass(){
		System.out.println("Add M8 to Schoolclass");
		m8dos.setFirstname("firstname");
		m8dos.setLastname("lastname");
		m8dos.setEmail("testingEmailm8dos777777777777777777777");
		m8dos.setPassword("password123");
		ur.create(m8dos);
		
		Response res = scr.addM8ToSchoolClass(null, null, m8dos.getId() + "", sc.getId()+ "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
		
	}
	
	@Test
	public void test5RemoveM8FromSchoolclass(){
		System.out.println("Remove M8 From Schoolclass");
		Response res = scr.removeM8fromSchoolClass(null, null, m8dos.getId() + "", sc.getId() + "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test6Delete() {
		System.out.println("Delete M8s and Schoolclasses");
		ur.delete(null, null, m8.getId() + "");
		ur.delete(null, null, m8dos.getId() + "");
		Response res = scr.deleteSchoolclass(null, null, sc.getId() + "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	

}
