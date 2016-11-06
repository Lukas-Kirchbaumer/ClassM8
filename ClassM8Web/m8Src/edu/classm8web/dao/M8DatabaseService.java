package edu.classm8web.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.classm8web.dao.common.BaseService;
import edu.classm8web.dto.M8;

@Stateless
public class M8DatabaseService implements BaseService<Integer, M8>{

	
	@PersistenceContext	
	private EntityManager em;

	@Override
	public void persist(M8 entity) {
		em.persist(entity);
	}

	@Override
	public void remove(M8 entity) {
		em.remove(entity);
	}

	@Override
	public void update(M8 entity) {
		em.merge(entity);
	}
	
	@Override
	public M8 findById(Integer id) {
		return em.find(M8.class, id);
	}

	@Override
	public List<M8> findAll() {
		Query q = em.createNamedQuery("SELECT * from M8");
		return q.getResultList();
	}


}
