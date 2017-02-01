package com.example.laubi.myapplication.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.backend.Dto.M8;
import com.example.backend.Services.UserServices;
import com.example.laubi.myapplication.R;

public class NewAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        final Button btnNewAccCreate = (Button) findViewById(R.id.btnNewAccCreate);
        final EditText txtNewAccFirstname = (EditText) findViewById(R.id.txtNewAccFirstname);
        final EditText txtNewAccLastname = (EditText) findViewById(R.id.txtNewAccLastname);
        final EditText txtNewAccEmail = (EditText) findViewById(R.id.txtNewAccEmail);
        final EditText txtNewAccPw = (EditText) findViewById(R.id.txtNewAccPassword);

        btnNewAccCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                M8 m8 = new M8();
                m8.setFirstname(txtNewAccFirstname.getText().toString());
                m8.setLastname(txtNewAccLastname.getText().toString());
                m8.setEmail(txtNewAccEmail.getText().toString());
                m8.setPassword(txtNewAccPw.getText().toString());

                System.out.println(m8.toString());
                UserServices.getInstance().createNewUser(m8);
                finish();
            }
        });


    }
}
