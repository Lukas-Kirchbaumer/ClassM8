package edu.classm8web.services;

import edu.classm8web.dao.Database;
import edu.classm8web.dto.School;
import edu.classm8web.exception.DatabaseException;

public class SchoolService {
	private static SchoolService instance;

	public static SchoolService getInstance() {
		if (SchoolService.instance == null) {
			SchoolService.instance = new SchoolService();
		}
		return SchoolService.instance;
	}

	public void registerSchool(School s) throws DatabaseException {
		s.setId(getHighestId() + 1);
		System.out.println(s.getId());
		Database.getInstance().addSchool(s);
	}

	private long getHighestId() throws DatabaseException {
		int id = 0;
		id = Database.getInstance().getMaxSchoolId();
		return id;
	}

	public School getSchoolByID(Integer idSchool) throws DatabaseException {
		School s = null;
		s = Database.getInstance().getSchoolById(idSchool);
		return s;
	}
}
