package edu.classm8web.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.classm8web.dao.common.BaseService;
import edu.classm8web.dto.M8;

@Stateless
public class M8DatabaseService implements BaseService{

	@PersistenceContext
	private EntityManager em;

	public Object createOrUpdate(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(Object object) {
		// TODO Auto-generated method stub
		
	}

	public Object find(Object id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO: CRUD OPERATIONS
}
