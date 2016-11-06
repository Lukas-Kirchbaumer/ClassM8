package edu.classm8web.services;

import java.util.Vector;

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
		sc.setId(getHighestId());
		sc.setPresident(null);
		sc.setPresidentDeputy(null);
		
		for(int i = 0; i < Database.getInstance().getMades().size(); i++){
			if(m8id == Database.getInstance().getMades().elementAt(i).getId()){
				sc.getClassMembers().add(Database.getInstance().getMades().elementAt(i));
			}
		}
		
		Database.getInstance().getSchoolClasses().add(sc);
	}
	
	public void updateSchoolclass(Schoolclass sc){
		for(int i = 0; i < Database.getInstance().getSchoolClasses().size();i++){
			if(sc.getId() == Database.getInstance().getSchoolClasses().elementAt(i).getId()){
				Database.getInstance().getSchoolClasses().elementAt(i).setNewClass(sc);;
			}
		}
	}
	
	public void deleteSchoolclass(long id){
		for(int i = 0; i < Database.getInstance().getSchoolClasses().size();i++){
			if(id == Database.getInstance().getSchoolClasses().elementAt(i).getId()){
				Database.getInstance().getSchoolClasses().remove(i);
			}
		}
	}
	
	public void addM8ToSchoolClass(long scid, long m8id){
		for(int i = 0; i < Database.getInstance().getSchoolClasses().size();i++){
			if(scid == Database.getInstance().getSchoolClasses().elementAt(i).getId()){
				
				for(int j = 0; j < Database.getInstance().getMades().size();j++){
					if(m8id == Database.getInstance().getMades().elementAt(j).getId()){
						Database.getInstance().getSchoolClasses().elementAt(i).getClassMembers().add(Database.getInstance().getMades().elementAt(j));
					}
				}
				
			}
		}
	}
	
	public void removeM8FromSchoolClass(long scid, long m8id){
		int remove = -1;
		for(int i = 0; i < Database.getInstance().getSchoolClasses().size();i++){
			if(scid == Database.getInstance().getSchoolClasses().elementAt(i).getId()){
				
				for(int j = 0; j < Database.getInstance().getSchoolClasses().elementAt(i).getClassMembers().size();j++){
					if(m8id == Database.getInstance().getSchoolClasses().elementAt(i).getClassMembers().get(j).getId()){
						Database.getInstance().getSchoolClasses().elementAt(i).getClassMembers().remove(j);
						if(Database.getInstance().getSchoolClasses().elementAt(i).getClassMembers().size() == 0){
							remove = i;
						}
					}
				}
				
			}
		}
		if(remove != -1){
			Database.getInstance().getSchoolClasses().remove(remove);
		}
	}
	
	public void setPresidentInSchoolClass(int scid, int m8id){
		for(int i = 0; i < Database.getInstance().getSchoolClasses().size();i++){
			if(scid == Database.getInstance().getSchoolClasses().elementAt(i).getId()){
				
				for(int j = 0; j < Database.getInstance().getMades().size();j++){
					if(m8id == Database.getInstance().getMades().elementAt(j).getId()){
						Database.getInstance().getSchoolClasses().elementAt(i).setPresident(Database.getInstance().getMades().elementAt(j));
					}
				}
				
			}
		}
	}
	
	public void setPresidentDebutyInSchoolClass(int scid, int m8id){
		for(int i = 0; i < Database.getInstance().getSchoolClasses().size();i++){
			if(scid == Database.getInstance().getSchoolClasses().elementAt(i).getId()){
				
				for(int j = 0; j < Database.getInstance().getMades().size();j++){
					if(m8id == Database.getInstance().getMades().elementAt(j).getId()){
						Database.getInstance().getSchoolClasses().elementAt(i).setPresidentDeputy(Database.getInstance().getMades().elementAt(j));
					}
				}
				
			}
		}
	}
	
	public Vector<M8> getMades(int scid){
		
		Vector<M8> mades = null;
		
		for(int i = 0; i < Database.getInstance().getSchoolClasses().size();i++){
			if(scid == Database.getInstance().getSchoolClasses().elementAt(i).getId()){
				mades = Database.getInstance().getSchoolClasses().elementAt(i).getClassMembers();
			}
		}
		
		return mades;
	}
	
	public Schoolclass getSchoolClass(int scid){
		Schoolclass ret = null;
		
		for(int i = 0; i < Database.getInstance().getSchoolClasses().size();i++){
			if(scid == Database.getInstance().getSchoolClasses().elementAt(i).getId()){
				ret = Database.getInstance().getSchoolClasses().elementAt(i);
			}
		}
		return ret;
	}
	
	public Schoolclass getSchoolClassByM8(long m8id){
		Schoolclass ret = null;
		
		for(Schoolclass sc : Database.getInstance().getSchoolClasses()){
			for(M8 m8 : sc.getClassMembers()){
				if(m8.getId() == m8id){
					ret = sc;
				}
			}
		}
		return ret;
	}
	
	
	private long getHighestId(){
		long highest = 0;
		for (int i = 0; i < Database.getInstance().getSchoolClasses().size(); i++) {
			if (highest < Database.getInstance().getSchoolClasses().elementAt(i).getId()) {
				highest = Database.getInstance().getSchoolClasses().elementAt(i).getId();
			}
		}
		return highest;
	}
	
}
