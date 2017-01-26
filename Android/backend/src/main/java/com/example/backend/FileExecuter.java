package com.example.backend;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Debug;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.net.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

/**
 * Created by laubi on 12/7/2016.
 */

public class FileExecuter extends AsyncTask<URL, File, File> {

    private File content;
    String method = "";
    File data;



    public File getData() {
        return data;
    }

    public void setData(java.io.File data) {
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }




    protected File doInBackground(URL... urls) {

        HttpURLConnection urlConnection = null;

        if(method == "POST"){
            uploadFile(data);
        }

        if(method == "GET"){

        }

        return null;
    }



    /*

    private void readStream(InputStream in) throws Exception {
        FileInputStream file = new FileInputStream(data);
        byte fileData[] = new byte[(int) data.length()];
        file.read(fileData);
    }

    private void writeStream(OutputStream out) throws Exception{
            int length;
            byte[] buffer = new byte[1024];
            while((length = responseStream.read(buffer)) != -1) {
                out.write(buffer, 0, length);
                out.write(buffer, 0, length);
            }
            out.flush();
            responseStream.close();
        }
    }
*/

    public void uploadFile(File file) {
        try {

            URL serverURL = new URL("http://10.0.0.9:8080/ClassM8Web/services/fileshare/");

            String charset = "UTF-8";

            MultipartUtility multipart = new MultipartUtility(serverURL.getPath(), charset);

            multipart.addFilePart(file.getName(), file);

            List<String> response = multipart.finish();

            for (String line : response) {
                String responsemy = line;
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
        public void downloadFile(){

    try {
            URL website = new URL(target.path("") + "BookService/download/" + strId);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(DEFAULT_IMAGENAME);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception ex) {
            lblMessages.setText("Something went wrong");
        }
       }
       */
}
