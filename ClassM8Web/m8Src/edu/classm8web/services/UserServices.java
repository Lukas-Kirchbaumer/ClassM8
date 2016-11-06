package edu.classm8web.services;

import edu.classm8web.dao.Database;
import edu.classm8web.dto.M8;

public class UserServices {
	
	public void registerUser(M8 m8){
		Database.getInstance().getMades().add(m8);
	}
	
	public void updateUser(M8 m8, int id){
		for(M8 m8s : Database.getInstance().getMades()){
			
		}
	}
	
	
	
	

}
