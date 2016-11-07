package edu.classm8web.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.classm8web.dao.Database;
import edu.classm8web.dto.M8;
import edu.classm8web.dto.Schoolclass;

public class SchoolclassService {
	
	private static SchoolclassService instance;

	public static SchoolclassService getInstance() {
		if (SchoolclassService.instance == null) {
			SchoolclassService.instance = new SchoolclassService();
		}
		return SchoolclassService.instance;
	}

	private SchoolclassService() {
	}
	
	public void registerSchoolclass(Schoolclass sc, long m8id){
		sc.setId(getHighestId()+1);
		sc.setPresident(null);
		sc.setPresidentDeputy(null);
		
		Database.getInstance().getSchoolclasses().put(sc.getId(), sc);
		M8 m8 = Database.getInstance().getMades().get(m8id);
		Database.getInstance().getSchoolclasses().get(sc.getId()).getClassMembers().put(m8id, m8);
	}
	
	public void updateSchoolclass(Schoolclass sc){
		
		Database.getInstance().getSchoolclasses().get(sc.getId()).setNewClass(sc);
	}
	
	public void deleteSchoolclass(long id){
		
		Database.getInstance().getSchoolclasses().remove(id);
	}
	
	public void addM8ToSchoolClass(long scid, long m8id){
		M8 m8 = Database.getInstance().getMades().get(m8id);
		Database.getInstance().getSchoolclasses().get(scid).getClassMembers().put(m8id, m8);
		
	}
	
	public void removeM8FromSchoolClass(long scid, long m8id){
		Database.getInstance().getSchoolclasses().get(scid).getClassMembers().remove(m8id);
		if(Database.getInstance().getSchoolclasses().get(scid).getClassMembers().size() == 0){
			Database.getInstance().getSchoolclasses().remove(scid);
		}
	}
	
	public void setPresidentInSchoolClass(int scid, int m8id){
		M8 m8 = Database.getInstance().getMades().get(m8id);
		Database.getInstance().getSchoolclasses().get(scid).setPresident(m8);
	}
	
	public void setPresidentDebutyInSchoolClass(int scid, int m8id){
		M8 m8 = Database.getInstance().getMades().get(m8id);
		Database.getInstance().getSchoolclasses().get(scid).setPresidentDeputy(m8);
	}
	
	public HashMap<Long, M8> getMades(int scid){
		return Database.getInstance().getSchoolclasses().get(scid).getClassMembers();
	}
	
	public Schoolclass getSchoolClass(int scid){
		return Database.getInstance().getSchoolclasses().get(scid);
	}
	
	public Schoolclass getSchoolClassByM8(long m8id){
		Schoolclass ret = null;
		
		for(Entry<Long,Schoolclass> sc : Database.getInstance().getSchoolclasses().entrySet()){
			for(Entry<Long,M8> m8 : sc.getValue().getClassMembers().entrySet()){
				if(m8.getValue().getId() == m8id){
					ret = sc.getValue();
				}
			}
		}
		return ret;
	}
	
	
	private long getHighestId(){
		List<Long> sortedKeys=new ArrayList<Long>(Database.getInstance().getSchoolclasses().keySet());
		Collections.sort(sortedKeys);
		return Collections.max(sortedKeys);
	}
	
}
