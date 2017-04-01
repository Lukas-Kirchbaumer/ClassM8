package edu.classm8web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import edu.classm8web.database.dto.Emote;
import edu.classm8web.database.dto.File;
import edu.classm8web.database.dto.M8;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.mapper.objects.MappedEmote;
import edu.classm8web.mapper.objects.MappedFile;
import edu.classm8web.mapper.objects.MappedM8;
import edu.classm8web.mapper.objects.MappedSchoolclass;

public class ObjectMapper {

	public static MappedSchoolclass map(Schoolclass sc){
		MappedSchoolclass msc = new MappedSchoolclass();
		msc.setName(sc.getName());
		msc.setId(sc.getId());
		if(sc.getPresident() != null) msc.setPresident(ObjectMapper.mapForSchoolClass(sc.getPresident()));
		if(sc.getPresidentDeputy() != null) msc.setPresidentDeputy(ObjectMapper.mapForSchoolClass(sc.getPresidentDeputy()));
		msc.setRoom(sc.getRoom());
		msc.setSchool(sc.getSchool());
		Vector<MappedM8> mscClassMembers = new Vector<MappedM8>();
		Vector<MappedFile> mscFiles = new Vector<MappedFile>();
		Vector<MappedEmote> mscEmotes = new Vector<MappedEmote>();
		
		for(M8 m8 : sc.getClassMembers()){
			mscClassMembers.add(ObjectMapper.mapForSchoolClass(m8));
		}
		
		for(File f : sc.getFiles()){
			mscFiles.add(ObjectMapper.mapForSchoolClasss(f));
		}
		
		for(Emote e : sc.getEmotes()){
			mscEmotes.add(ObjectMapper.mapForSchoolClasss(e));
		}
		
		msc.setFiles(mscFiles);
		msc.setClassMembers(mscClassMembers);
		return msc;
	}

	public static MappedM8 mapForSchoolClass(M8 m8) {
		MappedM8 mm8 = new MappedM8();
		mm8.setNewM8NoSchoolClass(m8);
		return mm8;
	}
	
	private static MappedFile mapForSchoolClasss(File f) {
		MappedFile mf = new MappedFile();
		mf.setNewFileNoSchoolClass(f);
		return mf;
	}
	private static MappedEmote mapForSchoolClasss(Emote e) {
		MappedEmote me = new MappedEmote();
		me.setNewEmoteNoSchoolClass(e);
		return me;
	}
	public static List<MappedEmote> map(List<Emote> emotes) {
		List<MappedEmote> mscEmotes = new ArrayList<MappedEmote>();
		for(Emote e : emotes){
			mscEmotes.add(ObjectMapper.mapForSchoolClasss(e));
		}
		return mscEmotes;
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
		
		if(sc.getPresident()!= null) mmc.setPresident(ObjectMapper.mapForSchoolClass(sc.getPresident()));
		if(sc.getPresidentDeputy()!= null) mmc.setPresidentDeputy(ObjectMapper.mapForSchoolClass(sc.getPresidentDeputy()));
		
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
		mm8.setVotes(m8.getVotes());
		
		if(m8.getSchoolclass() != null) {
			mm8.setSchoolclass(ObjectMapper.mapForM8(m8.getSchoolclass()));
		}
		
		
		return mm8;
	}
	
	public static Vector<MappedM8> mapM8s(List<M8> m8s){
		Vector<MappedM8> maped = new Vector<>();
		for(M8 m8 : m8s){
			maped.add(ObjectMapper.mapForSchoolClass(m8));
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
