package com.example.backend.Dto;

import java.io.Serializable;
import java.sql.Date;

public class File implements Serializable{

	private static final long serialVersionUID = 2622564174991058878L;

	private long id;
	
	private String fileName;
	
	private Date uploadDate;
	
	private long contentSize;
	
	private String contentType;

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

	@Override
	public String toString(){
		return fileName;
	}
	
	
}
