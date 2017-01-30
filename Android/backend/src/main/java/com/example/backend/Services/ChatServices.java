package com.example.backend.Services;

import com.example.backend.Database;
import com.example.backend.Dto.Chat;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Message;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Executer;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.MappedObjects.MappedSchoolclass;
import com.example.backend.Results.ChatResult;
import com.example.backend.Results.Result;
import com.example.backend.Results.SchoolclassResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.net.URL;
import java.util.Vector;
import java.util.logging.Level;

/**
 * Created by laubi on 1/18/2017.
 */

public class ChatServices {
    private Gson gson = new Gson();
    private JsonParser parser;

    public String writeMessage(M8 user, String s) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/schoolclass/chat?m8id=" + user.getId()+ "&scid="+ Database.getInstance().getCurrentSchoolclass().getId());

            executer.setMethod("POST");
            executer.setData(s);
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            Result r = gson.fromJson(o, Result.class);
        } catch (Exception e) {
            s = null;
            e.printStackTrace();
        }
        return s;
    }

    public Vector<Message> receiveMessages() {
        Vector<Message> messages;
        try
        {
            Executer executer = new Executer();
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/schoolclass/chat?scid="+ Database.getInstance().getCurrentSchoolclass().getId());

            executer.setMethod("GET");
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
            ChatResult r = gson.fromJson(o, ChatResult.class);
            messages = r.getContent();
            System.out.println(Chat.getInstance().addMultipleMessages(messages));
            System.out.println("loaded class " + messages);
        } catch (Exception e) {
            messages = null;
        }

    return messages;
    }
}
