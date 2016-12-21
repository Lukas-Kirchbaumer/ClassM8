package com.example.backend.Results;

import com.example.backend.Dto.M8;

import java.util.Vector;

public class M8Result extends Result{
	
	private Vector<M8> content = new Vector<M8>();
	
	public M8Result() {}

	public Vector<M8> getContent() {
		return content;
	}

	public void setContent(Vector<M8> content) {
		this.content = content;
	}
	

}
