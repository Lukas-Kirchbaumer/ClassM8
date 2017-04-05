package com.example.backend.Results;

import com.example.backend.Dto.Position;

import java.util.Vector;


public class PositionResult extends Result {

	private Vector<Position> content = new Vector<>();

	
	public PositionResult() {
	}


	public Vector<Position> getContent() {
		return content;
	}


	public void setContent(Vector<Position> content) {
		this.content = content;
	}
	
	
}
