package edu.classm8web.services;

import java.util.HashMap;
import java.util.Vector;

import edu.classm8web.dao.Database;
import edu.classm8web.dto.M8;
import edu.classm8web.dto.Schoolclass;
import edu.classm8web.exception.DatabaseException;

public class SchoolclassService {

	private static SchoolclassService instance;

	public static SchoolclassService getInstance() {
		if (SchoolclassService.instance == null) {
			SchoolclassService.instance = new SchoolclassService();
		}
		return SchoolclassService.instance;
	}

	private SchoolclassService() {
	}

	public void registerSchoolclass(Schoolclass sc, long m8id) throws DatabaseException {
		sc.setId(getHighestId() + 1);
		sc.setPresident(null);
		sc.setPresidentDeputy(null);

		Database.getInstance().addClass(sc);
		Database.getInstance().addStudentToClass((int) m8id, (int) sc.getId());
	}

	public void updateSchoolclass(Schoolclass sc) throws DatabaseException {
		Database.getInstance().updateClass(sc);
	}

	public void deleteSchoolclass(long id) throws DatabaseException {
		Database.getInstance().deleteSchoolClass((int) id);
	}

	public void addM8ToSchoolClass(long scid, long m8id) throws DatabaseException {
		Database.getInstance().addStudentToClass((int) m8id, (int) scid);
	}

	public void removeM8FromSchoolClass(long scid, long m8id) throws DatabaseException {
		Database.getInstance().removeEntryOfStudentInClass((int) m8id);
	}

	public void setPresidentInSchoolClass(int scid, int m8id) throws DatabaseException {

		Schoolclass s = Database.getInstance().getSchoolclassOfStudentbyStudentId(m8id);
		System.out.println(s);
		if (s.getId() == scid)
			s.setPresident(UserService.getInstance().getM8(m8id));
		else
			throw new DatabaseException("Student not in Class");
		System.out.println(s);
		Database.getInstance().updateClass(s);
	}

	public void setPresidentDebutyInSchoolClass(int scid, int m8id) throws DatabaseException {
		Schoolclass s = Database.getInstance().getSchoolclassOfStudentbyStudentId(m8id);
		System.out.println(s);
		if (s.getId() == scid)
			s.setPresidentDeputy(UserService.getInstance().getM8(m8id));
		else
			throw new DatabaseException("Student not in Class");
		System.out.println(s);
		Database.getInstance().updateClass(s);

	}

	public HashMap<Long, M8> getMades(int scid) throws DatabaseException {
		HashMap<Long, M8> mates = null;
		mates = Database.getInstance().getClassMatesOfStudent(scid);

		return mates;
	}

	public Schoolclass getSchoolClass(int scid) throws DatabaseException {
		Schoolclass c = null;
		c = Database.getInstance().getSchoolclassById(scid);

		return c;
	}

	public Schoolclass getSchoolClassByM8(long m8id) throws DatabaseException {
		Schoolclass ret = null;

		ret = Database.getInstance().getSchoolclassOfStudentbyStudentId((int) m8id);
		return ret;
	}

	private long getHighestId() throws DatabaseException {
		int id = 0;

		id = Database.getInstance().getMaxSchoolclassId();

		return id;
	}

	public Vector<Schoolclass> getAllSchoolClasses() throws DatabaseException{
		
		return Database.getInstance().getAllSchoolclasses();
	}

}
