package edu.classm8web.dao;

import java.util.Vector;

import edu.classm8web.dto.M8;


public class Database {

	private static Database instance;
	
	private Vector<M8> mades = new Vector<>();

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
	}
	
	public Vector<M8> getMades() {
		return mades;
	}

	public void setMades(Vector<M8> mades) {
		this.mades = mades;
	}
	
	  public static Database getInstance () {
		    if (Database.instance == null) {
		      Database.instance = new Database ();
		    }
		    return Database.instance;
		  }
	
}
