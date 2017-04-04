package edu.classm8web.rs.result;

import edu.classm8web.mapper.objects.MappedM8;

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
