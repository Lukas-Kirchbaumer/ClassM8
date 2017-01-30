package com.example.laubi.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.backend.Database.Database;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Interfaces.DataReader;

public class NewClassActivity extends Activity {

    private DataReader dr = new DataReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        final Button btnNewClass = (Button) findViewById(R.id.btnNewClass);
        final EditText txtNewClassName = (EditText) findViewById(R.id.txtNewClassName);
        final EditText txtNewClassSchool = (EditText) findViewById(R.id.txtNewClassSchool);
        final EditText txtNewClassRoom = (EditText) findViewById(R.id.txtNewClassRoom);


        btnNewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schoolclass sc = dr.createNewClass(txtNewClassName.getText().toString(), txtNewClassSchool.getText().toString(), txtNewClassRoom.getText().toString());
                Database.getInstance().setCurrentSchoolclass(sc);
                finish();
            }
        });

    }
}
