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

import edu.classm8web.database.dao.FileService;
import edu.classm8web.database.dao.MateService;
import edu.classm8web.database.dao.SchoolclassService;
import edu.classm8web.database.dto.M8;
import edu.classm8web.rs.resource.ElectionResource;
import edu.classm8web.rs.resource.SecurityResource;
import edu.classm8web.rs.resource.UserResource;
import edu.classm8web.rs.result.M8Result;
import edu.classm8web.rs.result.Result;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestM8 {

	private static UserResource ur;
	private static SecurityResource sr;
	private static M8 m8;
	private static M8 m8dos;
	private static final String PERSISTENCE_UNIT = "ClassM8Web";
	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeClass
	public static void setUpClass() {
        System.out.println("creating entity manager factory");
		ur = new UserResource();
		m8 = new M8();
		m8dos = new M8();
		sr = new SecurityResource();
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	@Before
	public void setUp() {
        System.out.println("creating entity manager");
		em = emf.createEntityManager();
		
		System.out.println("######## START - JAX RS - SERVLET ########");
		MateService.getInstance();
		SchoolclassService.getInstance();
		FileService.getInstance(); //Create Persistence Components
		
		System.out.println(MateService.getInstance().getEm());
		System.out.println(SchoolclassService.getInstance().getEm());
		System.out.println(FileService.getInstance().getEm());
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
		m8.setFirstname("firstname");
		m8.setLastname("lastname");
		m8.setEmail("testingEmail1212121212121212121212121212121212121212121212121212121212121");
		m8.setPassword("password123");
		Response res = ur.create(m8);
		M8Result result = (M8Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test1B_InsertFail() {
		m8dos.setFirstname("firstname");
		m8dos.setLastname("lastname");
		m8dos.setEmail("testingEmail1212121212121212121212121212121212121212121212121212121212121");
		m8dos.setPassword("password123");
		Response res = ur.create(m8dos);
		M8Result result = (M8Result) res.getEntity();
		assertFalse(result.isSuccess());
	}
	
	@Test
	public void test2A_Get(){
		Response res = ur.get(null, null, m8.getId() + "");
		M8Result result = (M8Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test2B_GetFail(){
		Response res = ur.get(null, null, -1 + "");
		M8Result result = (M8Result) res.getEntity();
		assertFalse(result.isSuccess());
	}
	
	@Test
	public void test3GetAll(){
		Response res = ur.getUsers(null, null);
		M8Result result = (M8Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	
	@Test
	public void test4A_Login() {
		Response res = sr.login(m8);
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test4B_LoginFail() {
		Response res = sr.login(new M8());
		Result result = (Result) res.getEntity();
		assertFalse(result.isSuccess());
	}

	@Test
	public void test5A_Update() {
		m8.setFirstname("updated");
		Response res = ur.update(null, null, m8.getId() + "", m8);
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test5B_UpdateFail() {
		m8.setFirstname("updated");
		Response res = ur.update(null, null, -1 + "", m8);
		Result result = (Result) res.getEntity();
		assertFalse(result.isSuccess());
	}
	
	@Test
	public void test6A_Delete() {
		Response res = ur.delete(null, null, m8.getId() + "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test6B_DeleteFail() {
		Response res = ur.delete(null, null, -1 + "");
		Result result = (Result) res.getEntity();
		assertFalse(result.isSuccess());
	}

}
