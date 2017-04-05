package com.example.backend.Services;

import android.content.Context;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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

    public ArrayList<Message> receiveMessages(Context x) {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            Executer executer = new Executer();

            String timestamp = Database.getInstance().getTimeStampOfLastMessageSentToCurrentUser(x);


String url = "http://" + DataReader.IP + ":8080/ClassM8Web/services/schoolclass/chat?scid=" +java.net.URLEncoder.encode(Long.toString(Database.getInstance().getCurrentSchoolclass().getId()),"UTF-8") + "&limit="+ java.net.URLEncoder.encode(timestamp,"UTF-8");

            System.out.println(url);
            URL serverURL = new URL(url);

            executer.setMethod("GET");
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            ChatResult cr = gson.fromJson(strFromWebService, ChatResult.class);

            if(cr.schoolclassChat != null) {
                messages = new ArrayList<>(cr.schoolclassChat.getMessages());
                System.out.println(cr.schoolclassChat.getMessages());
                saveReceivedMessages(messages, x);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    private void saveReceivedMessages(ArrayList<Message> messages, Context x) {
        for(Message m:messages){
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(m.getDateTime().getTime());
            cal.add(Calendar.HOUR, 2);
            m.setDateTime(new Timestamp(cal.getTime().getTime()));

            Database.getInstance().addSingleMessage(m);
            Database.getInstance().addLocalMessage(m,x);
        }
    }
}
