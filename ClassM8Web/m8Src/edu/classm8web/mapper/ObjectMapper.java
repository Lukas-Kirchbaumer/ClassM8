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
		msc.setPresident(ObjectMapper.mapForSchoolClass(sc.getPresident()));
		msc.setPresidentDeputy(ObjectMapper.mapForSchoolClass(sc.getPresidentDeputy()));
		msc.setRoom(sc.getRoom());
		msc.setSchool(sc.getSchool());
		Vector<MappedM8> mscClassMembers = new Vector<MappedM8>();
		
		for(M8 m8 : sc.getClassMembers()){
			mscClassMembers.add(ObjectMapper.mapForSchoolClass(m8));
		}
		
		msc.setClassMembers(mscClassMembers);
		return msc;
	}

	public static MappedM8 mapForSchoolClass(M8 m8) {
		MappedM8 mm8 = new MappedM8();
		mm8.setNewM8NoSchoolClass(m8);
		return mm8;
	}
	
	public static MappedSchoolclass mapForM8(Schoolclass sc){
		MappedSchoolclass mmc = new MappedSchoolclass();
		mmc.setId(sc.getId());
		mmc.setName(sc.getName());
		mmc.setRoom(sc.getRoom());
		mmc.setSchool(sc.getSchool());
		
		for(M8 member : sc.getClassMembers()){
			mmc.getClassMembers().add(ObjectMapper.mapForSchoolClass(member));
		}
		
		mmc.setPresident(ObjectMapper.mapForSchoolClass(sc.getPresident()));
		mmc.setPresidentDeputy(ObjectMapper.mapForSchoolClass(sc.getPresidentDeputy()));
		
		return mmc;
		
	}
	
	public static MappedM8 map(M8 m8){
		MappedM8 mm8 = new MappedM8();
		mm8.setId(m8.getId());
		mm8.setEmail(m8.getEmail());
		mm8.setFirstname(m8.getFirstname());
		mm8.setLastname(m8.getLastname());
		mm8.setEmail(m8.getEmail());
		mm8.setHasVoted(m8.isHasVoted());
		mm8.setVotes(mm8.getVotes());
		
		mm8.setSchoolclass(ObjectMapper.mapForM8(m8.getSchoolclass()));
		
		return mm8;
	}
	
	public static Vector<MappedM8> mapM8s(List<M8> m8s){
		Vector<MappedM8> maped = new Vector<>();
		for(M8 m8 : m8s){
			maped.add(ObjectMapper.map(m8));
		}
		return maped;
		
	}
	
	public static Vector<MappedSchoolclass> mapSchoolclasses(List<Schoolclass> schoolclasses){
		Vector<MappedSchoolclass> maped = new Vector<>();
		for(Schoolclass s : schoolclasses){
			maped.add(ObjectMapper.map(s));
		}
		return maped;
		
	}



}
