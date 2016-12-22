package com.example.backend.Services;

import com.example.backend.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Executer;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.MappedObjects.MappedM8;
import com.example.backend.MappedObjects.MappedSchoolclass;
import com.example.backend.Results.LoginResult;
import com.example.backend.Results.M8Result;
import com.example.backend.Results.Result;
import com.example.backend.Results.SchoolclassResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.net.URL;

/**
 * Created by laubi on 12/22/2016.
 */

public class SchoolclassServices {

    private static SchoolclassServices instance = null;
    private Executer executer = new Executer();;
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();

    public static SchoolclassServices getInstance(){
        if(instance == null){
            instance = new SchoolclassServices();
        }
        return instance;
    }

    public Schoolclass getSchoolclassByUser(M8 user) {
        Schoolclass schoolclass = null;
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/schoolclass/"+ user.getId());

            executer.setMethod("GET");
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            SchoolclassResult r = gson.fromJson(o, SchoolclassResult.class);

            MappedSchoolclass mappedSchoolclass = r.getSchoolclasses().get(0);

            schoolclass = mappedSchoolclass.toSchoolClass();


            serverURL = new URL("http://localhost:8080/ClassM8Web/services/user/byschoolclass/"+ schoolclass.getId());

            executer.setMethod("GET");
            executer.execute(serverURL);

            strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            o = parser.parse(strFromWebService);
            M8Result m = gson.fromJson(o, M8Result.class);

            for(MappedM8 mappedM8 :m.getContent()){
                schoolclass.getClassMembers().add(mappedM8.toM8());
            }

            Database.getInstance().setCurrentSchoolclass(schoolclass);

        }catch (Exception e){
            schoolclass = null;
            e.printStackTrace();
        }
        return schoolclass;
    }

    public Schoolclass createNewClass(Schoolclass s) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/schoolclass");

            executer.setMethod("POST");
            executer.setData(gson.toJson(s, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            Result r = gson.fromJson(o, Result.class);
        }catch (Exception e){
            s= null;
            e.printStackTrace();
        }
        return s;
    }


    public Schoolclass updateClass(Schoolclass newSchoolclass, Schoolclass oldSchoolClass) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/schoolclass/" + oldSchoolClass.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(newSchoolclass, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            Result r = gson.fromJson(o, Result.class);
        } catch (Exception e) {
            newSchoolclass = null;
            e.printStackTrace();
        }
        return newSchoolclass;
    }

    public void updateClass(Schoolclass schoolClass) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/schoolclass/"+schoolClass.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(schoolClass, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            Result r = gson.fromJson(o, Result.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteClass(Schoolclass schoolClass) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/schoolclass/"+schoolClass.getId());

            executer.setMethod("DELETE");
            executer.setData(gson.toJson(schoolClass, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            Result r = gson.fromJson(o, Result.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
