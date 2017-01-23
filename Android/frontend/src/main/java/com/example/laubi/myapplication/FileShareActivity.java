package com.example.laubi.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.backend.Database;
import com.example.backend.Dto.File;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Interfaces.DataReader;

import java.util.ArrayList;

public class FileShareActivity extends Activity {

    private ListView lvDownloads;
    private Button btnUpload;
    private Button btnDownload;
    private DataReader dr = new DataReader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_file_share);super.onCreate(savedInstanceState);
        lvDownloads = (ListView) findViewById(R.id.lvDownloads);
        btnDownload = (Button) findViewById(R.id.btnDownloadFile);
        btnUpload = (Button) findViewById(R.id.btnUploadFile);

        Schoolclass sc = dr.getSchoolclassByUser(Database.getInstance().getCurrentMate());

        ArrayList<com.example.backend.Dto.File> files;
        System.out.print(Database.getInstance().getCurrentSchoolclass());
        files = (ArrayList<File>)sc.getFiles();


        if(files == null || files.size() == 0) {
                files = new ArrayList<File>();
                File f = new File();
                f.setFileName("Keine Inhalte");
            files.add(f);
        }

        ArrayAdapter listViewArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, files);

        lvDownloads.setAdapter(listViewArrayAdapter);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               File selFile = (File)lvDownloads.getSelectedItem();

                //Todo: download File
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleFileDialog fileOpenDialog =  new SimpleFileDialog(
                        FileShareActivity.this,
                        "FileOpen..",
                        new SimpleFileDialog.SimpleFileDialogListener()
                        {
                            @Override
                            public void onChosenDir(String chosenDir)
                            {
                                // The code in this function will be executed when the dialog OK button is pushed
                                System.out.println(chosenDir);
                                java.io.File f = new java.io.File(chosenDir);

                                dr.uploadFile(f);
                            }
                        }
                );
                fileOpenDialog.chooseFile_or_Dir();
            }
        });
    }
}
