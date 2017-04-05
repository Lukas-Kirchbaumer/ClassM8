package com.example.backend.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.backend.Dto.Emote;
import com.example.backend.Dto.File;
import com.example.backend.Interfaces.DataReader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by laubi on 3/30/2017.
 */

public class EmoteExecuter extends AsyncTask<Emote, String, String> {

    private int id;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Emote... emotes) {
        Emote emote = emotes[0];
        try {
            System.out.println("File to download - " + emote.getShortString() + "." + emote.getFileName());

            InputStream in = null;
            int bytesToRead = 10000;
            byte[] buffer = new byte[bytesToRead];

            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/emote/content/" + emote.getId());

            HttpURLConnection con = (HttpURLConnection) serverURL.openConnection();

            in = con.getInputStream();

            java.io.File nf = new java.io.File(context.getFilesDir(), emote.getFileName());

            OutputStream out = new FileOutputStream(nf);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            System.out.println("End");
            System.out.println(emote.getFileName());
            System.out.println(nf.getPath());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
