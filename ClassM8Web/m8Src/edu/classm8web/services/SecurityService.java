package edu.classm8web.services;

import edu.classm8web.dao.Database;
import edu.classm8web.dto.M8;

public class SecurityService {
	private static SecurityService instance;

	public static SecurityService getInstance() {
		if (SecurityService.instance == null) {
			SecurityService.instance = new SecurityService();
		}
		return SecurityService.instance;
	}

	public long checkLogin(String email, String password){
		long ret = -1;
		for (M8 m8 : Database.getInstance().getMades()) {
			if (m8.getEmail().equals(email)) {
				if(m8.getPassword().equals(password)){
					ret = m8.getId();
				}
			}
		}
		return ret;
	}
}
