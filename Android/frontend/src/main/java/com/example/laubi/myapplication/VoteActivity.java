package com.example.laubi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.backend.DTO.M8;

import java.util.ArrayList;

public class VoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        Spinner spPresidentChoice = (Spinner) findViewById(R.id.spPresidentChoice);
        Spinner spDeputyChoice = (Spinner) findViewById(R.id.spDeputyChoice);
        Button btnVote = (Button) findViewById(R.id.btnVote);

        ArrayList<M8> m8s = new ArrayList<M8>();

          m8s.add(new M8(1,"John","T","J@T.com","asdf",false,2));
          m8s.add(new M8(2,"Tom","L","T@L.com","1234",false,8));

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, m8s);

        spPresidentChoice.setAdapter(spinnerArrayAdapter);

        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(spPresidentChoice.getSelectedItem());

                Toast.makeText(getApplicationContext(), "You Voted!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
