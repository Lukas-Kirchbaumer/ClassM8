package edu.classm8web.rs.result;

import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
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
