package com.example.backend.Results;

import com.example.backend.MappedObjects.MappedM8;

import java.util.Vector;


public class M8Result extends Result{
	
	private Vector<MappedM8> content = new Vector<>();
	
	public M8Result() {}

	public Vector<MappedM8> getContent() {
		return content;
	}

	public void setContent(Vector<MappedM8> content) {
		this.content = content;
	}
	

}
