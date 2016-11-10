package edu.classm8web.rs.resource;
import edu.classm8web.rs.result.Error;
import edu.classm8web.rs.result.Result;

public abstract class AbstractResource {

	public void handelAndThrowError(Exception e, Result r){
		Error error = new Error();
		error.setErrorCode(0); //TODO: define errors in this appl
		error.setErrorMessage(e.getMessage());
		r.setError(error);
		r.setSuccess(false);
		
		e.printStackTrace();
	}
}
