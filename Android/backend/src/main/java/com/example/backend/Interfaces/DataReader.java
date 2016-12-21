package com.example.backend.Interfaces;

import com.example.backend.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.MappedSchoolclass;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Executer;
import com.example.backend.Results.LoginResult;
import com.example.backend.Results.M8Result;
import com.example.backend.Results.SchoolclassResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * Created by laubi on 12/2/2016.
 */

public class DataReader implements InterfaceBetweenFrontAndBackendInterface {

    private Executer executer = new Executer();;
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();

    private DataReader instance = null;

    /**
     * DataReader is a Singleton. getInstance() returns the instance (duh)
     * @return the Instance of DataReader
     */
    public DataReader getInstance(){
        if(instance == null){
            instance = new DataReader();
        }
        return instance;
    }

    /**
     * Selfexplaining
     * Returns null if Authentification failed
     * @param email The E-Mail to verify
     * @param password The Password to verify
     * @return The Instance of the logged in User
     */
    @Override
    public M8 login(String email, String password) {

        M8 user = new M8();
        user.setEmail(email);
        user.setPassword(password);

        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/login");

            executer.setMethod("POST");
            executer.setData(gson.toJson(user, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            LoginResult r = gson.fromJson(o, LoginResult.class);


            serverURL = new URL("http://localhost:8080/ClassM8Web/services/user/"+r.getId());


            executer.setMethod("GET");
            executer.execute(serverURL);

            strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            user = gson.fromJson(strFromWebService, M8.class);

            o = parser.parse(strFromWebService);
            M8Result m8r = gson.fromJson(o, M8Result.class);
            user = m8r.getContent().get(0);
            Database.getInstance().setCurrentMate(user);

        }catch (Exception e){
            user = null;
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Method to get the Schoolclass of a specific User
     * @param user The logged in User
     * @return the Schoolclass of the User
     */
        @Override
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
                Database.getInstance().setCurrentSchoolclass(schoolclass);

            }catch (Exception e){
                schoolclass = null;
                e.printStackTrace();
            }
        return schoolclass;
    }

    @Override
    public Schoolclass createNewClass(String name, String school, String room) {
        Schoolclass s = new Schoolclass();
        s.setName(name);
        s.setSchool(school);
        s.setRoom(room);


        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/schoolclass");

            executer.setMethod("POST");
            executer.setData(gson.toJson(s, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            LoginResult r = gson.fromJson(o, LoginResult.class);
        }catch (Exception e){
            s= null;
            e.printStackTrace();
        }
        return s;
    }


    @Override
    public Schoolclass updateClass(String name, String school, String room, Schoolclass oldSchoolClass) {
        Schoolclass newSchoolclass = oldSchoolClass;
        newSchoolclass.setRoom(room);
        newSchoolclass.setSchool(school);
        newSchoolclass.setName(name);

        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/schoolclass/" + oldSchoolClass.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(newSchoolclass, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);

        } catch (Exception e) {
            newSchoolclass = null;
            e.printStackTrace();
        }
        return newSchoolclass;
    }

    @Override
    public void updateClass(Schoolclass schoolClass) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/schoolclass/"+schoolClass.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(schoolClass, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClass(Schoolclass schoolClass) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/schoolclass/"+schoolClass.getId());

            executer.setMethod("DELETE");
            executer.setData(gson.toJson(schoolClass, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public M8 createNewUser(String firstname, String lastname, String eMail, String password, String passwordConfirmation) {
        //  String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        //  new Executer().execute(serverURL);

        return null;
    }

    @Override
    public void deleteUser(M8 user) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/user/"+user.getId());

            executer.setMethod("DELETE");
            executer.setData(gson.toJson(user, Schoolclass.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(M8 user) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/user/" + user.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(user, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Replaces the Old User with a new one
     * @param firstname firstname of the newly generated
     * @param lastname
     * @param eMail
     * @param password
     * @param OldUser
     * @return A new generated User
     */
    @Override
    public M8 updateUser(String firstname, String lastname, String eMail, String password, M8 OldUser) {
        M8 newUser = new M8();
        newUser.setFirstname(firstname);
        newUser.setLastname(lastname);
        newUser.setEmail(eMail);
        newUser.setPassword(password);
        newUser.setId(OldUser.getId());
        newUser.setVotes(OldUser.getVotes());

        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/user/" + OldUser.getId());

            executer.setMethod("PUT");
            executer.setData(gson.toJson(newUser, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);

        } catch (Exception e) {
            newUser = null;
            e.printStackTrace();
        }
        return newUser;
    }

    public void uploadFile(File f) {
            try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/fileshare/");

            executer.setMethod("POST");
                FileInputStream imageInFile = new FileInputStream(f);
                byte fileData[] = new byte[(int)f.length()];
                imageInFile.read(fileData);
                String imageDataString = encode(fileData);

            executer.setData(gson.toJson(imageDataString));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String encode(byte[] fileData) {
        StringBuilder sb = new StringBuilder();
        for (byte b:fileData) {
            sb.append(b);
        }
        return sb.toString();

    }

    public void placeVoteForPresident(M8 user, M8 votedMate) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/president/" + user.getId());

            executer.setMethod("POST");
            executer.setData(gson.toJson(votedMate, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
        } catch (Exception e) {

        }
    }
    public void placeVoteForPresidentDeputy(M8 user, M8 votedMate){
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/president/Deputy/" + user.getId());

            executer.setMethod("POST");
            executer.setData(gson.toJson(votedMate, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
        } catch (Exception e) {

        }
    }


}
