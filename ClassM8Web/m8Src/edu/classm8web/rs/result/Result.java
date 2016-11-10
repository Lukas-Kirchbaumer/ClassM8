package edu.classm8web.rs.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {

	private boolean success;
	
	private Error error;
	
	public Result() {}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
	
}
