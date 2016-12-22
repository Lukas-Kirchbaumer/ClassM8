package com.example.backend;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by laubi on 12/7/2016.
 */

public class Executer extends AsyncTask<URL, String, String> {

    private String content;
    String data ="";

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    String method = "";
/*
    protected void onPreExecute() {

        try{
            // Set Request parameter
            data +="&" + URLEncoder.encode("data", "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
*/
    protected String doInBackground(URL... urls) {

        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) urls[0].openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
        e.printStackTrace();
        }
        try {

            if(method != "GET"){
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);}

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return data;
    }

    private void readStream(InputStream in) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        in.close();
        content = sb.toString();
    }

    private void writeStream(OutputStream out) throws Exception{
        out.write(data.getBytes());
        out.flush();
    }

}