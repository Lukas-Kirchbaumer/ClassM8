package edu.classm8web.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import edu.classm8web.dao.common.BaseService;

@Stateless
public class SchoolclassDatabaseService implements BaseService{

	@PersistenceUnit
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

}
