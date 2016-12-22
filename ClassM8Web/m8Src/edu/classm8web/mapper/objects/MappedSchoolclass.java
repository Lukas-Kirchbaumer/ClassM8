package edu.classm8web.mapper.objects;

import java.util.Vector;

import edu.classm8web.database.dto.M8;

public class MappedSchoolclass {

	private long id;

	private Vector<MappedM8> classMembers = new Vector<MappedM8>();
	
	private String name;
	
	private String room;
	
	private M8 president;
	
	private M8 presidentDeputy;
	
	private String school;
	
	public MappedSchoolclass() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Vector<MappedM8> getClassMembers() {
		return classMembers;
	}

	public void setClassMembers(Vector<MappedM8> classMembers) {
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
	
	public void setNewClass(MappedSchoolclass sc){
		this.classMembers = sc.classMembers;
		this.name = sc.name;
		this.room = sc.room;
		this.president = sc.president;
		this.presidentDeputy = sc.presidentDeputy;
		this.school = sc.school;
	}
	

	
}
