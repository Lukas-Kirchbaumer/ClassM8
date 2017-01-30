package com.example.laubi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.backend.Database.Database;
import com.example.backend.Dto.*;
import com.example.backend.Interfaces.*;

import java.util.ArrayList;

public class HomeActivity extends Activity {
    static final int UPDATE_DELETE_CLASS = 1;
    static final int VOTE = 2;
    static final int ADDM8 = 3;
    TextView tvCurrClass = null;
    private  ArrayList<M8> m8s;
    private Button btnStartVote;
    private Button btnDownloads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MainActivity.mainActivity.finish();
        final Button btnM8Settings = (Button) findViewById(R.id.btnM8Settings);
        final Button btnOpenAddM8 = (Button) findViewById(R.id.btnOpenAddM8);
        btnStartVote = (Button) findViewById(R.id.btnStartVote);
        tvCurrClass = (TextView) findViewById(R.id.tvCurrClass);
        btnDownloads = (Button) findViewById(R.id.btnDownloads);
        final ListView lvClassM8s = (ListView) findViewById(R.id.lvClassM8s);

        M8 m8 = Database.getInstance().getCurrentMate();

        System.out.println(m8);
        getCurrClass(m8);

         m8s = (ArrayList<M8>) Database.getInstance().getCurrentSchoolclass().getClassMembers();

        ArrayAdapter listViewArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, m8s);

        lvClassM8s.setAdapter(listViewArrayAdapter);

        if(Database.getInstance().getCurrentMate().isHasVoted()){
            btnStartVote.setVisibility(View.GONE);
        }

        btnDownloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDownloads = new Intent(HomeActivity.this, FileShareActivity.class);

                startActivity(intentDownloads);
            }
        });

        btnM8Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(HomeActivity.this, UserSettingsActivity.class);

                startActivity(intentSettings);
            }
        });

        btnOpenAddM8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(HomeActivity.this, AddM8Activity.class);
                startActivityForResult(intentSettings,ADDM8);
            }
        });

        btnStartVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(HomeActivity.this, VoteActivity.class);
                startActivityForResult(intentSettings,VOTE);
            }
        });
    }

    public void getCurrClass(com.example.backend.Dto.M8 m8){

        DataReader dr = new DataReader();
        Database.getInstance().setCurrentSchoolclass(dr.getSchoolclassByUser(m8));

        if(Database.getInstance().getCurrentSchoolclass() != null) {
            showClass();
        }
        else{
            createClass();
        }

    }

    public void showClass(){
        tvCurrClass.setText(Database.getInstance().getCurrentSchoolclass().getName());
        tvCurrClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClassSettings = new Intent(HomeActivity.this, ClassSettingsActivity.class);
                startActivityForResult(intentClassSettings, UPDATE_DELETE_CLASS);
            }
        });
    }

    public void createClass(){
        tvCurrClass.setText("Neue Klasse...");
        tvCurrClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClassSettings = new Intent(HomeActivity.this, NewClassActivity.class);
                startActivityForResult(intentClassSettings, UPDATE_DELETE_CLASS);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_DELETE_CLASS) {
            if(Database.getInstance().getCurrentSchoolclass() != null) {
                showClass();
            }
            else{
                createClass();
            }
        }
        if(requestCode == VOTE){
            if(Database.getInstance().getCurrentMate().isHasVoted() == true){
                btnStartVote.setVisibility(View.GONE);
            }
        }
        if(requestCode == ADDM8){
           m8s = (ArrayList<M8>) Database.getInstance().getCurrentSchoolclass().getClassMembers();
        }
    }
}
