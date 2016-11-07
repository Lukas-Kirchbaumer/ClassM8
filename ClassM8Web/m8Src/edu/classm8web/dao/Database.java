package edu.classm8web.dao;

import java.util.HashMap;

import edu.classm8web.dto.M8;
import edu.classm8web.dto.Schoolclass;

public class Database {

	private static Database instance;
	
	public static Database getInstance() {
		if (Database.instance == null) {
			Database.instance = new Database();
		}
		return Database.instance;
	}

	private HashMap<Long,M8> mades = new HashMap<>();
	private HashMap<Long,Schoolclass> classes = new HashMap<>();

	private Database() {
		M8 max = new M8();
		max.setEmail("max.alskdf@slkdfj.at");
		max.setId(1);
		max.setFirstname("Max");
		max.setLastname("Haider");
		max.setHasVoted(false);
		max.setPassword("danke");
		max.setVotes(0);

		M8 kramer = new M8();
		kramer.setEmail("kramer.alskdf@slkdfj.at");
		kramer.setId(2);
		kramer.setFirstname("Lukas");
		kramer.setLastname("Kramer");
		kramer.setHasVoted(true);
		kramer.setPassword("dnke");
		kramer.setVotes(324);

		mades.put(kramer.getId(), kramer);
		mades.put(max.getId(), max);

		Schoolclass b5 = new Schoolclass();
		b5.getClassMembers().put(max.getId(), max);
		b5.setId(1);
		b5.setName("5bhifs");
		b5.setPresident(max);
		b5.setPresidentDeputy(kramer);
		b5.setRoom("1337");
		b5.setSchool("HTÖL");
		
		classes.put(b5.getId(), b5);
	}
	
	
	
	public HashMap<Long, Schoolclass> getSchoolclasses() {
		return classes;
	}
	
	public void setSchoolclasses(HashMap<Long, Schoolclass> classes) {
		this.classes = classes;
	}
	

	public HashMap<Long,M8> getMades() {
		return mades;
	}

	public void setMades(HashMap<Long,M8> mades) {
		this.mades = mades;
	}




}
