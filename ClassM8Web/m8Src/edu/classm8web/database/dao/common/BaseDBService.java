package edu.classm8web.database.dao.common;

import java.util.List;

import javax.persistence.EntityManager;


public interface BaseDBService<K, E> {      
	void persist(E entity);
	void removeById (K id);
	void removeByEntity(E entity);
	void update(E entity);
	E findById(K id);
	List<E> findAll();
	void createPersistentComponents();
	void closeEntityManagerFactory();
	void closeEntityManager();
	void deleteAll();
	EntityManager getEm();

}