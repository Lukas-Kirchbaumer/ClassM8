package com.example.backend.Services;

import com.example.backend.Executer;
import com.example.backend.Interfaces.DataReader;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * Created by laubi on 12/22/2016.
 */

public class FileServices {

    private static FileServices instance = null;
    private Executer executer = new Executer();;
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();

    public static FileServices getInstance(){
        if(instance == null){
            instance = new FileServices();
        }
        return instance;
    }
    public void uploadFile(File f) {
        try {
            URL serverURL = new URL("http://localhost:8080/ClassM8Web/services/fileshare/");

            executer.setMethod("POST");
            FileInputStream imageInFile = new FileInputStream(f);
            byte fileData[] = new byte[(int) f.length()];
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
}
