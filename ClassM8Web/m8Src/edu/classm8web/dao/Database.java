package edu.classm8web.dao;

import java.util.Vector;

import edu.classm8web.dto.M8;
import edu.classm8web.dto.Schoolclass;

public class Database {

	private static Database instance;

	private Vector<M8> mades = new Vector<>();
	private Vector<Schoolclass> classes = new Vector<>();

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

		mades.add(kramer);
		mades.add(max);

		Schoolclass b5 = new Schoolclass();
		b5.getClassMembers().add(max);
		b5.setId(1);
		b5.setName("5bhifs");
		b5.setPresident(max);
		b5.setPresidentDeputy(kramer);
		b5.setRoom("1337");
		b5.setSchool("HTÖL");
		
		classes.addElement(b5);
	}

	public Vector<M8> getMades() {
		return mades;
	}

	public void setMades(Vector<M8> mades) {
		this.mades = mades;
	}

	public static Database getInstance() {
		if (Database.instance == null) {
			Database.instance = new Database();
		}
		return Database.instance;
	}

	public Vector<Schoolclass> getSchoolClasses() {
		return classes;
	}

	public void setSchoolClasses(Vector<Schoolclass> classes) {
		this.classes = classes;
	}

}
