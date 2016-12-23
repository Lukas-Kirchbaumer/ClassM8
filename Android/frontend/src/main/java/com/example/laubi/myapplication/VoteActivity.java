package com.example.laubi.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.backend.Database;
import com.example.backend.Dto.*;
import com.example.backend.Interfaces.DataReader;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

public class VoteActivity extends Activity {

    public final DataReader dr = new DataReader();
    public Spinner spPresidentChoice;
    public Spinner spDeputyChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        spPresidentChoice = (Spinner) findViewById(R.id.spPresidentChoice);
        Button btnVote = (Button) findViewById(R.id.btnVote);


        ArrayList<com.example.backend.Dto.M8> m8s;
        System.out.print(Database.getInstance().getCurrentSchoolclass());
        m8s = (ArrayList<M8>) Database.getInstance().getCurrentSchoolclass().getClassMembers();

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, m8s);

        spPresidentChoice.setAdapter(spinnerArrayAdapter);


        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vote();
                finish();
            }
        });
    }

    public void vote(){
        M8 president = (M8) spPresidentChoice.getSelectedItem();

        dr.placeVoteForPresident(Database.getInstance().getCurrentMate(), president);
        Database.getInstance().getCurrentMate().setHasVoted(true);
        dr.updateUser(Database.getInstance().getCurrentMate());

        Toast.makeText(getApplicationContext(), "You Voted!", Toast.LENGTH_SHORT).show();

    }
}
