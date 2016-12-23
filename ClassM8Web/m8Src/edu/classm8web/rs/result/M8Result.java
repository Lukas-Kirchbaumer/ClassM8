package edu.classm8web.rs.result;

import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;

import edu.classm8web.mapper.objects.MappedM8;

@XmlRootElement
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
