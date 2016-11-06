package edu.classm8web.dao.common;

import java.util.List;

import javax.ejb.Local;

@Local
public interface BaseService<K, E> {
	   void persist(E entity);      
	   void remove(E entity);      
	   E findById(K id);
	   List findAll();
	   void update(E entity);
}
