package edu.classm8web.services;

import java.util.ArrayList;

import edu.classm8web.database.dao.MateService;
import edu.classm8web.database.dto.M8;
import edu.classm8web.exception.DatabaseException;

public class SecurityService {
	private static SecurityService instance;

	public static SecurityService getInstance() {
		if (SecurityService.instance == null) {
			SecurityService.instance = new SecurityService();
		}
		return SecurityService.instance;
	}

	public long checkLogin(String email, String password) throws DatabaseException {
		
		long ret = -1;
		
		email = email.toLowerCase();
		
		ArrayList<M8> result = (ArrayList<M8>) MateService.getInstance().findByEmail(email);
		M8 dude = result.get(0);
		
		if(dude != null){
			if(dude.getPassword().equals(password)){
				ret = dude.getId();
			}
		} else {
			//false
		}


		return ret;
	}
}