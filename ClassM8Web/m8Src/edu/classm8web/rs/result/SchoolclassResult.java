package edu.classm8web.rs.result;

import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;

import edu.classm8web.dto.Schoolclass;

@XmlRootElement
public class SchoolclassResult extends Result{
	private Vector<Schoolclass> schoolclasses = new Vector<>();
	
	public SchoolclassResult() {
		// TODO Auto-generated constructor stub
	}
	
	public Vector<Schoolclass> getSchoolclasses() {
		return schoolclasses;
	}
	
	public void setSchoolclasses(Vector<Schoolclass> schoolclasses) {
		this.schoolclasses = schoolclasses;
	}
}
