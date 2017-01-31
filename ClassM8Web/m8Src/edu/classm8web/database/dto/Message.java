package edu.classm8web.database.dto;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Message implements Comparable<Message>{

	private String content;
	
	private String sender;
	
	private Timestamp dateTime;
	
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

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public int compareTo(Message m) {
		return this.dateTime.compareTo(m.dateTime);
	}
	
	
}
