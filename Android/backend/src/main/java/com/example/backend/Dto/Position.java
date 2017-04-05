package com.example.backend.Dto;

import com.example.backend.MappedObjects.MappedM8;

public class Position {

	private Point coordinate;
	
	private MappedM8 owner;
	
	
	public Position() {
	}

	public Point getCoordinate() {
		return coordinate;
	}


	public void setCoordinate(Point coordinate) {
		this.coordinate = coordinate;
	}


	public MappedM8 getOwner() {
		return owner;
	}


	public void setOwner(MappedM8 owner) {
		this.owner = owner;
	}
}
