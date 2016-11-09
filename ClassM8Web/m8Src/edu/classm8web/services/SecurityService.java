package edu.classm8web.services;

import dao.Database;
import dao.DatabaseException;
import dto.M8;

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
		M8 dude = null;
		try {
			dude = Database.getInstance().getStudentbyPasswordAndEMail(email, password);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		if(dude != null)
			ret = dude.getId();
		
		return ret;
	}
}
