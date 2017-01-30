package com.example.laubi.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.backend.Database.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Interfaces.DataReader;

public class UserSettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        final Button btnUserSettingsOk = (Button) findViewById(R.id.btnSettingsOk);
        final EditText txtSettingsFirstname = (EditText) findViewById(R.id.txtSettingsFirstname);
        final EditText txtSettingsLastname = (EditText) findViewById(R.id.txtSettingsLastname);
        final EditText txtSettingsEmail = (EditText) findViewById(R.id.txtSettingsEmail);
        final EditText txtSettingsPw = (EditText) findViewById(R.id.txtSettingsPassword);
        final TextView tvUserSettingsError = (TextView) findViewById(R.id.tvUserSettingsError);

        M8 m8 = Database.getInstance().getCurrentMate();
        txtSettingsFirstname.setText(m8.getFirstname());
        txtSettingsLastname.setText(m8.getLastname());
        txtSettingsEmail.setText(m8.getEmail());
        txtSettingsPw.setText(m8.getPassword());

        btnUserSettingsOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = (txtSettingsFirstname.getText().toString());
                String ln = (txtSettingsLastname.getText().toString());
                String email = (txtSettingsEmail.getText().toString());
                String pw = (txtSettingsPw.getText().toString());
                M8 m8 = Database.getInstance().getCurrentMate();

                if(fn != "" && ln != "" && email != "" && pw != ""){
                    m8.setId(m8.getId());
                    m8.setFirstname(fn);
                    m8.setLastname(ln);
                    m8.setEmail(email);
                    m8.setPassword(pw);
                    DataReader.getInstance().updateUser(m8);
                }else{
                    tvUserSettingsError.setText("Alle Felder ausfüllen");
                }
                Database.getInstance().setCurrentMate(m8);
            }
        });
    }
}
