package edu.classm8web.services;

import java.util.Vector;

import edu.classm8web.dao.Database;
import edu.classm8web.dto.M8;
import edu.classm8web.exception.DatabaseException;

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

	public void registerUser(M8 m8) throws DatabaseException {

		m8.setId(getHighestId() + 1);
		m8.setVotes(0);
		m8.setHasVoted(false);

		Database.getInstance().addStudent(m8);
	}

	public void updateUser(M8 newm8) throws DatabaseException {

		Database.getInstance().updateStudent(newm8);

	}

	public void deleteUser(long id) throws DatabaseException {

		Database.getInstance().removeStudentById((int) id);

	}

	private long getHighestId() throws DatabaseException {
		long id = 0;

		id = Database.getInstance().getMaxM8Id();
		return id;
	}

	public M8 getM8(long m8id) throws DatabaseException {
		M8 mate = null;

		mate = Database.getInstance().getStudentById((int) m8id);

		return mate;
	}

	public Vector<M8> getAllM8s() throws DatabaseException {
		Vector<M8> mates = null;

		mates = Database.getInstance().getAllMates();

		return mates;
	}

}
