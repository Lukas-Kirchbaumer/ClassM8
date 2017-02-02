package edu.classm8web.junit;

import static org.junit.Assert.*;

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
import edu.classm8web.rs.resource.ElectionResource;
import edu.classm8web.rs.resource.SchoolclassResource;
import edu.classm8web.rs.resource.UserResource;
import edu.classm8web.rs.result.Result;
import edu.classm8web.rs.result.SchoolclassResult;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSchoolclass {
	private static SchoolclassResource scr;
	private static UserResource ur;
	private static ElectionResource er;
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
		er = new ElectionResource();
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
	public void test1A_Insert() {
		System.out.println("Insert M8 and Schoolclass");
		m8.setFirstname("firstname");
		m8.setLastname("lastname");
		m8.setEmail("testingEmail34343434343434343434344343434");
		m8.setPassword("password123");
		ur.create(null, m8);
		
		sc.setName("TestBHIFS");
		sc.setRoom("1337");
		sc.setSchool("TestTL");
		
		Response res = scr.createSchoolclass(null, sc, m8.getId() + "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	
	@Test
	public void test2A_Get(){
		System.out.println("Get Schoolclass");
		Response res = scr.getClassbyUser(null, null, m8.getId() + "");
		SchoolclassResult result = (SchoolclassResult) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test2B_GetFail(){
		System.out.println("Get Schoolclass");
		Response res = scr.getClassbyUser(null, null, -1 + "");
		SchoolclassResult result = (SchoolclassResult) res.getEntity();
		assertFalse(result.isSuccess());
	}
	
	@Test
	public void test3GetAll(){
		System.out.println("Get All Schoolclasses");
		Response res = scr.getAllSchoolclasses(null, null);
		SchoolclassResult result = (SchoolclassResult) res.getEntity();
		assertTrue(result.isSuccess());
	}

	@Test
	public void test4A_Update() {
		sc.setName("updated");
		Response res = scr.updateSchoolclass(null, null, sc.getId() + "", sc);
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test4B_UpdateFail() {
		sc.setName("updated2");
		Response res = scr.updateSchoolclass(null, null, -1 + "", null);
		Result result = (Result) res.getEntity();
		assertFalse(result.isSuccess());
	}
	
	@Test
	public void test5A_GetM8BySchoolclass() {
		System.out.println("Get M8s by Schoolclass");
		Response res = ur.getBySchoolClass(null, null, sc.getId() + "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test5B_GetM8BySchoolclassFail() {
		System.out.println("Get M8s by Schoolclass Failing");
		Response res = ur.getBySchoolClass(null, null, -1 + "");
		Result result = (Result) res.getEntity();
		assertFalse(result.isSuccess());
	}
	
	
	@Test
	public void test6A_AddM8ToSchoolclass(){
		System.out.println("Add M8 to schoolclass");
		System.out.println("Add M8 to Schoolclass");
		m8dos.setFirstname("firstname");
		m8dos.setLastname("lastname");
		m8dos.setEmail("testingEmailm8dos777777777777777777777");
		m8dos.setPassword("password123");
		ur.create(null, m8dos);
		
		Response res = scr.addM8ToSchoolClass(null, null, m8dos.getId() + "", sc.getId()+ "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
		
	}
	
	@Test
	public void test6B_AddM8ToSchoolclassFail(){
		System.out.println("Add M8 to schoolclass fail");
		Response res = scr.addM8ToSchoolClass(null, null, -1 + "", -1 + "");
		Result result = (Result) res.getEntity();
		assertFalse(result.isSuccess());
		
	}
	
	@Test
	public void test7A_Election() {
		System.out.println("Election");
		Response res = er.vote(null, m8dos.getId() + "", m8.getId() + "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test7B_ElectionFail() {
		System.out.println("Election Failing");
		Response res = er.vote(null, m8dos.getId() + "", m8.getId() + "");
		Result result = (Result) res.getEntity();
		assertFalse(result.isSuccess());
	}
	
	@Test
	public void test8A_RemoveM8FromSchoolclass(){
		System.out.println("Remove M8 From Schoolclass");
		Response res = scr.removeM8fromSchoolClass(null, null, m8dos.getId() + "", sc.getId() + "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test8B_RemoveM8FromSchoolclassFail(){
		System.out.println("Remove M8 From Schoolclass Failing");
		Response res = scr.removeM8fromSchoolClass(null, null, m8dos.getId() + "", sc.getId() + "");
		Result result = (Result) res.getEntity();
		assertFalse(result.isSuccess());
	}
	
	@Test
	public void test9A_Delete() {
		System.out.println("Delete M8s and Schoolclasses");
		sc.setPresident(null);
		sc.setPresidentDeputy(null);
		scr.updateSchoolclass(null, null, sc.getId()+ "", sc);
		ur.delete(null, null, m8.getId() + "");
		ur.delete(null, null, m8dos.getId() + "");
		
		Response res = scr.deleteSchoolclass(null, null, sc.getId() + "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test9B_DeleteFail() {
		System.out.println("Delete M8s and Schoolclasses Failing");
		Response res = scr.deleteSchoolclass(null, null, -1 + "");
		Result result = (Result) res.getEntity();
		assertFalse(result.isSuccess());
	}
	
	

}
