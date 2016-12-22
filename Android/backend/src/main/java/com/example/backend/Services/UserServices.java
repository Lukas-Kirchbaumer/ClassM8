package com.example.backend.Services;

import com.example.backend.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Executer;
import com.example.backend.Results.LoginResult;
import com.example.backend.Results.M8Result;
import com.example.backend.Results.Result;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.net.URL;

/**
 * Created by laubi on 12/22/2016.
 */

public class UserServices {

    private static UserServices instance = null;
    private Executer executer = new Executer();;
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();

    public static UserServices getInstance(){
        if(instance == null){
            instance = new UserServices();
        }
        return instance;
    }

    public M8 login(M8 user) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/login");

            executer.setMethod("POST");
            executer.setData(gson.toJson(user, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            LoginResult r = gson.fromJson(o, LoginResult.class);

            if (r.getId() == -1)
                throw new Exception("no valid user");
            serverURL = new URL("http://localhost:8080/ClassM8Web/services/user/" + r.getId());


            executer.setMethod("GET");
            executer.execute(serverURL);

            strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            user = gson.fromJson(strFromWebService, M8.class);

            o = parser.parse(strFromWebService);
            M8Result m8r = gson.fromJson(o, M8Result.class);
            user = m8r.getContent().get(0).toM8();
            Database.getInstance().setCurrentMate(user);

        } catch (Exception e) {
            user = null;
            e.printStackTrace();
        }
        return user;
    }

    public M8 createNewUser(M8 user) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/schoolclass");

            executer.setMethod("POST");
            executer.setData(gson.toJson(user, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            M8Result r = gson.fromJson(o, M8Result.class);
        }catch (Exception e){
            user = null;
            e.printStackTrace();
        }

        return user;
    }

    public void deleteUser(M8 user) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/user/"+user.getId());

            executer.setMethod("DELETE");
            executer.setData(gson.toJson(user, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            Result r = gson.fromJson(o, Result.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateUser(M8 user) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/user/" + user.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(user, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            Result r = gson.fromJson(o, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public M8 updateUser(M8 newUser, M8 OldUser) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/user/" + OldUser.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(newUser, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            Result r = gson.fromJson(o, Result.class);
        } catch (Exception e) {
            newUser = null;
            e.printStackTrace();
        }
        return newUser;
    }

}
