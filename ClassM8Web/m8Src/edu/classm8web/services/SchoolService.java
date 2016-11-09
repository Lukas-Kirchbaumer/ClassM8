package edu.classm8web.services;

import edu.classm8web.dao.Database;
import edu.classm8web.dao.DatabaseException;
import edu.classm8web.dto.School;

public class SchoolService {
	private static SchoolService instance;

	public static SchoolService getInstance() {
		if (SchoolService.instance == null) {
			SchoolService.instance = new SchoolService();
		}
		return SchoolService.instance;
	}
	
	public void registerSchool(School s){
		try {
			s.setId(getHighestId()+1);
			System.out.println(s.getId());
			Database.getInstance().addSchool(s);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	private long getHighestId(){
		int id = 0;
		try {
			id = Database.getInstance().getMaxSchoolId();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public School getSchoolByID(Integer idSchool) {
		School s = null;
		try {
			s = Database.getInstance().getSchoolById(idSchool);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return s;
	}
}
