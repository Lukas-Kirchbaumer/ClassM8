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
import edu.classm8web.rs.resource.UserResource;
import edu.classm8web.rs.result.M8Result;
import edu.classm8web.rs.result.Result;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestM8 {

	private static UserResource ur;
	private static M8 m8;
	private static final String PERSISTENCE_UNIT = "ClassM8Web";
	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeClass
	public static void setUpClass() {
        System.out.println("creating entity manager factory");
		ur = new UserResource();
		m8 = new M8();
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
	public void test1Insert() {
		m8.setFirstname("firstname");
		m8.setLastname("lastname");
		m8.setEmail("testingEmail1212121212121212121212121212121212121212121212121212121212121");
		m8.setPassword("password123");
		Response res = ur.create(m8);
		M8Result result = (M8Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test2Get(){
		Response res = ur.get(null, null, m8.getId() + "");
		M8Result result = (M8Result) res.getEntity();
		assertTrue(result.isSuccess());
	}

	@Test
	public void test3Update() {
		m8.setFirstname("updated");
		Response res = ur.update(null, null, m8.getId() + "", m8);
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void test4Delete() {
		Response res = ur.delete(null, null, m8.getId() + "");
		Result result = (Result) res.getEntity();
		assertTrue(result.isSuccess());
	}

}
