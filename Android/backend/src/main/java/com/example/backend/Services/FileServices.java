package com.example.backend.Services;

import com.example.backend.Dto.File;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Executer;
import com.example.backend.FileExecuter;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.Results.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.net.URL;

/**
 * Created by laubi on 12/22/2016.
 */

public class FileServices {

    private static FileServices instance = null;
    private Gson gson  = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private JsonParser parser = new JsonParser();

    public static FileServices getInstance(){
        if(instance == null){
            instance = new FileServices();
        }
        return instance;
    }

    public void createFileMetaDataInGroup(Schoolclass schoolclass,File file) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://"+DataReader.IP+":8080/ClassM8Web/services/fileshare/?schoolclassid="+ schoolclass.getId());

            executer.setMethod("POST");
            executer.setData(gson.toJson(file, File.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            Result r = gson.fromJson(o, Result.class);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void uploadFile(java.io.File f) {

        FileExecuter executer = new FileExecuter();
        try {
            executer.setMethod("POST");
            executer.setData(f);
            executer.execute();
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

}
