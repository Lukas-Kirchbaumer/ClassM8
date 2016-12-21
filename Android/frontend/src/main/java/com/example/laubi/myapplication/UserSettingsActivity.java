package com.example.laubi.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.backend.DTO.M8;

import static com.example.laubi.myapplication.MainActivity.EXTRA_M8;

public class UserSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        final Button btnUserSettingsOk = (Button) findViewById(R.id.btnSettingsOk);
        final EditText txtSettingsFirstname = (EditText) findViewById(R.id.txtSettingsFirstname);
        final EditText txtSettingsLastname = (EditText) findViewById(R.id.txtSettingsLastname);
        final EditText txtSettingsEmail = (EditText) findViewById(R.id.txtSettingsEmail);
        final EditText txtSettingsPw = (EditText) findViewById(R.id.txtSettingsPassword);

        Intent intent = getIntent();
        M8 m8 = (M8) intent.getSerializableExtra(EXTRA_M8);
        txtSettingsFirstname.setText(m8.getFirstname());
        txtSettingsLastname.setText(m8.getLastname());
        txtSettingsEmail.setText(m8.getEmail());
        txtSettingsPw.setText(m8.getPassword());

        btnUserSettingsOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                M8 m8 = new M8();
                m8.setFirstname(txtSettingsFirstname.getText().toString());
                m8.setLastname(txtSettingsLastname.getText().toString());
                m8.setEmail(txtSettingsEmail.getText().toString());
                m8.setPassword(txtSettingsPw.getText().toString());

                System.out.println("Update " + m8.toString());
                finish();
            }
        });
    }
}
