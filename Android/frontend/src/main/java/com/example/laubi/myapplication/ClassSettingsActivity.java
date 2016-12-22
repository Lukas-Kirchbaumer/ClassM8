package com.example.laubi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.backend.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Schoolclass;
import com.example.backend.Interfaces.DataReader;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClassSettingsActivity extends Activity {

    final DataReader dr = new DataReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_settings);

        final EditText txtClassName = (EditText) findViewById(R.id.txtClassSettingsName);
        final EditText txtNewSchool = (EditText) findViewById(R.id.txtClassSettingsSchool);
        final EditText txtNewRoom = (EditText) findViewById(R.id.txtClassSettingsRoom);
        final Button btnUpdateClass = (Button) findViewById(R.id.btnUpdateClass);
        final Button btnDeleteClass = (Button) findViewById(R.id.btnDeleteClass);
        final TextView tvClassSettingsError = (TextView) findViewById(R.id.tvClassSettingsError);
        final ListView lvClassM8s = (ListView) findViewById(R.id.lvClassM8s);

        txtClassName.setText(Database.getInstance().getCurrentSchoolclass().getName());
        txtNewRoom.setText(Database.getInstance().getCurrentSchoolclass().getRoom());
        txtNewSchool.setText(Database.getInstance().getCurrentSchoolclass().getSchool());

        //ArrayList<M8> m8s = (ArrayList<M8>) Database.getInstance().getCurrentSchoolclass().getClassMembers();

        ArrayList<M8> m8s = new ArrayList<M8> ();

        m8s.add(new M8(1,"asdf","asdf","asdf","asfd",false, 2, new Schoolclass()));
        m8s.add(new M8(1,"dsf","asdf","asdf","asfd",false, 2, new Schoolclass()));
        m8s.add(new M8(1,"123","asdf","asdf","asfd",false, 2, new Schoolclass()));
        m8s.add(new M8(1,"1234","asdf","asdf","asfd",false, 2, new Schoolclass()));
        m8s.add(new M8(1,"bv","asdf","asdf","asfd",false, 2, new Schoolclass()));

        ArrayAdapter listViewArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, m8s);

        lvClassM8s.setAdapter(listViewArrayAdapter);

        btnDeleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr.deleteClass(Database.getInstance().getCurrentSchoolclass());

                Database.getInstance().setCurrentSchoolclass(null);

                finish();
            }
        });

        btnUpdateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtClassName.getText().length() != 0 && txtNewRoom.getText().length() != 0 && txtNewSchool.getText().length() != 0) {
                    Schoolclass sc = new Schoolclass();
                    sc.setName(txtClassName.getText().toString());
                    sc.setRoom(txtNewRoom.getText().toString());
                    sc.setSchool(txtNewSchool.getText().toString());

                    dr.updateClass(sc);

                    Database.getInstance().setCurrentSchoolclass(sc);

                    finish();
                }
                else{
                    tvClassSettingsError.setText("Kein Feld darf leer sein!");
                }
            }
        });


    }
}
