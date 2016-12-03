package com.example.backend.Interfaces;

import com.example.backend.DTO.M8;
import com.example.backend.DTO.Schoolclass;

/**
 * Created by laubi on 12/2/2016.
 */

public class DataReader implements InterfaceBetweenFrontAndBackendInterface {

    @Override
    public M8 login(String email, String password) {
        return null;
    }

    @Override
    public Schoolclass getSchoolclassByUser(M8 User) {
        return null;
    }

    @Override
    public Schoolclass createNewClass(String name, String school, String room) {
        return null;
    }

    @Override
    public M8 createNewUser(String firstname, String lastname, String eMail, String password, String passwordConfirmation) {
        return null;
    }

    @Override
    public void deleteClass(Class schoolClass) {

    }

    @Override
    public Class updateClass(String name, String school, String room, Class OldSchoolClass) {
        return null;
    }

    @Override
    public void updateClass(Class schoolClass) {

    }

    @Override
    public void deleteUser(M8 user) {

    }

    @Override
    public void updateUser(M8 user) {

    }

    @Override
    public void updateUser(String firstname, String lastname, String eMail, String password, M8 OldUser) {

    }


}
