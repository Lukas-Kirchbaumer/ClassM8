package edu.classm8web.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import edu.classm8web.dao.common.BaseService;
import edu.classm8web.dto.Schoolclass;

@Stateless
public class SchoolclassDatabaseService implements BaseService<Integer, Schoolclass>{

	@PersistenceUnit
	private EntityManager em;

	@Override
	public void persist(Schoolclass entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Schoolclass entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Schoolclass findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Schoolclass entity) {
		// TODO Auto-generated method stub
		
	}
	

}
