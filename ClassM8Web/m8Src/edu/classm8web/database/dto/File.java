package edu.classm8web.database.dto;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FileMate")
public class File implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2622564174991058878L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="fileid")
	private long id;
	
	private String fileName;
	
	private Date uploadDate;
	
	private long contentSize;
	
	private String contentType;
	
	@ManyToOne
	@JoinColumn(name = "schoolclassid")
	private Schoolclass referencedSchoolclass;
	
	public File() {}

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

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public long getContentSize() {
		return contentSize;
	}

	public void setContentSize(long size) {
		this.contentSize = size;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Schoolclass getReferencedSchoolclass() {
		return referencedSchoolclass;
	}

	public void setReferencedSchoolclass(Schoolclass referencedSchoolclass) {
		this.referencedSchoolclass = referencedSchoolclass;
	}
	
	
}
