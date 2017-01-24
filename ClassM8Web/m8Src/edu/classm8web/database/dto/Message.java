package edu.classm8web.database.dto;

import java.sql.Date;

import javax.persistence.Embeddable;

@Embeddable
public class Message {

	private String content;
	
	private String sender;
	
	private Date dateTime;
	
	public Message() {}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
