package com.example.backend.MappedObjects;

import com.example.backend.Dto.File;
import com.example.backend.Dto.Schoolclass;

import java.util.Vector;


public class MappedSchoolclass {

	private long id;

	private Vector<MappedM8> classMembers = new Vector<MappedM8>();
	
	private String name;
	
	private String room;
	
	private MappedM8 president;
	
	private MappedM8 presidentDeputy;
	
	private String school;
	
	private Vector<MappedFile> files = new Vector<MappedFile>();
	
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

	public MappedM8 getPresident() {
		return president;
	}

	public void setPresident(MappedM8 president) {
		this.president = president;
	}

	public MappedM8 getPresidentDeputy() {
		return presidentDeputy;
	}

	public void setPresidentDeputy(MappedM8 presidentDeputy) {
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
	
	public void setNewClassNoM8(MappedSchoolclass sc){
		this.classMembers = null;
		this.name = sc.name;
		this.room = sc.room;
		this.president = sc.president;
		this.presidentDeputy = sc.presidentDeputy;
		this.school = sc.school;
	}

	public Vector<MappedFile> getFiles() {
		return files;
	}

	public void setFiles(Vector<MappedFile> files) {
		this.files = files;
	}

	public Schoolclass toSchoolClass() {
		Schoolclass s = new Schoolclass();
		s.setName(this.getName());
		s.setRoom(this.getRoom());
		s.setSchool(this.getSchool());

		for(MappedFile f : this.files){
			s.getFiles().add(f.toFile());
		}

		s.setPresident(this.president.toM8());
		s.setPresidentDeputy(this.presidentDeputy.toM8());
		return s;
	}
}
