package com.example.backend.Dto;

import java.io.Serializable;



public class M8 implements Serializable{

	
	private static final long serialVersionUID = 7576437934172296816L;

	private long id;
	
	private String firstname;
	
	private String lastname;

	private String email;
	
	private String password;
	
	private boolean hasVoted;
	
	private int votes;

	private Schoolclass schoolclass;
	
	
	public M8() {}
	

	public M8(long id, String firstname, String lastname, String email, String password, boolean hasVoted, int votes, Schoolclass schoolclass) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.hasVoted = hasVoted;
		this.votes = votes;
		this.schoolclass = schoolclass;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isHasVoted() {
		return hasVoted;
	}

	public void setHasVoted(boolean hasVoted) {
		this.hasVoted = hasVoted;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	public void setNewM8(M8 newm8){
		this.firstname = newm8.firstname;
		this.lastname = newm8.lastname;
		this.email = newm8.email;
		this.password = newm8.password;
		this.hasVoted = newm8.hasVoted;
		this.votes = newm8.votes;
		this.schoolclass = newm8.schoolclass;
	}
	

	public Schoolclass getSchoolclass() {
		return schoolclass;
	}


	public void setSchoolclass(Schoolclass schoolclass) {
		this.schoolclass = schoolclass;
	}

	
}
