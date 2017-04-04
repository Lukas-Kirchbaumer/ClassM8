package edu.classm8web.database.dao;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.classm8web.database.dao.common.BaseDBService;
import edu.classm8web.database.dto.File;
import edu.classm8web.exception.DatabaseException;

public class FileService implements BaseDBService<Long,File>{

	private static FileService instance;

	private EntityManager em;
	private EntityManagerFactory emf;
	
	private FileService() {
		createPersistentComponents();
	}

	public static FileService getInstance() {
		if (FileService.instance == null) {
			FileService.instance = new FileService();
		}
		return FileService.instance;
	}
	
	@Override
	public void persist(File entity) throws DatabaseException {
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
			File itemToRemove = this.findById(id);
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
	public void removeByEntity(File entity) throws DatabaseException {
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
	public void update(File entity) throws DatabaseException {
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
}		
	}

	@Override
	public File findById(Long id) {
		return em.find(File.class, id);
	}

	@Override
	public List<File> findAll() {
		String query = new StringBuilder("SELECT u FROM ").append(File.class.getSimpleName()).append(" u").toString();
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
	
	public String getConnectionSting(){
		String dbName = null;
	    Map<String, Object> map = emf.getProperties();
	    String url = (String) map.get("javax.persistence.jdbc.url");

	    return url;
	}
	
	public String getUsername(){
	    Map<String, Object> map = emf.getProperties();
	    String user = (String) map.get("javax.persistence.jdbc.user");

	    return user;
	}
	
	public String getPassword(){
	    Map<String, Object> map = emf.getProperties();
	    String pw = (String) map.get("javax.persistence.jdbc.password");

	    return pw;
	}

}
