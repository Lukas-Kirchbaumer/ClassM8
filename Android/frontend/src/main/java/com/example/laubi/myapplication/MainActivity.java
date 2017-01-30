package com.example.laubi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.backend.Dto.*;
import com.example.backend.Interfaces.*;

public class MainActivity extends Activity {

    public static Activity mainActivity;
    private DataReader dr = new DataReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final Button btnNewAccount = (Button) findViewById(R.id.btnNewAccount);
        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        final TextView tvLoginEmailError = (TextView) findViewById(R.id.tvLoginEmailError);
        final TextView tvLoginPasswordError = (TextView) findViewById(R.id.tvLoginPasswordError);

        btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewAccountActivity.class);

                //Falls er wegen Theme.AppCompat theme blablba weint
                //android:theme="@style/Theme.AppCompat.Light"
                //im Manifest bei der Activity einfügen
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Boolean correct = true;
                if (txtEmail.getText().length() == 0) {
                    tvLoginEmailError.setText("Keine Email");
                    correct = false;
                } else {
                    tvLoginEmailError.setText("");
                }
                if (txtPassword.getText().length() == 0) {
                    tvLoginPasswordError.setText("Kein Passwort");
                    correct = false;
                } else {
                    tvLoginPasswordError.setText("");
                }
                System.out.println(txtEmail.getText() + "  " + txtPassword.getText());
                if (correct) {
                    System.out.println("correct");
                    M8 currM8 = dr.login(txtEmail.getText().toString(), txtPassword.getText().toString());
                    //  M8 currM8 = new M8(1,"asdf","asdf","asdf","asfd",false, 2, new Schoolclass());
                    //  Database.getInstance().setCurrentMate(currM8);


                    if (currM8 != null) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                        //Falls er wegen Theme.AppCompat theme blablba weint
                        //android:theme="@style/Theme.AppCompat.Light"
                        //im Manifest bei der Activity einfügen
                        startActivity(intent);
                    } else {
                        tvLoginPasswordError.setText("M8 nicht bekannt");
                    }

                }

            }
        });


    }
}
