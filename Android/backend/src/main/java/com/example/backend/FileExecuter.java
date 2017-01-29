package com.example.backend;

import android.content.Context;
import android.os.AsyncTask;

import com.example.backend.Dto.File;
import com.example.backend.Interfaces.DataReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by laubi on 1/27/2017.
 */

public class FileExecuter  extends AsyncTask<File, String,String>{
    private Context context;
    private boolean isDownload;

    @Override
    protected String doInBackground(File... files) {
        if(isDownload) {
            File file = files[0];
            try {
                System.out.println("File to download - " + file.getFileName() + "." + file.getContentType());

                InputStream in = null;
                int bytesToRead = 10000;
                byte[] buffer = new byte[bytesToRead];

                URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/file/content/" + file.getId());

                HttpURLConnection con = (HttpURLConnection) serverURL.openConnection();

                con.setConnectTimeout(1000);
                in = con.getInputStream();

                java.io.File nf = new java.io.File(context.getFilesDir(), file.getFileName());
                OutputStream out = new FileOutputStream(nf);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
                System.out.println("End");
                System.out.println(file.getFileName());
                System.out.println(nf.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            /*
            System.out.println("Uploading "+ file+ " to "+ url);
            String boundary = "---------------------------" + System.currentTimeMillis();
            byte[] boundarybytes = ("\r\n--" + boundary + "\r\n").getBytes();


            URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/file/content/" + file.getId());

            HttpURLConnection con = (HttpURLConnection) serverURL.openConnection();

            con.setConnectTimeout(1000);

            con.setDoOutput(true);    // indicates POST method
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Keep-Alive", "header");

            OutputStream outputStream = con.getOutputStream();

            outputStream.write(boundarybytes, 0, boundarybytes.length);

            outputStream.write(boundarybytes, 0, boundarybytes.length);

            String header = "Content-Disposition: form-data; name=\""+paramName+"+\"; filename=\""+file+"\"\r\nContent-Type: "+contentType+"\r\n\r\n";

            byte[] headerbytes = header.getBytes();

            outputStream.write(headerbytes, 0, headerbytes.length);

            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while ((bytesRead = fileInputStream.read(buffer, 0, buffer.length)) != 0)
            {
                outputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();

            byte[] trailer = ("\r\n--" + boundary + "--\r\n").getBytes();
            outputStream.write(trailer, 0, trailer.length);
            outputStream.close();

            InputStream inputStream = con.getInputStream();
            StringReader r = new StringReader()


            WebResponse wresp = null;
            try
            {
                wresp = wr.GetResponse();
                Stream stream2 = wresp.GetResponseStream();
                StreamReader reader2 = new StreamReader(stream2);
                Console.WriteLine(string.Format("File uploaded, server response is: {0}", reader2.ReadToEnd()));
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error uploading file", ex);
                if (wresp != null)
                {
                    wresp.Close();
                    wresp = null;
                }
            }
            finally
            {
                wr = null;
            }
        }*/
        }
        return "";
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }
}
