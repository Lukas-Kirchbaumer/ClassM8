package edu.classm8web.rs.result;

import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;

import edu.classm8web.database.dto.M8;

@XmlRootElement
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
