package com.example.backend.Services;

import com.example.backend.Dto.M8;
import com.example.backend.AsyncTasks.Executer;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.Results.M8Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.net.URL;

/**
 * Created by laubi on 12/22/2016.
 */

public class VotingServices {

    private static VotingServices instance = null;
    private Executer executer = new Executer();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private JsonParser parser = new JsonParser();

    public static VotingServices getInstance() {
        if (instance == null) {
            instance = new VotingServices();
        }
        return instance;
    }

    public void placeVoteForPresident(M8 user, M8 votedMate) {
        Executer executer = new Executer();
        try {
            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/election/?voterId=" + user.getId() + "&votedid=" + votedMate.getId());

            executer.setMethod("PUT");
            executer.setData("");
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            M8Result r = gson.fromJson(strFromWebService, M8Result.class);
        } catch (Exception e) {

        }
    }

  /*  public void placeVoteForPresidentDeputy(M8 user, M8 votedMate){
        try {
            URL serverURL = new URL("http://10.0.0.9:8080/ClassM8Web/services/president/Deputy/" + user.getId());

            executer.setMethod("POST");
            executer.setData(gson.toJson(votedMate, M8.class));
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            JsonElement o = parser.parse(strFromWebService);
        } catch (Exception e) {

        }
    }*/
}
