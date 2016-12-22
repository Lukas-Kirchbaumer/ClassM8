package edu.classm8web.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import edu.classm8web.database.dto.M8;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.mapper.objects.MappedM8;
import edu.classm8web.mapper.objects.MappedSchoolclass;

public class ObjectMapper {

	public static MappedSchoolclass map(Schoolclass sc){
		MappedSchoolclass msc = new MappedSchoolclass();
		msc.setName(sc.getName());
		msc.setId(sc.getId());
		msc.setPresident(sc.getPresident());
		msc.setPresidentDeputy(sc.getPresidentDeputy());
		msc.setRoom(sc.getRoom());
		msc.setSchool(sc.getSchool());
		Vector<MappedM8> mscClassMembers = new Vector<MappedM8>();
		
		for(M8 m8 : sc.getClassMembers()){
			mscClassMembers.add(ObjectMapper.mapForSchoolClass(m8));
		}
		
		msc.setClassMembers(mscClassMembers);
		return msc;
	}

	private static MappedM8 mapForSchoolClass(M8 m8) {
		MappedM8 mm8 = new MappedM8();
		mm8.setNewM8NoSchoolClass(m8);
		return mm8;
	}
	
	private static MappedM8 map(M8 m8){
		MappedM8 mm8 = new MappedM8();
		mm8.setNewM8(m8);
		return mm8;
	}
	
	private static Vector<MappedM8> map(List<M8> m8s){
		
		return null;
		
	}

	public static Vector<MappedSchoolclass> map(Collection<Schoolclass> values) {
		Vector<MappedSchoolclass> mscs = new Vector<MappedSchoolclass>();
		
		for(Schoolclass sc : values){
			mscs.add(ObjectMapper.map(sc));
		}
		
		return mscs;
	}

	public static Vector<MappedSchoolclass> map(HashMap<Long, Schoolclass> allSchoolClasses) {
		Iterator it = allSchoolClasses.entrySet().iterator();
		Vector<MappedSchoolclass> ret = new Vector<MappedSchoolclass>();
		while(it.hasNext()){
			Map.Entry<Integer, Schoolclass> entry = (Entry<Integer, Schoolclass>) it.next();
			ret.add(ObjectMapper.map(entry.getValue()));
		}
		return ret;
	}
}
