package edu.classm8web.services;

import edu.classm8web.dao.Database;
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
		Database.getInstance().getMades().add(m8);
	}

	public void updateUser(M8 newm8) {
		for (int i = 0; i < Database.getInstance().getMades().size(); i++) {
			if (newm8.getId() == Database.getInstance().getMades().elementAt(i).getId()) {
				Database.getInstance().getMades().elementAt(i).setNewM8(newm8);
			}
		}
	}

	public void deleteUser(int id) {
		for (int i = 0; i < Database.getInstance().getMades().size(); i++) {
			if (id == Database.getInstance().getMades().elementAt(i).getId()) {
				Database.getInstance().getMades().remove(i);
			}
		}
	}

	public M8 getMade(int m8id) {
		M8 ret = null;
		for (int i = 0; i < Database.getInstance().getMades().size(); i++) {
			if (m8id == Database.getInstance().getMades().elementAt(i).getId()) {
				ret = Database.getInstance().getMades().elementAt(i);
			}
		}
		return ret;
	}
	
	private long getHighestId(){
		long highest = 0;
		for (int i = 0; i < Database.getInstance().getMades().size(); i++) {
			if (highest < Database.getInstance().getMades().elementAt(i).getId()) {
				highest = Database.getInstance().getMades().elementAt(i).getId();
			}
		}
		return highest;
	}

}
