package com.example.laubi.myapplication.Activities;

import android.app.Activity;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.backend.Dto.M8;
import com.example.backend.Interfaces.DataReader;
import com.example.laubi.myapplication.R;

public class AddM8Activity extends Activity {

    private DataReader dr = new DataReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_m8);
        final Button btnAddM8 = (Button) findViewById(R.id.btnAddM8);
        final TextView txtNewM8Email = (TextView) findViewById(R.id.txtAddM8Email);


        btnAddM8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtNewM8Email.getText().toString();

                M8 mate = DataReader.getInstance().getMateByEMail(email);
                if(mate!=null)
                    DataReader.getInstance().addMateToSchoolclass(mate);
                else
                    System.out.println("wellp");
                finish();
            }
        });

    }
}
