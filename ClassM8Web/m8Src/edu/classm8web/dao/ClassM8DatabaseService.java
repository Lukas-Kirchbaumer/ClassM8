package edu.classm8web.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClassM8DatabaseService {

	@PersistenceContext
	private EntityManager em;
	
	//TODO: CRUD OPERATIONS
}
