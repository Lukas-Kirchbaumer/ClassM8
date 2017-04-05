package com.example.backend.MappedObjects;

import com.example.backend.Dto.Emote;
import com.example.backend.Dto.File;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class MappedSchoolclass {

    private long id;

    private ArrayList<MappedM8> classMembers = new ArrayList<>();

    private String name;

    private String room;

    private MappedM8 president;

    private MappedM8 presidentDeputy;

    private String school;

    private Vector<MappedFile> files = new Vector<MappedFile>();

    private Vector<MappedEmote> emotes= new Vector<MappedEmote>();

    public MappedSchoolclass() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<MappedM8> getClassMembers() {
        return classMembers;
    }

    public void setClassMembers(ArrayList<MappedM8> classMembers) {
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

    public MappedM8 getPresident() {
        return president;
    }

    public void setPresident(MappedM8 president) {
        this.president = president;
    }

    public MappedM8 getPresidentDeputy() {
        return presidentDeputy;
    }

    public void setPresidentDeputy(MappedM8 presidentDeputy) {
        this.presidentDeputy = presidentDeputy;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setNewClass(MappedSchoolclass sc) {
        this.classMembers = sc.classMembers;
        this.name = sc.name;
        this.room = sc.room;
        this.president = sc.president;
        this.presidentDeputy = sc.presidentDeputy;
        this.school = sc.school;
    }

    public void setNewClassNoM8(MappedSchoolclass sc) {
        this.classMembers = null;
        this.name = sc.name;
        this.room = sc.room;
        this.president = sc.president;
        this.presidentDeputy = sc.presidentDeputy;
        this.school = sc.school;
    }

    public Vector<MappedFile> getFiles() {
        return files;
    }

    public void setFiles(Vector<MappedFile> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "MappedSchoolclass{" +
                "id=" + id +
                ", classMembers=" + classMembers +
                ", name='" + name + '\'' +
                ", room='" + room + '\'' +
                ", president=" + president +
                ", presidentDeputy=" + presidentDeputy +
                ", school='" + school + '\'' +
                ", files=" + files +
                ", emotes=" + emotes +
                '}';
    }

    public Schoolclass toSchoolClass() {
        Schoolclass s = new Schoolclass();
        s.setId(this.getId());
        s.setName(this.getName());
        s.setRoom(this.getRoom());
        s.setSchool(this.getSchool());
        s.setFiles(new ArrayList<File>());

        try {
            for (MappedFile f : this.files) {
                s.getFiles().add(f.toFile());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            for(MappedEmote e :this.emotes){
                s.getEmotes().add(e.toEmote());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        s.setClassMembers(new ArrayList<M8>());


        try {
            s.setPresident(this.president.toM8());
        } catch (Exception e) {
            System.out.println("no President set");
        }
        try {
            s.setPresidentDeputy(this.presidentDeputy.toM8());
        } catch (Exception e) {
            System.out.println("no President Deputy set");
        }

        return s;
    }
}
