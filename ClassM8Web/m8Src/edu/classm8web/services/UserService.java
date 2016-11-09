package edu.classm8web.services;

import edu.classm8web.dao.Database;
import edu.classm8web.dao.DatabaseException;
import edu.classm8web.dto.M8;

public class UserService {

	private static UserService instance;

	public static UserService getInstance() {
		if (UserService.instance == null) {
			UserService.instance = new UserService();
		}
		return UserService.instance;
	}

	private UserService() {
	}

	public void registerUser(M8 m8) {
		
		m8.setId(getHighestId()+1);
		m8.setVotes(0);
		m8.setHasVoted(false);
		
		try {
			Database.getInstance().addStudent(m8);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public void updateUser(M8 newm8) {
		try {
			Database.getInstance().updateStudent(newm8);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(long id) {
		try {
			Database.getInstance().removeStudentById((int)id);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public M8 getMade(long m8id) {
		M8 mate = null;
		try {
			mate= Database.getInstance().getStudentById((int)m8id);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return mate;
	}
	
	private long getHighestId(){
		long id = 0;
		try {
			id = Database.getInstance().getMaxM8Id();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public M8 getM8(long m8id){
		M8 mate = null;
		try {
			mate= Database.getInstance().getStudentById((int)m8id);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return mate;
	}

}
