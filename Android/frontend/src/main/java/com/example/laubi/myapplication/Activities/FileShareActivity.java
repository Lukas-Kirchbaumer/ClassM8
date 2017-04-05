package com.example.laubi.myapplication.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.backend.Database.Database;
import com.example.backend.Dto.File;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Interfaces.DataReader;
import com.example.laubi.myapplication.R;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.util.ArrayList;

public class FileShareActivity extends Activity {

    private ListView lvDownloads;
   // private Button btnUpload;
    private Button btnDownload;
    private DataReader dr = new DataReader();
    private File currentSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_file_share);
        super.onCreate(savedInstanceState);
        lvDownloads = (ListView) findViewById(R.id.lvDownloads);
        btnDownload = (Button) findViewById(R.id.btnDownloadFile);
      //  btnUpload = (Button) findViewById(R.id.btnUploadFile);

        Schoolclass sc = dr.getSchoolclassByUser(Database.getInstance().getCurrentMate());

        ArrayList<com.example.backend.Dto.File> files;
        System.out.print(Database.getInstance().getCurrentSchoolclass());
        files = (ArrayList<File>) sc.getFiles();


        if (files == null || files.size() == 0) {
            files = new ArrayList<File>();
            File f = new File();
            f.setFileName("Keine Inhalte");
            files.add(f);
        }

        ArrayAdapter<File> listViewArrayAdapter = new ArrayAdapter<File>(this,
                android.R.layout.simple_list_item_1, files);

        lvDownloads.setAdapter(listViewArrayAdapter);

        lvDownloads.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCurrentSelected((File) lvDownloads.getItemAtPosition(position));
            }
        });


        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataReader.getInstance().downloadFile(currentSelected, FileShareActivity.this);
            }
        });

  /*      btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogProperties properties = new DialogProperties();

                properties.selection_mode = DialogConfigs.SINGLE_MODE;
                properties.selection_type = DialogConfigs.FILE_SELECT;

                properties.extensions = null;

                FilePickerDialog dialog = new FilePickerDialog(FileShareActivity.this, properties);
                dialog.setTitle("Select a File");

                dialog.setDialogSelectionListener(new DialogSelectionListener() {
                    @Override
                    public void onSelectedFilePaths(String[] files) {
                        System.out.println(files[0]);
                        DataReader.getInstance().uploadFile(new java.io.File(files[0]));
                    }
                });

                dialog.show();
            }
        });*/
    }

    private void setCurrentSelected(File selectedItem) {
        this.currentSelected = selectedItem;
    }
}
