package com.example.backend.Services;

import android.content.Context;

import com.example.backend.Database;
import com.example.backend.Dto.File;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Executer;
import com.example.backend.FileExecuter;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.Results.LoginResult;
import com.example.backend.Results.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;;

import javax.ws.rs.core.MediaType;

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

    public int createFileMetaDataInGroup(Schoolclass schoolclass,File file) {
        int id = -1;
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://"+DataReader.IP+":8080/ClassM8Web/services/file/?schoolclassid="+ schoolclass.getId());
            System.out.println(serverURL.getPath());
            executer.setMethod("POST");
            executer.setData(gson.toJson(file, File.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            LoginResult r = gson.fromJson(o, LoginResult.class);
            id = (int)r.getId();
        }catch (Exception e){
            e.printStackTrace();
            id = -1;
        }
        return id;
    }


    private String encode(byte[] fileData) {
        StringBuilder sb = new StringBuilder();
        for (byte b:fileData) {
            sb.append(b);
        }
        return sb.toString();
    }

    public String uploadFile(java.io.File f, File file) {
        int id = this.createFileMetaDataInGroup(Database.getInstance().getCurrentSchoolclass(), file);

        String retValue = "image uploaded ??";
        try {
            URL serverURL = new URL("http://"+ DataReader.IP + ":8080/ClassM8Web/services/file/content/" + id);
            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
            BodyPart bp = multiPart.bodyPart(new FileDataBodyPart("file", f, MediaType.APPLICATION_OCTET_STREAM_TYPE));

            Executer e = new Executer();
            e.setMediaType(MediaType.MULTIPART_FORM_DATA);

            e.setData(bp.toString());

            e.execute(serverURL);

            String strFromWebService = e.get();

            System.out.println("returned string: " + strFromWebService);

            e.setMethod("POST");
        }catch (Exception e){
            e.printStackTrace();
        }

        return retValue;
    }

    public void downloadFile(File file, Context context) {
        if (file != null)
            {
                FileExecuter fe = new FileExecuter();
                fe.setContext(context);
                fe.setDownload(true);
                fe.execute(file);
            }
    }
/*
    public void downloadImage(int _id) throws Exception {
        String strId = new Integer(_id).toString();

        try {
            URL website = new URL(uri + "/file/download/" + strId);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(DEFAULT_IMAGENAME);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    */


}
