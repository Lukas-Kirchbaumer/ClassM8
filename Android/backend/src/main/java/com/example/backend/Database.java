package com.example.backend;

import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;

/**
 * Created by laubi on 12/21/2016.
 */
public class Database {
    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private M8 currentMate;

    private Schoolclass currentSchoolclass;


    private Database() {
    }

    public M8 getCurrentMate() {
        return currentMate;
    }

    public void setCurrentMate(M8 currentMate) {
        this.currentMate = currentMate;
    }

    public Schoolclass getCurrentSchoolclass() {
        return currentSchoolclass;
    }

    public void setCurrentSchoolclass(Schoolclass currentSchoolclass) {
        this.currentSchoolclass = currentSchoolclass;
    }
}
