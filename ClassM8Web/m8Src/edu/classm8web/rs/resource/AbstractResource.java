package edu.classm8web.rs.resource;
import javax.servlet.http.HttpServletRequest;

import edu.classm8web.database.dao.FileService;
import edu.classm8web.database.dao.MateService;
import edu.classm8web.database.dao.SchoolclassService;
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
	
	protected void workaround() {
		if(MateService.getInstance().getEm().isOpen()){
			MateService.getInstance().closeEntityManager();
			MateService.getInstance().closeEntityManagerFactory();
		}
		
		if(SchoolclassService.getInstance().getEm().isOpen()){
			SchoolclassService.getInstance().closeEntityManager();
			SchoolclassService.getInstance().closeEntityManagerFactory();
		}
		
		if(FileService.getInstance().getEm().isOpen()){
			FileService.getInstance().closeEntityManager();
			FileService.getInstance().closeEntityManagerFactory();
		}
		
		MateService.getInstance().createPersistentComponents();
		SchoolclassService.getInstance().createPersistentComponents();
		FileService.getInstance().createPersistentComponents();

	}
	
	public void logMessage(Class<?> res, HttpServletRequest r, String meth){
		System.out.println("[Service Info] : @" + r.getRemoteAddr() + " -> " + r.getMethod() + " : " + res.getSimpleName() + " // action : " + meth);
	}
}
