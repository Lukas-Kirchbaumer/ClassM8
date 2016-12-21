package edu.classm8web.database.dao.common;

import java.util.List;

import javax.persistence.EntityManager;

import edu.classm8web.exception.DatabaseException;


public interface BaseDBService<K, E> {      
	void persist(E entity) throws DatabaseException;
	void removeById (K id) throws DatabaseException;
	void removeByEntity(E entity) throws DatabaseException;
	void update(E entity) throws DatabaseException;
	E findById(K id);
	List<E> findAll();
	void createPersistentComponents();
	void closeEntityManagerFactory();
	void closeEntityManager();
	void deleteAll() throws DatabaseException;
	EntityManager getEm();

}