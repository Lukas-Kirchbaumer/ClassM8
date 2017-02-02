package com.example.backend.Services;

import android.provider.ContactsContract;

import com.example.backend.Database.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.AsyncTasks.Executer;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.Results.LoginResult;
import com.example.backend.Results.M8Result;
import com.example.backend.Results.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.net.URL;

/**
 * Created by laubi on 12/22/2016.
 */

public class UserServices {

    private static UserServices instance = null;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private JsonParser parser = new JsonParser();

    public static UserServices getInstance() {
        if (instance == null) {
            instance = new UserServices();
        }
        return instance;
    }

    public M8 login(M8 user) {
        try {
            Executer executer = new Executer();
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/login");
            System.out.println("serverUrl");
            executer.setMethod("POST");
            executer.setData(gson.toJson(user, M8.class));
            System.out.println("Data Set: " + executer.getData());
            executer.execute(serverURL);
            System.out.println("executed");
            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            LoginResult r = gson.fromJson(strFromWebService, LoginResult.class);
            System.out.println("returned string2: " + r.getId());

            if (r.getId() == -1)
                throw new Exception("no valid user");

            serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/user/" + r.getId());

            executer = new Executer();
            executer.setMethod("GET");
            executer.execute(serverURL);

            strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            user = gson.fromJson(strFromWebService, M8.class);

            M8Result m8r = gson.fromJson(strFromWebService, M8Result.class);

            System.out.println(m8r.getContent());
            System.out.println(m8r.getContent().get(0).toM8());

            user = m8r.getContent().get(0).toM8();

            Database.getInstance().setCurrentMate(user);
            System.out.println(user.detailedToString());
            Database.getInstance().setCurrentSchoolclass(user.getSchoolclass());
            System.out.println(Database.getInstance().getCurrentSchoolclass());
        } catch (Exception e) {
            user = null;
            e.printStackTrace();
        }
        return user;
    }
    public M8 getUserByEMail(String eMail){
        M8 ret = null;
        try {

            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/user/byemail/" + eMail);

            Executer executer = new Executer();
            executer.setMethod("GET");
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            M8Result m8r = gson.fromJson(strFromWebService, M8Result.class);

            ret = m8r.getContent().get(0).toM8();

        }catch(Exception e){
            e.printStackTrace();
            ret = null;
        }
        return ret;
    }

    public M8 createNewUser(M8 user) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/user");

            executer.setMethod("POST");
            executer.setData(gson.toJson(user, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            M8Result r = gson.fromJson(strFromWebService, M8Result.class);
        } catch (Exception e) {
            user = null;
            e.printStackTrace();
        }

        return user;
    }

    public void deleteUser(M8 user) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/user?id=" + user.getId());

            executer.setMethod("DELETE");
            executer.setData(gson.toJson(user, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            Result r = gson.fromJson(strFromWebService, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(M8 user) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/user/?id=" + user.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(user, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            Result r = gson.fromJson(strFromWebService, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public M8 updateUser(M8 newUser, M8 OldUser) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/user/" + OldUser.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(newUser, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            Result r = gson.fromJson(strFromWebService, Result.class);
        } catch (Exception e) {
            newUser = null;
            e.printStackTrace();
        }
        return newUser;
    }

}
