package edu.classm8web.services;

import java.util.HashMap;

import edu.classm8web.dao.Database;
import edu.classm8web.dao.DatabaseException;
import edu.classm8web.dto.M8;
import edu.classm8web.dto.Schoolclass;

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
	
	public void registerSchoolclass(Schoolclass sc, long m8id){
		sc.setId(getHighestId()+1);
		sc.setPresident(null);
		sc.setPresidentDeputy(null);
		
		try {
			Database.getInstance().addClass(sc);
			Database.getInstance().addStudentToClass((int)m8id,(int) sc.getId());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateSchoolclass(Schoolclass sc){
		try {
			Database.getInstance().updateClass(sc);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteSchoolclass(long id){
		
		try {
			Database.getInstance().deleteSchoolClass((int)id);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addM8ToSchoolClass(long scid, long m8id){
		try {
			Database.getInstance().addStudentToClass((int)m8id, (int)scid);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeM8FromSchoolClass(long scid, long m8id){
		try {
			Database.getInstance().removeEntryOfStudentInClass((int)m8id);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPresidentInSchoolClass(int scid, int m8id){
		try {
			Schoolclass s = Database.getInstance().getSchoolclassOfStudentbyStudentId(m8id);
			System.out.println(s);
			if(s.getId() == scid)
				s.setPresident(UserService.getInstance().getM8(m8id));
			else
				throw new DatabaseException("Student not in Class");
			System.out.println(s);
			Database.getInstance().updateClass(s);


		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPresidentDebutyInSchoolClass(int scid, int m8id) {
		try {
			Schoolclass s = Database.getInstance().getSchoolclassOfStudentbyStudentId(m8id);
			System.out.println(s);
			if (s.getId() == scid)
				s.setPresidentDeputy(UserService.getInstance().getM8(m8id));
			else
				throw new DatabaseException("Student not in Class");
			System.out.println(s);
			Database.getInstance().updateClass(s);

		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public HashMap<Long, M8> getMades(int scid){
		HashMap<Long, M8> mates = null;
		 try {
			mates =  Database.getInstance().getClassMatesOfStudent(scid);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mates;
	}
	
	public Schoolclass getSchoolClass(int scid){
		Schoolclass c = null;
		try {
			c = Database.getInstance().getSchoolclassById(scid);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	
	public Schoolclass getSchoolClassByM8(long m8id){
		Schoolclass ret = null;
		try {
			ret = Database.getInstance().getSchoolclassOfStudentbyStudentId((int)m8id);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	
	private long getHighestId(){
		int id = 0;
		try {
			id = Database.getInstance().getMaxSchoolclassId();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
}
