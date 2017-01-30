package com.example.backend.Results;


import com.example.backend.MappedObjects.MappedSchoolclass;

import java.util.Vector;

public class SchoolclassResult extends Result {
    private Vector<MappedSchoolclass> schoolclasses = new Vector<>();

    public SchoolclassResult() {
    }

    public Vector<MappedSchoolclass> getSchoolclasses() {
        return schoolclasses;
    }

    public void setSchoolclasses(Vector<MappedSchoolclass> schoolclasses) {
        this.schoolclasses = schoolclasses;
    }
}
