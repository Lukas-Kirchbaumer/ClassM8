package com.example.laubi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.backend.Database;
import com.example.backend.Dto.*;
import com.example.backend.Interfaces.*;

public class HomeActivity extends Activity {
    static final int UPDATE_DELETE_CLASS = 1;
    static final int VOTE = 2;
    TextView tvCurrClass = null;
    private Button btnStartVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MainActivity.mainActivity.finish();
        final Button btnM8Settings = (Button) findViewById(R.id.btnM8Settings);
        btnStartVote = (Button) findViewById(R.id.btnStartVote);
        tvCurrClass = (TextView) findViewById(R.id.tvCurrClass);

        M8 m8 = Database.getInstance().getCurrentMate();

        System.out.println(m8);
        getCurrClass(m8);

        if(Database.getInstance().getCurrentMate().isHasVoted()){
            btnStartVote.setVisibility(View.GONE);
        }

        btnM8Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(HomeActivity.this, UserSettingsActivity.class);

                startActivity(intentSettings);
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

    public void getCurrClass(M8 m8){

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
    }
}
