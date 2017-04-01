package edu.classm8web.database.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.classm8web.database.dao.common.BaseDBService;
import edu.classm8web.database.dto.Emote;
import edu.classm8web.database.dto.File;
import edu.classm8web.exception.DatabaseException;

public class EmoteService implements BaseDBService<Long,Emote>{

	private static EmoteService instance;

	private EntityManager em;
	private EntityManagerFactory emf;
	
	private EmoteService() {
		createPersistentComponents();
	}

	public static EmoteService getInstance() {
		if (EmoteService.instance == null) {
			EmoteService.instance = new EmoteService();
		}
		return EmoteService.instance;
	}
	
	@Override
	public void persist(Emote entity) throws DatabaseException {
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
			Emote itemToRemove = this.findById(id);
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
	public void removeByEntity(Emote entity) throws DatabaseException {
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
	public void update(Emote entity) throws DatabaseException {
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
}		
	}

	@Override
	public Emote findById(Long id) {
		return em.find(Emote.class, id);
	}

	@Override
	public List<Emote> findAll() {
		String query = new StringBuilder("SELECT u FROM ").append(Emote.class.getSimpleName()).append(" u").toString();
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
			String query = new StringBuilder("DELETE FROM ").append(File.class.getSimpleName()).append(" u").toString();
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