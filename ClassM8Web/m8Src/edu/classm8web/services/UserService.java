package edu.classm8web.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.classm8web.dao.Database;
import edu.classm8web.dto.M8;
import edu.classm8web.dto.Schoolclass;

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
		Database.getInstance().getMades().put(m8.getId(), m8);
	}

	public void updateUser(M8 newm8) {
		Database.getInstance().getMades().get(newm8.getId()).setNewM8(newm8);;
	}

	public void deleteUser(long id) {
		Database.getInstance().getMades().remove(id);
	}

	public M8 getMade(long m8id) {
		return Database.getInstance().getMades().get(m8id);
	}
	
	private long getHighestId(){
		List<Long> sortedKeys=new ArrayList<Long>(Database.getInstance().getMades().keySet());
		Collections.sort(sortedKeys);
		return Collections.max(sortedKeys);
	}
	
	public M8 getM8(long m8id){
		return Database.getInstance().getMades().get(m8id);
	}

}
