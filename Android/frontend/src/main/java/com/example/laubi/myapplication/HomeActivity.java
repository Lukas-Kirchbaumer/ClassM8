package com.example.laubi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.backend.Dto.*;
import com.example.backend.Interfaces.*;

import static com.example.laubi.myapplication.MainActivity.EXTRA_M8;

public class HomeActivity extends Activity {

    TextView tvCurrClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button btnM8Settings = (Button) findViewById(R.id.btnM8Settings);
        tvCurrClass = (TextView) findViewById(R.id.tvCurrClass);

        Intent intent = getIntent();
        final M8 m8 = (M8) intent.getSerializableExtra(EXTRA_M8);

        getCurrClass(m8);

        btnM8Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(HomeActivity.this, UserSettingsActivity.class);

                intentSettings.putExtra(EXTRA_M8, m8);

                startActivity(intentSettings);
            }
        });
    }

    public void getCurrClass(M8 m8){

        DataReader dr = new DataReader();
        Schoolclass currSchoolclass = dr.getSchoolclassByUser(m8);
        if(currSchoolclass == null) {
            currSchoolclass = new Schoolclass();
            currSchoolclass.setName("TestClass");
        }
        tvCurrClass.setText(currSchoolclass.getName());
    }
}
