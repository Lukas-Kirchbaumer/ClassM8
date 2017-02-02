package com.example.backend.Interfaces;

import android.content.Context;

import com.example.backend.Database.Database;
import com.example.backend.Dto.File;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Message;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Services.ChatServices;
import com.example.backend.Services.FileServices;
import com.example.backend.Services.SchoolclassServices;
import com.example.backend.Services.UserServices;
import com.example.backend.Services.VotingServices;

import java.util.ArrayList;

/**
 * Created by laubi on 12/2/2016.
 */

public class DataReader implements InterfaceBetweenFrontAndBackendInterface {

    public static String IP = "10.0.0.37";
    private static DataReader instance = null;

    public static DataReader getInstance() {
        if (instance == null) {
            instance = new DataReader();
        }
        return instance;
    }

    @Override
    public M8 login(String email, String password) {
        M8 user = new M8();
        user.setEmail(email);
        user.setPassword(password);
        System.out.println(user);
        user = UserServices.getInstance().login(user);

        return user;
    }

    @Override
    public Schoolclass getSchoolclassByUser(M8 user) {
        Schoolclass schoolclass = SchoolclassServices.getInstance().getSchoolclassByUser(user);
        return schoolclass;
    }

    @Override
    public Schoolclass createNewClass(String name, String school, String room) {
        Schoolclass s = new Schoolclass();
        s.setName(name);
        s.setSchool(school);
        s.setRoom(room);

        SchoolclassServices.getInstance().createNewClass(s);

        return s;
    }

    @Override
    public Schoolclass updateClass(String name, String school, String room, Schoolclass oldSchoolClass) {
        Schoolclass newSchoolclass = oldSchoolClass;
        newSchoolclass.setRoom(room);
        newSchoolclass.setSchool(school);
        newSchoolclass.setName(name);

        SchoolclassServices.getInstance().updateClass(newSchoolclass, oldSchoolClass);

        return newSchoolclass;
    }

    @Override
    public void updateClass(Schoolclass schoolClass) {
        System.out.println(schoolClass);
        SchoolclassServices.getInstance().updateClass(schoolClass);
    }

    @Override
    public void deleteClass(Schoolclass schoolClass) {
        SchoolclassServices.getInstance().deleteClass(schoolClass);
    }

    @Override
    public M8 createNewUser(String firstname, String lastname, String eMail, String password, String passwordConfirmation) {
        M8 user = null;
        if (password == passwordConfirmation) {
            user = new M8();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(eMail);
            user.setPassword(password);

            UserServices.getInstance().createNewUser(user);
        }
        return user;
    }

    @Override
    public void deleteUser(M8 user) {
        UserServices.getInstance().deleteUser(user);
    }

    @Override
    public void updateUser(M8 user) {
        UserServices.getInstance().updateUser(user);
    }

    @Override
    public M8 updateUser(String firstname, String lastname, String eMail, String password, M8 OldUser) {
        M8 newUser = new M8();
        newUser.setFirstname(firstname);
        newUser.setLastname(lastname);
        newUser.setEmail(eMail);
        newUser.setPassword(password);
        newUser.setId(OldUser.getId());
        newUser.setVotes(OldUser.getVotes());

        UserServices.getInstance().updateUser(newUser, OldUser);

        return newUser;
    }

    public void uploadFile(java.io.File f) {
        FileServices.getInstance().uploadFile(f);
    }

    public void placeVoteForPresident(M8 user, M8 votedMate) {
        VotingServices.getInstance().placeVoteForPresident(user, votedMate);
    }

    public void downloadFile(File file, Context context) {
        FileServices.getInstance().downloadFile(file, context);
    }

    public void sendMessage(String message) {
        ChatServices chatService = new ChatServices();
        chatService.writeMessage(Database.getInstance().getCurrentMate(), message);
    }

    public ArrayList<Message> receiveMessage() {
        ChatServices chatService = new ChatServices();
        return new ArrayList<Message>(chatService.receiveMessages());
    }

    public boolean addMateToSchoolclass(M8 mate){
        return SchoolclassServices.getInstance().addMateToClass((int)mate.getId());
    }

    public M8 getMateByEMail(String mail){
        return UserServices.getInstance().getUserByEMail(mail);
    }
}
