package edu.classm8web.database.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.classm8web.database.dao.common.BaseDBService;
import edu.classm8web.database.dto.M8;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.exception.DatabaseException;

public class MateService implements BaseDBService<Long, M8> {

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
	public void persist(M8 entity) throws DatabaseException {
		try {
			em.getTransaction().begin();

			em.persist(entity);

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public void removeById(Long id) throws DatabaseException {
		try {
			em.getTransaction().begin();
			M8 itemToRemove = this.findById(id);
			if (itemToRemove != null) {
				em.remove(itemToRemove);
			} else {
				em.getTransaction().commit();
				throw new DatabaseException("Entity not in Database");
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public void removeByEntity(M8 entity) throws DatabaseException {
		try {
			em.getTransaction().begin();
			if (em.contains(entity))
				em.remove(entity);
			else
				throw new DatabaseException("Entity not in Database");
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public void update(M8 entity) throws DatabaseException {
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public M8 findById(Long id) {
		return em.find(M8.class, id);
	}

	@Override
	public List<M8> findAll() {
		String query = new StringBuilder("SELECT u FROM ").append(M8.class.getSimpleName()).append(" u").toString();
		return em.createQuery(query).getResultList();
	}

	@Override
	public void createPersistentComponents() {
		emf = Persistence.createEntityManagerFactory("ClassM8Web");
		em = emf.createEntityManager();
	}

	@Override
	public void closeEntityManagerFactory() {
		emf.close();
	}

	@Override
	public void closeEntityManager() {
		em.close();
	}

	@Override
	public void deleteAll() throws DatabaseException {
		try {
			em.getTransaction().begin();
			String query = new StringBuilder("DELETE FROM ").append(M8.class.getSimpleName()).append(" u").toString();
			em.createQuery(query).executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public EntityManager getEm() {
		return this.em;

	}
	
	public List<M8> findByEmail(String email) throws DatabaseException{
		Query query = em.createQuery("SELECT u from " + M8.class.getSimpleName() + " u where u.email=:arg1");
		query.setParameter("arg1", email);

		M8 u = (M8) query.getSingleResult();
		List<M8> list = new ArrayList<M8>();
		list.add(u);

		return list;
		
	}

	public List<M8> findBySchoolclass(Schoolclass sc) {
		Query query = em.createQuery("SELECT u from " + M8.class.getSimpleName() + " u where u.schoolclass=:arg1");
		query.setParameter("arg1", sc);

		List<M8> list = new ArrayList<M8>();
		list.addAll((List<M8>)query.getResultList());

		return list;
	}

}
