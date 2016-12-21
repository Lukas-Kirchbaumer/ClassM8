package edu.classm8web.database.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.classm8web.database.dao.common.BaseDBService;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.exception.DatabaseException;

public class SchoolclassService implements BaseDBService<Long, Schoolclass> {

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
	public void persist(Schoolclass entity) throws DatabaseException {
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
			Schoolclass itemToRemove = this.findById(id);
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
	public void removeByEntity(Schoolclass entity) throws DatabaseException {
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
	public void update(Schoolclass entity) throws DatabaseException {
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public Schoolclass findById(Long id) {
		return em.find(Schoolclass.class, id);
	}

	@Override
	public List<Schoolclass> findAll() {
		String query = new StringBuilder("SELECT u FROM ").append(Schoolclass.class.getSimpleName()).append(" u")
				.toString();
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
			String query = new StringBuilder("DELETE FROM ").append(Schoolclass.class.getSimpleName()).append(" u")
					.toString();
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

}
