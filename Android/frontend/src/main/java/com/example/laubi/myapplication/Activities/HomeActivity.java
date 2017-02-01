package com.example.laubi.myapplication.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backend.Database.Database;
import com.example.backend.Dto.*;
import com.example.backend.Interfaces.*;
import com.example.laubi.myapplication.Adapters.ChatArrayAdapter;
import com.example.laubi.myapplication.Polling.AlarmListener;
import com.example.laubi.myapplication.Polling.OnItemChangedListener;
import com.example.laubi.myapplication.Polling.ReceiveMessageTask;
import com.example.laubi.myapplication.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class HomeActivity extends Activity{
    static final int UPDATE_DELETE_CLASS = 1;
    static final int VOTE = 2;
    static final int ADDM8 = 3;
    private static ListView lvMessages;
    TextView tvCurrClass = null;
    private  ArrayList<M8> m8s;
    private Button btnStartVote;
    private Button btnDownloads;
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
        try {
            m8s = (ArrayList<M8>) Database.getInstance().getCurrentSchoolclass().getClassMembers();
        } catch (NullPointerException npe) {
            System.out.println("schoolclass is null");
            m8s = new ArrayList<M8>();
        }
        final ArrayAdapter listViewArrayAdapter = new ArrayAdapter(this,
                R.layout.m8row, m8s);
        lvClassM8s.setAdapter(listViewArrayAdapter);

        ArrayList<Message> msgs = new ArrayList<Message>();
        try {
            msgs = DataReader.getInstance().receiveMessage();
        }catch(Exception ex){
            System.out.println("---------err");
        }

        System.out.println("Total messages: " + msgs.size());
        MappedChat.getInstance().addMultipleMessages(msgs);

        final ChatArrayAdapter chatAdapter = new ChatArrayAdapter(this, MappedChat.getInstance().getMessages());

        lvMessages.setAdapter(chatAdapter);

        MappedChat.getInstance().getMessages().addOnListChangedCallback(new OnItemChangedListener(new WeakReference<Activity>(this)));

        if(Database.getInstance().getCurrentMate().isHasVoted()){
            btnStartVote.setVisibility(View.GONE);
        }

        if (Database.getInstance().getCurrentSchoolclass() != null) {
            ReceiveMessageTask rmt = new ReceiveMessageTask();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(rmt, 0, 5000);
        }

        btnDownloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Database.getInstance().getCurrentSchoolclass() != null) {
                    Intent intentDownloads = new Intent(HomeActivity.this, FileShareActivity.class);
                    startActivity(intentDownloads);
                } else {
                    System.out.println("No Schoolclass, so no files");
                }
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
                if (Database.getInstance().getCurrentSchoolclass() != null) {
                    Intent intentSettings = new Intent(HomeActivity.this, VoteActivity.class);
                    startActivityForResult(intentSettings, VOTE);
                } else {
                    System.out.println("No Schoolclass, so no files");
                }
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Database.getInstance().getCurrentSchoolclass() != null) {
                    Message m = new Message();
                    String s = String.valueOf(txtMessage.getText());
                    m.setSender(Database.getInstance().getCurrentMate().getFirstname() + " " + Database.getInstance().getCurrentMate().getLastname());
                    m.setDateTime(new Date());
                    m.setContent(s);

                    try {
                        DataReader.getInstance().sendMessage(s);
                        chatAdapter.add(m);
                        lvMessages.setSelection(chatAdapter.getCount() - 1);
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Error while sending", Toast.LENGTH_SHORT);
                    }
                }

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
