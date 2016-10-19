package edu.classm8web.rs.result;

public abstract class Result {

	private boolean success;
	
	public Result() {}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
