package com.example.backend.Syncronisation.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

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
 * Created by laubi on 1/27/2017.
 */

public class FileExecuter extends AsyncTask<File, String, String> {
    private Context context;
    private boolean isDownload;
    private java.io.File original;
    private File metaFile;
    private int id;

    @Override
    protected String doInBackground(File... files) {
        System.out.println("in Fileexecuter1");
        if (isDownload) {
            File file = files[0];
            try {
                System.out.println("File to download - " + file.getFileName() + "." + file.getContentType());

                InputStream in = null;
                int bytesToRead = 10000;
                byte[] buffer = new byte[bytesToRead];

                URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/file/content/" + file.getId());

                HttpURLConnection con = (HttpURLConnection) serverURL.openConnection();

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
        } else {
            metaFile.setId(id);
            try {
                /*
                String charset = "UTF-8";
                metaFile.setId(id);
                String paramName = "file";
                String contentType = metaFile.getContentType();
                //URLConnection.guessContentTypeFromName(f.getName())

                URL serverURL = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/file/content/" + metaFile.getId());

                System.out.println("Uploading " + metaFile.getFileName() + " to " + serverURL.getPath());
                String boundary = "---------------------------" + System.currentTimeMillis();
                byte[] boundarybytes = ("\r\n--" + boundary + "\r\n").getBytes();
                System.out.println(boundarybytes.length);
                HttpURLConnection con = (HttpURLConnection) serverURL.openConnection();


                con.setDoOutput(true);    // indicates POST method
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ boundary);

                con.setRequestMethod("POST");
                con.setRequestProperty("Connection", "Keep-Alive");
                con.setRequestProperty("Keep-Alive", "header");
                con.setRequestProperty("Accept", "multipart/form-data");

                System.out.println("getting output stream");

                OutputStream outputStream = con.getOutputStream();
                BufferedOutputStream outputStreamToServer = new BufferedOutputStream(outputStream);


                outputStreamToServer.write(boundarybytes, 0, boundarybytes.length);
                outputStreamToServer.write(boundarybytes, 0, boundarybytes.length);

                String header = "Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + original.getAbsolutePath() + "\"\r\nContent-Type: " + contentType + "\r\n\r\n";

                byte[] headerbytes = header.getBytes();

                outputStreamToServer.write(headerbytes, 0, headerbytes.length);

                System.out.println("gettting file stream");

                byte[] buf = new byte[4096];

                InputStream is = new FileInputStream(original);


                int c = 0;

                while ((c = is.read(buf, 0, buf.length)) > 0) {
                    outputStreamToServer.write(buf, 0, c);
                }
                is.close();

                byte[] trailer = ("\r\n--" + boundary + "--\r\n").getBytes();
                outputStreamToServer.write(trailer, 0, trailer.length);
                System.out.println(outputStreamToServer.toString());

                outputStreamToServer.flush();
                outputStream.flush();


                System.out.println("gettting input stream");
                InputStream inputStream = con.getInputStream();

                System.out.println("gettting bytearray stream");

                String s1 = getStringFromInputStream(inputStream);
outputStreamToServer.close();
outputStream.close();
                inputStream.close();
                System.out.println("inputstrem closed");
                System.out.println(s1);

                metaFile.setId(id);

                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                DataInputStream inStream = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary =  "*****";
                int bytesRead, bytesAvailable, bufferSize;

                byte[] buffer;
                int maxBufferSize = 1*1024*1024;

                String urlString ="http://" + DataReader.IP + ":8080/ClassM8Web/services/file/content/" + metaFile.getId();
                try{
                    //------------------ CLIENT REQUEST
                    FileInputStream fileInputStream = new FileInputStream(original);
                    // open a URL connection to the Servlet
                    URL url = new URL(urlString);
                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    // Allow Inputs
                    conn.setDoInput(true);
                    // Allow Outputs
                    conn.setDoOutput(true);
                    // Don't use a cached copy.
                    conn.setUseCaches(false);
                    // Use a post method.
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
                    dos = new DataOutputStream( conn.getOutputStream() );
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + original.getName() + "\"" + lineEnd); // uploaded_file_name is the Name of the File to be uploaded
                    dos.writeBytes(lineEnd);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    while (bytesRead > 0){
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    fileInputStream.close();
                    dos.flush();
                    dos.close();
                }
                catch (MalformedURLException ex){
                    //Log.e("Debug", "error: " + ex.getMessage(), ex);
                }
                catch (IOException ioe){
                    //Log.e("Debug", "error: " + ioe.getMessage(), ioe);
                }
                //------------------ read the SERVER RESPONSE
                try {
                    inStream = new DataInputStream ( conn.getInputStream() );
                    String str;
                    while (( str = inStream.readLine()) != null){
                      //  Log.e("Debug","Server Response "+str);
                        System.out.println(str);
                    }
                    inStream.close();
                }
                catch (IOException ioex){
                   // Log.e("Debug", "error: " + ioex.getMessage(), ioex);
                }*/

                HttpURLConnection connection = null;
                DataOutputStream outputStream = null;
                InputStream inputStream = null;

                String twoHyphens = "--";
                String boundary =  "*****"+Long.toString(System.currentTimeMillis())+"*****";
                String lineEnd = "\r\n";

                String result = "";

                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1*1024*1024;

                try {
                    java.io.File file = original;
                    FileInputStream fileInputStream = new FileInputStream(file);

                    URL url = new URL("http://" + DataReader.IP + ":8080/ClassM8Web/services/file/content/" + metaFile.getId());
                    connection = (HttpURLConnection) url.openConnection();

                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
                    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);

                    outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\"" + original.getName() +"\"" + lineEnd);
                    outputStream.writeBytes("Content-Type: "+"application/octet-stream" + lineEnd);
                    outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
                    outputStream.writeBytes(lineEnd);

                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    while(bytesRead > 0) {
                        outputStream.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    outputStream.writeBytes(lineEnd);
/*
                    String post = "none";
                    // Upload POST Data
                    String[] posts = post.split("&");
                    int max = posts.length;
                    for(int i=0; i<max;i++) {
                        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                        String[] kv = posts[i].split("=");
                        outputStream.writeBytes("Content-Disposition: form-data; name=\"" + kv[0] + "\"" + lineEnd);
                        outputStream.writeBytes("Content-Type: text/plain"+lineEnd);
                        outputStream.writeBytes(lineEnd);
                        outputStream.writeBytes(kv[1]);
                        outputStream.writeBytes(lineEnd);
                    }
*/
                    outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    inputStream = connection.getInputStream();
                    result = this.convertStreamToString(inputStream);

                    fileInputStream.close();
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();

                    return result;
                } catch(Exception e) {
                  //  Log.e("MultipartRequest","Multipart Form Upload Error");
                    e.printStackTrace();
                    return "error";
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
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

    public java.io.File getOriginal() {
        return original;
    }

    public void setOriginal(java.io.File original) {
        this.original = original;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public File getMetaFile() {
        return metaFile;
    }

    public void setMetaFile(File metaFile) {
        this.metaFile = metaFile;
    }
}
