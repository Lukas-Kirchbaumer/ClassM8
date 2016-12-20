package edu.classm8web.services;

import edu.classm8web.database.dao.Database;
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
		M8 dude = null;

		dude = Database.getInstance().getStudentbyPasswordAndEMail(email, password);

		if (dude != null)
			ret = dude.getId();

		return ret;
	}
}
