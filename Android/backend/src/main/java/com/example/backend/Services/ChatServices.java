package com.example.backend.Services;

import com.example.backend.Database.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Message;
import com.example.backend.AsyncTasks.Executer;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.Results.ChatResult;
import com.example.backend.Results.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Semaphore;

/**
 * Created by laubi on 1/18/2017.
 */

public class ChatServices {
    private Gson gson = new GsonBuilder().create();
    private JsonParser parser;

    public String writeMessage(M8 user, String s) {
        Executer executer = new Executer();
        try {
            System.out.println(Database.getInstance().getCurrentSchoolclass().getId());
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/schoolclass/chat?m8id=" + user.getId() + "&scid=" + Database.getInstance().getCurrentSchoolclass().getId());

            executer.setMethod("POST");
            executer.setData(s);
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            Result r = gson.fromJson(strFromWebService, Result.class);
        } catch (Exception e) {
            s = null;
            e.printStackTrace();
        }
        return s;
    }

    public ArrayList<Message> receiveMessages() {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            Executer executer = new Executer();
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/schoolclass/chat?scid=" + Database.getInstance().getCurrentSchoolclass().getId());

            executer.setMethod("GET");
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            ChatResult cr = gson.fromJson(strFromWebService, ChatResult.class);
            messages = new ArrayList<>(cr.schoolclassChat.getMessages());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }
}
