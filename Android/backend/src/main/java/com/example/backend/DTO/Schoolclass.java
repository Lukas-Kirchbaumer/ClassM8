package com.example.backend.Dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Schoolclass implements Serializable {

    private static final long serialVersionUID = -69655466883930376L;

    private long id;

    private ArrayList<M8> classMembers;

    private ArrayList<File> files;

    private String name;

    private String room;

    private M8 president;

    private M8 presidentDeputy;

    private String school;

    public Schoolclass() {
        classMembers = new ArrayList<>();
    }

    private List<Emote> emotes = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<M8> getClassMembers() {
        return classMembers;
    }

    public void setClassMembers(ArrayList<M8> classMembers) {
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

    public void setNewClass(Schoolclass sc) {
        //TODO: @kirche check for null values!
        if(sc.classMembers == null){
            this.classMembers=new ArrayList<>();
        }
        else{
            this.classMembers = sc.classMembers;
        }

        this.name = sc.name;
        this.room = sc.room;
        this.president = sc.president;
        this.presidentDeputy = sc.presidentDeputy;
        this.school = sc.school;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Schoolclass{" +
                "id=" + id +
                ", classMembers=" + classMembers +
                ", files=" + files +
                ", name='" + name + '\'' +
                ", room='" + room + '\'' +
                ", president=" + president +
                ", presidentDeputy=" + presidentDeputy +
                ", school='" + school + '\'' +
                ", emotes=" + emotes +
                '}';
    }

    public List<Emote> getEmotes() {
        return emotes;
    }

    public void setEmotes(List<Emote> emotes) {
        this.emotes = emotes;
    }
}
