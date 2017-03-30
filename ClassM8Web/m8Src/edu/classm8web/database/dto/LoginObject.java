package edu.classm8web.database.dto;

public class LoginObject {
	private String ip;
	private M8 m8;
	
	public LoginObject() {

	}
	public LoginObject(String ip, M8 m8) {
		super();
		this.ip = ip;
		this.m8 = m8;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public M8 getM8() {
		return m8;
	}
	public void setM8(M8 m8) {
		this.m8 = m8;
	}
	
	
	
}
