package edu.classm8web.rs.result;

import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;

import edu.classm8web.dto.Schoolclass;
import edu.classm8web.mapper.objects.MappedSchoolclass;

@XmlRootElement
public class SchoolclassResult extends Result{
	private Vector<MappedSchoolclass> schoolclasses = new Vector<>();
	
	public SchoolclassResult() {
		// TODO Auto-generated constructor stub
	}
	
	public Vector<MappedSchoolclass> getSchoolclasses() {
		return schoolclasses;
	}
	
	public void setSchoolclasses(Vector<MappedSchoolclass> schoolclasses) {
		this.schoolclasses = schoolclasses;
	}
}
