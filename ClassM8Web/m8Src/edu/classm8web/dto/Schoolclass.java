package edu.classm8web.dto;

import java.io.Serializable;
import java.util.HashMap;



public class Schoolclass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -69655466883930376L;

	private long id;
	

	private HashMap<Long, M8> classMembers = new HashMap<Long, M8>();
	
	private String name;
	
	private String room;
	
	private M8 president;
	
	private M8 presidentDeputy;
	
	private String school;
	
	public Schoolclass() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public HashMap<Long, M8> getClassMembers() {
		return classMembers;
	}

	public void setClassMembers(HashMap<Long, M8> classMembers) {
		this.classMembers = classMembers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public M8 getPresident() {
		return president;
	}

	public void setPresident(M8 president) {
		this.president = president;
	}

	public M8 getPresidentDeputy() {
		return presidentDeputy;
	}

	public void setPresidentDeputy(M8 presidentDeputy) {
		this.presidentDeputy = presidentDeputy;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public void setNewClass(Schoolclass sc){
		//TODO: @kirche check for null values!
		this.classMembers = sc.classMembers;
		this.name = sc.name;
		this.room = sc.room;
		this.president = sc.president;
		this.presidentDeputy = sc.presidentDeputy;
		this.school = sc.school;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classMembers == null) ? 0 : classMembers.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((president == null) ? 0 : president.hashCode());
		result = prime * result + ((presidentDeputy == null) ? 0 : presidentDeputy.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schoolclass other = (Schoolclass) obj;
		if (classMembers == null) {
			if (other.classMembers != null)
				return false;
		} else if (!classMembers.equals(other.classMembers))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (president == null) {
			if (other.president != null)
				return false;
		} else if (!president.equals(other.president))
			return false;
		if (presidentDeputy == null) {
			if (other.presidentDeputy != null)
				return false;
		} else if (!presidentDeputy.equals(other.presidentDeputy))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schoolclass{" +
				"id=" + id +
				", classMembers=" + classMembers +
				", name='" + name + '\'' +
				", room='" + room + '\'' +
				", president=" + president +
				", presidentDeputy=" + presidentDeputy +
				", school=" + school +
				'}';
	}
}
