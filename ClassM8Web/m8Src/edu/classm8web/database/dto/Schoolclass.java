package edu.classm8web.database.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
public class Schoolclass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -69655466883930376L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="schoolclassid")
	private long id;
	
	@OneToMany(mappedBy = "schoolclass")
	private List<M8> classMembers;
	
	@OneToMany(mappedBy = "referencedSchoolclass")
	private List<File> files;
	
	private String name;
	
	private String room;
	
	@OneToOne
	@JoinColumn(name="presidentid")
	private M8 president;
	
	@OneToOne
	@JoinColumn(name="presidentdeputyid")
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
