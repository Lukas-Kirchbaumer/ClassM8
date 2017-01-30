package com.example.backend.Services;

import android.content.Context;

import com.example.backend.Database.Database;
import com.example.backend.Dto.File;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.AsyncTasks.Executer;
import com.example.backend.AsyncTasks.FileExecuter;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.Results.LoginResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Semaphore;

/**
 * Created by laubi on 12/22/2016.
 */

public class FileServices {

    private static FileServices instance = null;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private JsonParser parser = new JsonParser();

    public static FileServices getInstance() {
        if (instance == null) {
            instance = new FileServices();
        }
        return instance;
    }

    public int createFileMetaDataInGroup(Schoolclass schoolclass, File file) {
        int id = -1;
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/file/?schoolclassid=" + schoolclass.getId());
            System.out.println(serverURL.getQuery());
            executer.setMethod("POST");
            executer.setData(gson.toJson(file, File.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            LoginResult r = gson.fromJson(o, LoginResult.class);
            id = (int) r.getId();
        } catch (Exception e) {
            e.printStackTrace();
            id = -1;
        }
        return id;
    }

    public String uploadFile(java.io.File f) {
        try {
            Semaphore semaphore = new Semaphore(1);
            semaphore.acquire();
            System.out.println("uploadFile");
            System.out.println("in Fileexecuter2");
            File file = new File();
            file.setFileName(f.getName());
            System.out.println("inFileexecuter4");
            file.setContentType(URLConnection.guessContentTypeFromName(f.getName()));
            System.out.println("inFileexecuter5");

            System.out.println(file);

            int id = this.createFileMetaDataInGroup(Database.getInstance().getCurrentSchoolclass(), file);
            Thread.sleep(1000);
            semaphore.release();

            semaphore.acquire();
            System.out.println("inFileexecuter6");
            FileExecuter fileExecuter = new FileExecuter();
            fileExecuter.setDownload(false);
            fileExecuter.setId(id);
            fileExecuter.setMetaFile(file);
            fileExecuter.setOriginal(f);
            fileExecuter.execute();
            semaphore.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public void downloadFile(File file, Context context) {
        if (file != null) {
            FileExecuter fe = new FileExecuter();
            fe.setContext(context);
            fe.setDownload(true);
            fe.execute(file);
        }
    }
}
