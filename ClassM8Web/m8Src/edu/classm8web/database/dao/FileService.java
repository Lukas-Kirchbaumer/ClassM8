package edu.classm8web.database.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import edu.classm8web.database.dao.common.BaseDBService;
import edu.classm8web.database.dto.File;

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
	public void persist(File entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeByEntity(File entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(File entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public File findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createPersistentComponents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeEntityManagerFactory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeEntityManager() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return null;
	}

}
