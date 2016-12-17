package com.example.backend.Interfaces;

import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Executer;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by laubi on 12/2/2016.
 */

public class DataReader implements InterfaceBetweenFrontAndBackendInterface {

    Executer executer;

    @Override
    public M8 login(String email, String password) {
        M8 user = null;
        try {
            URL serverURL = new URL("http://androidexample.com/media/webservice/JsonReturn.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept", "application/json");

            executer = new Executer();

            executer.execute(urlConnection);
            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            Gson gson = new Gson();
            user = gson.fromJson(strFromWebService, M8.class);

        }catch (Exception e){

        }
        return user;
    }

    @Override
    public Schoolclass getSchoolclassByUser(M8 User) {
      //  String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        //  new Executer().execute(serverURL);

        return null;
    }

    @Override
    public Schoolclass createNewClass(String name, String school, String room) {
        //  String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        //  new Executer().execute(serverURL);
        return null;
    }

    @Override
    public M8 createNewUser(String firstname, String lastname, String eMail, String password, String passwordConfirmation) {
        //  String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        //  new Executer().execute(serverURL);

        return null;
    }

    @Override
    public void deleteClass(Class schoolClass) {
        //   String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        //   new Executer().execute(serverURL);
    }

    @Override
    public Class updateClass(String name, String school, String room, Class OldSchoolClass) {
        //   String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        //   new Executer().execute(serverURL);

        return null;
    }

    @Override
    public void updateClass(Class schoolClass) {
        //   String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        //new Executer().execute(serverURL);

    }

    @Override
    public void deleteUser(M8 user) {
        //  String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        //  new Executer().execute(serverURL);

    }

    @Override
    public void updateUser(M8 user) {
        //   String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        //   new Executer().execute(serverURL);
    }

    @Override
    public void updateUser(String firstname, String lastname, String eMail, String password, M8 OldUser) {
        //   String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        //   new Executer().execute(serverURL);
    }


}
