package com.example.backend.MappedObjects;

import com.example.backend.Dto.File;

import java.sql.Date;

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

	public File toFile() {
		File f = new File();
		f.setContentSize(this.contentSize);
		f.setContentType(this.contentType);
		f.setFileName(this.fileName);
		f.setId(this.getId());
		try {
			f.setReferencedSchoolclass(this.getReferencedSchoolclass().toSchoolClass());
		}catch (Exception e){
			e.printStackTrace();
		}
		f.setUploadDate(this.uploadDate);
		return f;
	}
}
