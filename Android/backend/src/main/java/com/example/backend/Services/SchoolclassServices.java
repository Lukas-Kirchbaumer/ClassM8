package com.example.backend.Services;

import com.example.backend.Database.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.AsyncTasks.Executer;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.MappedObjects.MappedM8;
import com.example.backend.MappedObjects.MappedSchoolclass;
import com.example.backend.Results.M8Result;
import com.example.backend.Results.Result;
import com.example.backend.Results.SchoolclassResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by laubi on 12/22/2016.
 */

public class SchoolclassServices {

    private static SchoolclassServices instance = null;
    private Gson gson  = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
            Executer executer = new Executer();
            URL serverURL = new URL("http://"+DataReader.IP+ ":8080/ClassM8Web/services/schoolclass/"+ user.getId());

            executer.setMethod("GET");
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            SchoolclassResult r = gson.fromJson(o, SchoolclassResult.class);
            if (!r.isSuccess())
                throw new Exception("schoolclass not found");
            try {
                MappedSchoolclass mappedSchoolclass = r.getSchoolclasses().get(0);
                schoolclass = mappedSchoolclass.toSchoolClass();
            }catch (ArrayIndexOutOfBoundsException e){
                throw new Exception("schoolclass not found");
            }

            System.out.println("loaded class " + schoolclass);

            executer = new Executer();

            serverURL = new URL("http://"+DataReader.IP+ ":8080/ClassM8Web/services/user/byschoolclass/"+ schoolclass.getId());

            executer.setMethod("GET");
            executer.execute(serverURL);

            strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            M8Result m = gson.fromJson(strFromWebService, M8Result.class);

            for(MappedM8 mappedM8 : m.getContent()){
                schoolclass.setClassMembers(new ArrayList<M8>());
                System.out.println(mappedM8);
                schoolclass.getClassMembers().add(mappedM8.toM8());
            }

            Database.getInstance().setCurrentSchoolclass(schoolclass);

        }catch (Exception e){
            schoolclass = null;
            System.out.println(e.getMessage());
        }
        return schoolclass;
    }

    public boolean addMateToClass(int id){
        Executer executer = new Executer();
        boolean ret = false;
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/schoolclass/" + id +"?scid=" + Database.getInstance().getCurrentSchoolclass().getId());

            executer.setMethod("POST");
            executer.setData("");
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            Result r = gson.fromJson(strFromWebService, Result.class);
            ret = r.isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Schoolclass createNewClass(Schoolclass s) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/schoolclass?m8id=" + Database.getInstance().getCurrentMate().getId());

            executer.setMethod("POST");
            executer.setData(gson.toJson(s, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            Result r = gson.fromJson(strFromWebService, Result.class);
        }catch (Exception e){
            s= null;
            e.printStackTrace();
        }
        return s;
    }


    public Schoolclass updateClass(Schoolclass newSchoolclass, Schoolclass oldSchoolClass) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/schoolclass?id=" + oldSchoolClass.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(newSchoolclass, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            Result r = gson.fromJson(strFromWebService, Result.class);
        } catch (Exception e) {
            newSchoolclass = null;
            e.printStackTrace();
        }
        return newSchoolclass;
    }

    public void updateClass(Schoolclass schoolClass) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/schoolclass?id=" + schoolClass.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(schoolClass, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            Result r = gson.fromJson(strFromWebService, Result.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteClass(Schoolclass schoolClass) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/schoolclass?id=" + schoolClass.getId());

            executer.setMethod("DELETE");
            executer.setData(gson.toJson(schoolClass, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            Result r = gson.fromJson(strFromWebService, Result.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
