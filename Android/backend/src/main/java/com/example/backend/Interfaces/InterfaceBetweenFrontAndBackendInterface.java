package com.example.backend.Interfaces;

import android.content.Context;

import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;

/**
 * Created by laubi on 12/2/2016.
 */

interface InterfaceBetweenFrontAndBackendInterface {
    public M8 login(String email, String password, Context c);

    public Schoolclass getSchoolclassByUser(M8 user);

    public Schoolclass createNewClass(String name, String school, String room);

    public M8 createNewUser(String firstname, String lastname, String eMail, String password, String passwordConfirmation);

    public void deleteClass(Schoolclass schoolClass);

    public Schoolclass updateClass(String name, String school, String room, Schoolclass OldSchoolClass);

    public void updateClass(Schoolclass schoolClass);

    public void deleteUser(M8 user);

    public void updateUser(M8 user);

    public M8 updateUser(String firstname, String lastname, String eMail, String password, M8 OldUser);
}
