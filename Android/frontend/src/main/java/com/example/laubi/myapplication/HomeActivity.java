package com.example.laubi.myapplication;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.backend.Database;
import com.example.backend.Dto.*;
import com.example.backend.Interfaces.*;

import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends Activity{
    static final int UPDATE_DELETE_CLASS = 1;
    static final int VOTE = 2;
    static final int ADDM8 = 3;
    TextView tvCurrClass = null;
    private  ArrayList<M8> m8s;
    private Button btnStartVote;
    private Button btnDownloads;
    private static ListView lvMessages;
    private Button btnSendMessage;
    private EditText txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MainActivity.mainActivity.finish();
        final Button btnM8Settings = (Button) findViewById(R.id.btnM8Settings);
        btnStartVote = (Button) findViewById(R.id.btnStartVote);
        tvCurrClass = (TextView) findViewById(R.id.tvCurrClass);
        btnDownloads = (Button) findViewById(R.id.btnDownloads);
        final ListView lvClassM8s = (ListView) findViewById(R.id.lvClassM8s);
        lvMessages = (ListView) findViewById(R.id.lvMessages);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
        txtMessage = (EditText) findViewById(R.id.txtMessage);

        M8 m8 = Database.getInstance().getCurrentMate();

        System.out.println(m8);
        getCurrClass(m8);

        m8s = (ArrayList<M8>) Database.getInstance().getCurrentSchoolclass().getClassMembers();
        m8s.add(new M8(1, "Thoams", "Leiter","email", "a", false, 2, Database.getInstance().getCurrentSchoolclass()));
        m8s.add(new M8(1, "Thoams", "Leiter","email", "a", false, 2, Database.getInstance().getCurrentSchoolclass()));
        m8s.add(new M8(1, "Thoams", "Leiter","email", "a", false, 2, Database.getInstance().getCurrentSchoolclass()));


        final ArrayAdapter listViewArrayAdapter = new ArrayAdapter(this,
                R.layout.m8row, m8s);
        lvClassM8s.setAdapter(listViewArrayAdapter);


        //Todo getMessages
        ArrayList<Message> msgs = new ArrayList<Message>();
        //Sample
        msgs.add(new Message(Database.getInstance().getCurrentMate().getFirstname() + " " + Database.getInstance().getCurrentMate().getLastname(), "Hallo", new Date()));
        msgs.add(new Message("Lederjackenjhonny", "Huso sohn gtfout", new Date()));
        Chat.getInstance().addMultipleMessages(msgs);
        final ChatArrayAdapter chatAdapter = new ChatArrayAdapter(this, Chat.getInstance().getMessages());
        lvMessages.setAdapter(chatAdapter);



        new AsyncPolling(HomeActivity.this).execute(getApplicationContext());

        if(Database.getInstance().getCurrentMate().isHasVoted()){
            btnStartVote.setVisibility(View.GONE);
        }

        btnDownloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDownloads = new Intent(HomeActivity.this, FileShareActivity.class);

                startActivity(intentDownloads);
            }
        });

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

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Message m = new Message();
                String s = String.valueOf(txtMessage.getText());
                m.setSender(Database.getInstance().getCurrentMate().getFirstname() + " " + Database.getInstance().getCurrentMate().getLastname());
                m.setDatetime(new Date());
                m.setContent(s);

                chatAdapter.add(m);
                lvMessages.setSelection(chatAdapter.getCount() - 1);

                //Todo send message
            }

        });

        lvClassM8s.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentSettings = new Intent(HomeActivity.this, AddM8Activity.class);
                startActivityForResult(intentSettings, ADDM8);
                return true;
            }
        });
    }

    public void getCurrClass(com.example.backend.Dto.M8 m8){

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
        if(requestCode == ADDM8){
           m8s = (ArrayList<M8>) Database.getInstance().getCurrentSchoolclass().getClassMembers();
        }
    }
}
