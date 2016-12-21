package edu.classm8web.database.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import edu.classm8web.database.dao.common.BaseDBService;
import edu.classm8web.database.dto.Schoolclass;

public class SchoolclassService implements BaseDBService<Long,Schoolclass>{

	private static SchoolclassService instance;

	private EntityManager em;
	private EntityManagerFactory emf;
	
	private SchoolclassService() {
		createPersistentComponents();
	}

	public static SchoolclassService getInstance() {
		if (SchoolclassService.instance == null) {
			SchoolclassService.instance = new SchoolclassService();
		}
		return SchoolclassService.instance;
	}
	
	@Override
	public void persist(Schoolclass entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeByEntity(Schoolclass entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Schoolclass entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Schoolclass findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Schoolclass> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createPersistentComponents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeEntityManagerFactory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeEntityManager() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return null;
	}

}
