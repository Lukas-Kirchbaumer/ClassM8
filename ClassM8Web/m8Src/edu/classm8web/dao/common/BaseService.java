package edu.classm8web.dao.common;

import javax.ejb.Local;

@Local
public interface BaseService {
	Object createOrUpdate(Object object);
	void remove(Object object);
	Object find(Object id);
}
