package edu.classm8web.database.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import edu.classm8web.database.dao.common.BaseDBService;
import edu.classm8web.database.dto.M8;

public class MateService implements BaseDBService<Long, M8>{


	private static MateService instance;

	private EntityManager em;
	private EntityManagerFactory emf;
	
	private MateService() {
		createPersistentComponents();
	}

	public static MateService getInstance() {
		if (MateService.instance == null) {
			MateService.instance = new MateService();
		}
		return MateService.instance;
	}
	
	@Override
	public void persist(M8 entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeByEntity(M8 entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(M8 entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public M8 findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<M8> findAll() {
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
