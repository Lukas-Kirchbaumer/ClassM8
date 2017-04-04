package edu.classm8web.database.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EmoteMate")
public class Emote {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="fileid", unique = true)
	private long id;
	
	private String fileName;
	
	@Id
	private String shortString;
	
	private long contentSize;

	@Id
	@ManyToOne
	@JoinColumn(name = "schoolclassid")
	private Schoolclass referencedSchoolclass;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getShortString() {
		return shortString;
	}

	public void setShortString(String shortString) {
		this.shortString = shortString;
	}

	public long getContentSize() {
		return contentSize;
	}

	public void setContentSize(long contentSize) {
		this.contentSize = contentSize;
	}

	public Schoolclass getReferencedSchoolclass() {
		return referencedSchoolclass;
	}

	public void setReferencedSchoolclass(Schoolclass referencedSchoolclass) {
		this.referencedSchoolclass = referencedSchoolclass;
	}
	
	

}
