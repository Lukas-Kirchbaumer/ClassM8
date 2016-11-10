package edu.classm8web.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import edu.classm8web.dto.M8;
import edu.classm8web.dto.Schoolclass;
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
		Vector<M8> mscClassMembers = new Vector<M8>();
		
		for(M8 m8 : sc.getClassMembers().values()){
			mscClassMembers.add(m8);
		}
		
		msc.setClassMembers(mscClassMembers);
		return msc;
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
