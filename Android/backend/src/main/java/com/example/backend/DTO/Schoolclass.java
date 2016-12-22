package com.example.backend.Dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;




public class Schoolclass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -69655466883930376L;

	private long id;

	private List<M8> classMembers;

	private List<File> files;
	
	private String name;
	
	private String room;

	private M8 president;

	private M8 presidentDeputy;
	
	private String school;
	
	public Schoolclass() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<M8> getClassMembers() {
		return classMembers;
	}

	public void setClassMembers(List<M8> classMembers) {
		this.classMembers = classMembers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public M8 getPresident() {
		return president;
	}

	public void setPresident(M8 president) {
		this.president = president;
	}

	public M8 getPresidentDeputy() {
		return presidentDeputy;
	}

	public void setPresidentDeputy(M8 presidentDeputy) {
		this.presidentDeputy = presidentDeputy;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public void setNewClass(Schoolclass sc){
		//TODO: @kirche check for null values!
		this.classMembers = sc.classMembers;
		this.name = sc.name;
		this.room = sc.room;
		this.president = sc.president;
		this.presidentDeputy = sc.presidentDeputy;
		this.school = sc.school;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
	
}