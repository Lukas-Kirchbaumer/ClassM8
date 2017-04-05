package com.example.backend.Syncronisation.AsyncTasks;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by laubi on 12/7/2016.
 */

public class Executer extends AsyncTask<URL, String, String> {

    String data = "";
    String method = "";
    String mediaType = "application/json";
    private String content;

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

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    protected String doInBackground(URL... urls) {

        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) urls[0].openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            urlConnection.setRequestProperty("Accept", mediaType);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (method != "GET" && method != "DELETE") {
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.setConnectTimeout(5000);
                System.out.println("starting to write outputStream");
                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                writeStream(out);
            }
            int statusCode = urlConnection.getResponseCode();
            System.out.println(statusCode);
            System.out.println("starting to read inputStream");
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
            System.out.println("got inputStream");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return content;
    }

    private void readStream(InputStream in) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        in.close();
        content = sb.toString();
    }

    private void writeStream(OutputStream out) throws Exception {
        out.write(data.getBytes());
        out.flush();
    }

}
