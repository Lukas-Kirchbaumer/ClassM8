package com.example.laubi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.backend.DTO.*;
import com.example.backend.Interfaces.*;

public class MainActivity extends Activity {
    public final static String EXTRA_M8 = "currM8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                if(txtEmail.getText().length() == 0){
                    tvLoginEmailError.setText("Keine Email");
                    correct = false;
                }
                else{
                    tvLoginEmailError.setText("");
                }
                if(txtPassword.getText().length() == 0){
                    tvLoginPasswordError.setText("Kein Passwort");
                    correct = false;
                }
                else {
                    tvLoginPasswordError.setText("");
                }
                System.out.println(txtEmail.getText() + "  " + txtPassword.getText());
                if(correct){

                    //TODO try to login User
                    DataReader dr = new DataReader();

                    M8 currm8 = dr.login(txtEmail.getText().toString(), txtPassword.getText().toString());

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                    intent.putExtra(EXTRA_M8, currm8);

                    //Falls er wegen Theme.AppCompat theme blablba weint
                    //android:theme="@style/Theme.AppCompat.Light"
                    //im Manifest bei der Activity einfügen
                    startActivity(intent);

                }

            }
        });

    }
}
