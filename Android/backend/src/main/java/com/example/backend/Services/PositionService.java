package com.example.backend.Services;

import android.content.Context;

import com.example.backend.AsyncTasks.Executer;
import com.example.backend.Database.Database;
import com.example.backend.Dto.Position;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.Results.PositionResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by laubi on 4/4/2017.
 */

public class PositionService {

    private Gson gson = new GsonBuilder().create();
    /*
}
@Path("position")
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getPositonsBySchoolclass(@Context Request request, @Context HttpServletRequest httpServletRequest,
                                             @QueryParam("scid") String scid) {

*/

    private static PositionService instance = null;

    public static PositionService getInstance() {
        if (instance == null) {
            instance = new PositionService();
        }
        return instance;
    }

    public ArrayList<Position> getPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        try {
            Executer executer = new Executer();

            String url = "http://" + DataReader.IP + ":8080/ClassM8Web/services/position?scid=" + Database.getInstance().getCurrentSchoolclass().getId();

            System.out.println(url);
            URL serverURL = new URL(url);

            executer.setMethod("GET");
            executer.execute(serverURL);

            String strFromWebService = executer.get();

            System.out.println("returned string: " + strFromWebService);

            PositionResult pr = gson.fromJson(strFromWebService, PositionResult.class);

            if (pr.isSuccess()) {
                positions = new ArrayList<>(pr.getContent());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return positions;
    }
}
