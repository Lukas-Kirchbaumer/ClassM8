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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by laubi on 12/7/2016.
 */

public class FileExecuter extends AsyncTask<URL, File, File> {
    @Override
    protected File doInBackground(URL... params) {
        return null;
    }
/*
    private File content;
    File data;

    public File getData() {
        return data;
    }

    public void setData(File data) {
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    String method = "";

    protected File doInBackground(URL... urls) {

        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) urls[0].openConnection();
            System.out.println("opened Connection");
            urlConnection.setRequestMethod(method);

            if(method =="GET"){
                urlConnection.setRequestProperty("Content-Type", "application/octet_stream;charset=utf-8");
                urlConnection.setRequestProperty("Accept", "application/octet_stream");
            }else{
                urlConnection.setRequestProperty("Content-Type", "multipart/form_data;charset=utf-8");
                urlConnection.setRequestProperty("Accept", "multipart/form_data");
            }

            System.out.println("set Properties");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setAllowUserInteraction(false);

            DataOutputStream dstream = new DataOutputStream(urlConnection.getOutputStream());

            // The POST line
            dstream.writeBytes(s);
            dstream.close();

            // Read Response
            InputStream in = urlConnection.getInputStream();
            int x;
            while ( (x = in.read()) != -1)
            {
                System.out.write(x);
            }
            in.close();

            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuffer buf = new StringBuffer();
            String line;

            while ((line = r.readLine())!=null) {
                buf.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return content;
    }

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


    public void uploadFile(){
    String strId = txtid.getText();

        File fileToUpload = new File(txtimage.getText());
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
        multiPart.bodyPart(new FileDataBodyPart("file", fileToUpload,
                MediaType.APPLICATION_OCTET_STREAM_TYPE));

        WebResource directTarget = target.path("upload/" + strId);
        directTarget.accept(MediaType.MULTIPART_FORM_DATA_TYPE).post(multiPart);
        }

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
