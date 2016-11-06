package edu.classm8web.rs.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginResult extends Result {
	private long id;
	
	public LoginResult() {
		// TODO Auto-generated constructor stub
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

}
