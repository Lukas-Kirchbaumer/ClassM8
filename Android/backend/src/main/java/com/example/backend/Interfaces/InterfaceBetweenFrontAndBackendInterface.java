package com.example.backend.Interfaces;

import com.example.backend.DTO.M8;
import com.example.backend.DTO.Schoolclass;

/**
 * Created by laubi on 12/2/2016.
 */

interface InterfaceBetweenFrontAndBackendInterface {
    public M8 login(String email, String password);
    public Schoolclass getSchoolclassByUser(M8 user);
    public Schoolclass createNewClass (String name, String school, String room);
    public M8 createNewUser (String firstname, String lastname, String eMail, String password, String passwordConfirmation);
    public void deleteClass(Class schoolClass);
    public Class updateClass (String name, String school, String room, Class OldSchoolClass);
    public void updateClass (Class schoolClass);
    public void deleteUser (M8 user);
    public void updateUser(M8 user);
    public void updateUser(String firstname, String lastname, String eMail, String password, M8 OldUser);
}
