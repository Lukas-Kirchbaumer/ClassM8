package com.example.backend.Results;

import com.example.backend.Dto.MappedSchoolclass;
import com.example.backend.Dto.Schoolclass;

import java.util.Vector;

public class SchoolclassResult extends Result{
	private Vector<MappedSchoolclass> schoolclasses = new Vector<>();
	
	public SchoolclassResult() {
	}
	
	public Vector<MappedSchoolclass> getSchoolclasses() {
		return schoolclasses;
	}
	
	public void setSchoolclasses(Vector<MappedSchoolclass> schoolclasses) {
		this.schoolclasses = schoolclasses;
	}
}
