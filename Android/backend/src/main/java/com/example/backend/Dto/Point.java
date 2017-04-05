package com.example.backend.Dto;

public class Point {

	private double x;
	
	private double y;
	
	
	public Point() {}


	public Point(double i, double j) {
		this.x = i;
		this.y = j;
	}


	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}
	
	
}
