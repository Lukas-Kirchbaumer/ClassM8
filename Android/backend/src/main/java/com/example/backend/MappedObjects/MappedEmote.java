package com.example.backend.MappedObjects;


import com.example.backend.Dto.Emote;
import com.example.backend.Dto.Schoolclass;

public class MappedEmote {

    private long id;

    private String fileName;

    private String shortString;

    private long contentSize;

    private Schoolclass referencedSchoolclass;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getShortString() {
        return shortString;
    }

    public void setShortString(String shortString) {
        this.shortString = shortString;
    }

    public long getContentSize() {
        return contentSize;
    }

    public void setContentSize(long contentSize) {
        this.contentSize = contentSize;
    }

    public Schoolclass getReferencedSchoolclass() {
        return referencedSchoolclass;
    }

    public void setReferencedSchoolclass(Schoolclass referencedSchoolclass) {
        this.referencedSchoolclass = referencedSchoolclass;
    }

    public void setNewEmoteNoSchoolClass(Emote ne){
        this.id = ne.getId();
        this.contentSize = ne.getContentSize();
        this.shortString = ne.getShortString();
        this.fileName = ne.getFileName();
        this.referencedSchoolclass = null;
    }

    public Emote toEmote(){
        Emote e = new Emote();
        e.setId(this.id);
        e.setContentSize(this.contentSize);
        e.setShortString(this.shortString);
        e.setFileName(this.fileName);
        return e;
    }
}
