package edu.classm8web.mapper.objects;

import java.sql.Date;

import edu.classm8web.database.dto.File;

public class MappedFile {

	private long id;
	
	private String fileName;
	
	private Date uploadDate;
	
	private long contentSize;
	
	private String contentType;
	
	private MappedSchoolclass referencedSchoolclass;
	
	public MappedFile() {}

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

	public void setContentSize(long contentSize) {
		this.contentSize = contentSize;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public MappedSchoolclass getReferencedSchoolclass() {
		return referencedSchoolclass;
	}

	public void setReferencedSchoolclass(MappedSchoolclass referencedSchoolclass) {
		this.referencedSchoolclass = referencedSchoolclass;
	}
	
	public void setNewFileNoSchoolClass(File newFile){
		this.id = newFile.getId();
		this.uploadDate = newFile.getUploadDate();
		this.contentSize = newFile.getContentSize();
		this.contentType = newFile.getContentType();
		this.fileName = newFile.getFileName();
		this.referencedSchoolclass = null;
	}
}
