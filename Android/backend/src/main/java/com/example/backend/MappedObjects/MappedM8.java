package com.example.backend.MappedObjects;

import android.provider.ContactsContract;

import com.example.backend.Database.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Mapper.ObjectMapper;

public class MappedM8 {

    private long id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private boolean hasVoted;

    private int votes;

    private MappedSchoolclass schoolclass;

    public MappedM8() {
        // TODO Auto-generated constructor stub
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public MappedSchoolclass getSchoolclass() {
        return schoolclass;
    }

    public void setSchoolclass(MappedSchoolclass schoolclass) {
        this.schoolclass = schoolclass;
    }

    public void setNewM8NoSchoolClass(M8 newm8) {
        this.id = newm8.getId();
        this.firstname = newm8.getFirstname();
        this.lastname = newm8.getLastname();
        this.email = newm8.getEmail();
        this.password = newm8.getPassword();
        this.hasVoted = newm8.isHasVoted();
        this.votes = newm8.getVotes();
        this.schoolclass = null;
    }

    public void setNewM8(M8 newm8) {
        this.id = newm8.getId();
        this.firstname = newm8.getFirstname();
        this.lastname = newm8.getLastname();
        this.email = newm8.getEmail();
        this.password = newm8.getPassword();
        this.hasVoted = newm8.isHasVoted();
        this.votes = newm8.getVotes();
        this.schoolclass = ObjectMapper.map(newm8.getSchoolclass());
    }

    public M8 toM8() {
        M8 mate = new M8();

        mate.setId(this.getId());
        mate.setFirstname(this.firstname);
        mate.setLastname(this.lastname);
        mate.setVotes(this.votes);
        mate.setPassword(this.password);
        mate.setEmail(this.email);
        try {
            mate.setSchoolclass(this.schoolclass.toSchoolClass());
            System.out.println(this.schoolclass);
            Database.getInstance().setCurrentSchoolclass(mate.getSchoolclass());
            for (MappedM8 mappedMate : this.getSchoolclass().getClassMembers()) {
                Database.getInstance().getCurrentSchoolclass().getClassMembers().add(mappedMate.toM8());
            }
        } catch (Exception e) {
            System.out.println("schoolclass not known yet");
        }
        mate.setHasVoted(this.hasVoted);

        return mate;
    }
}
