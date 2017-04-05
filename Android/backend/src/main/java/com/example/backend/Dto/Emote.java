package com.example.backend.Dto;

public class Emote {

	private long id;

	private String fileName;

	private String shortString;

	private long contentSize;

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

	@Override
	public String toString() {
		return "Emote{" +
				"id=" + id +
				", fileName='" + fileName + '\'' +
				", shortString='" + shortString + '\'' +
				", contentSize=" + contentSize +
				", referencedSchoolclass=" + referencedSchoolclass +
				'}';
	}
}
