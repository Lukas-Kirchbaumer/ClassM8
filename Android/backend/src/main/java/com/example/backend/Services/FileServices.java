package com.example.backend.Services;

import com.example.backend.Dto.File;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Executer;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.Results.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

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



    private String encode(byte[] fileData) {
        StringBuilder sb = new StringBuilder();
        for (byte b:fileData) {
            sb.append(b);
        }
        return sb.toString();

    }

    public String uploadFile(int id, java.io.File f) {
        String retValue = "image uploaded ??";
        try {
            String strId = Integer.toString(id);

            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
            BodyPart bp = multiPart.bodyPart(new FileDataBodyPart("file", f, MediaType.APPLICATION_OCTET_STREAM_TYPE));

            Executer e = new Executer();
            e.setMediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE.toString());

            e.setData(bp.toString());

            URL serverURL = new URL("http://"+ DataReader.IP + ":8080/ClassM8Web/services/content/" + id);
            e.execute(serverURL);

            String strFromWebService = e.get();

            System.out.println("returned string: " + strFromWebService);

            e.setMethod("POST");
        }catch (Exception e){
            e.printStackTrace();
        }

        return retValue;
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
