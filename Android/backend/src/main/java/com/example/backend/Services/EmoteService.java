package com.example.backend.Services;

import android.content.Context;

import com.example.backend.AsyncTasks.EmoteExecuter;
import com.example.backend.AsyncTasks.Executer;
import com.example.backend.AsyncTasks.FileExecuter;
import com.example.backend.Database.Database;
import com.example.backend.Dto.Emote;
import com.example.backend.Dto.File;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.MappedObjects.MappedEmote;
import com.example.backend.Results.EmoteResult;
import com.example.backend.Results.LoginResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by laubi on 3/30/2017.
 */

public class EmoteService {

    private static EmoteService instance = null;
    private JsonParser parser = new JsonParser();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public static EmoteService getInstance() {
        if (instance == null) {
            instance = new EmoteService();
        }
        return instance;
    }

    public ArrayList<Emote> getEmotesOfSchoolclass(){
        ArrayList<Emote> emotes = new ArrayList<>();
        try {
            Executer executer = new Executer();

            String url = "http://" + DataReader.IP + ":8080/ClassM8Web/services/emote/all/"+ Database.getInstance().getCurrentSchoolclass().getId();

            System.out.println(url);
            URL serverURL = new URL(url);

            executer.setMethod("GET");
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            EmoteResult er = gson.fromJson(strFromWebService, EmoteResult.class);
            for(MappedEmote me: er.getEmotes()){
                emotes.add(me.toEmote());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emotes;
    }

    public void downloadFile(Emote emote, Context c) {
        if (emote != null) {
            EmoteExecuter ee = new EmoteExecuter();
            ee.setContext(c);
            ee.execute(emote);
        }
    }
}
