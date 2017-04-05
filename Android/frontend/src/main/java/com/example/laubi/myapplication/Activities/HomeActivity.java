package com.example.laubi.myapplication.Activities;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backend.Database.Database;
import com.example.backend.Dto.Emote;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.Message;
import com.example.backend.Interfaces.DataReader;
import com.example.backend.Services.EmoteService;
import com.example.laubi.myapplication.Adapters.ChatArrayAdapter;
import com.example.laubi.myapplication.Adapters.ListAdapter;
import com.example.laubi.myapplication.Fragments.NavigationDrawerFragment;
import com.example.laubi.myapplication.Fragments.PlaceholderFragment;
import com.example.laubi.myapplication.Polling.OnItemChangedListener;
import com.example.laubi.myapplication.Polling.ReceiveMessageTask;
import com.example.laubi.myapplication.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Semaphore;

public class HomeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    static private Semaphore updating = new Semaphore(1);

    static final int UPDATE_DELETE_CLASS = 1;
    static final int ADDM8 = 3;
    private static ListView lvMessages;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private ListView lvClassM8s;
    private TextView tvCurrClass = null;
    private TextView tvYourM8s;
    private ArrayList<M8> m8s;
    private Button btnSendMessage;
    private EditText txtMessage;
    private Button btnShowEmojis;

    public static Semaphore getUpdating() {
        return updating;
    }

    public static void setUpdating(Semaphore updating) {
        HomeActivity.updating = updating;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_home);
        MainActivity.mainActivity.finish();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.assignViews();
        this.setTitle("Home");
        this.assignListeners();

        M8 m8 = Database.getInstance().getCurrentMate();
        getCurrClass(m8);

        try {
            m8s = Database.getInstance().getCurrentSchoolclass().getClassMembers();
        } catch (NullPointerException npe) {
            System.out.println("schoolclass is null");
            m8s = new ArrayList<>();
        }

        this.setUpArrayAdapter();
        this.setUpDrawer();

        Database.getInstance().getLocalMessagesOfCurrentUser(this.getApplicationContext());

        try {
            DataReader.getInstance().receiveMessage(this.getApplicationContext());
        }catch(Exception ex){
            System.out.println("error while receiving messages");
        }

        System.out.println("Total messages: " + Database.getInstance().getMessages().size());

        if (Database.getInstance().getCurrentSchoolclass() != null) {
            this.setUpPolling();
        }else{
            this.hideSchoolclassComponents();
        }
    }

    private void setUpPolling(){
        ReceiveMessageTask rmt = new ReceiveMessageTask(this.getApplicationContext(), HomeActivity.this);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(rmt, 0, 10000);
    }

    private void hideSchoolclassComponents(){
        txtMessage.setVisibility(View.INVISIBLE);
        btnSendMessage.setVisibility(View.INVISIBLE);
        tvYourM8s.setVisibility(View.INVISIBLE);
    }

    private void setUpArrayAdapter(){
        final ArrayAdapter listViewArrayAdapter = new ArrayAdapter(this,
                R.layout.m8row, m8s);
        lvClassM8s.setAdapter(listViewArrayAdapter);
    }

    public static ListView getLvMessages() {
        return lvMessages;
    }

    public static void setLvMessages(ListView lvMessages) {
        HomeActivity.lvMessages = lvMessages;
    }

    private void assignViews(){
        tvCurrClass = (TextView) findViewById(R.id.tvCurrClass);
        tvYourM8s = (TextView) findViewById(R.id.tvYourM8s);
        lvClassM8s = (ListView) findViewById(R.id.lvClassM8s);
        lvMessages = (ListView) findViewById(R.id.lvMessages);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessages);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        btnShowEmojis = (Button) findViewById(R.id.btnEmoji);
    }

    private void setUpDrawer(){
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setHomeActivity(this);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    private void assignListeners(){
        final ChatArrayAdapter chatAdapter = new ChatArrayAdapter(this, Database.getInstance().getLocalMessagesOfCurrentUser(HomeActivity.this));
        lvMessages.setAdapter(chatAdapter);
        btnShowEmojis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Prepare grid view
                GridView gridView = new GridView(HomeActivity.this);

                List<Integer> mList = new ArrayList<Integer>();
                for (int i = 1; i < 36; i++) {
                    mList.add(i);
                }

                final ListAdapter a = new ListAdapter(HomeActivity.this);

                for(Emote e : Database.getInstance().getEmojis()){
                    a.addEmote(e);
                }

                gridView.setAdapter(a);//, android.R.layout.simple_list_item_1, mList
                gridView.setNumColumns(5);

                // Set grid view to alertDialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setView(gridView);
                builder.setTitle("Choose an Emoji");
                final AlertDialog alert = builder.create();
                alert.show();
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println(a.getItem(position));
                        Emote e = (Emote)a.getItem(position);
                        txtMessage.getText().append("§" + e.getShortString() + "§");
                        alert.cancel();
                    }
                });
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Database.getInstance().getCurrentSchoolclass() != null) {


                    final Message m = new Message();

                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(System.currentTimeMillis());
                    cal.add(Calendar.HOUR, 2);
                    cal.add(Calendar.MINUTE, 55);
                    Timestamp t = new Timestamp(cal.getTime().getTime());

                    final String s = String.valueOf(txtMessage.getText());
                    m.setSender(Database.getInstance().getCurrentMate().getFirstname() + " " + Database.getInstance().getCurrentMate().getLastname());
                    m.setDateTime(t);
                    m.setContent(s);
                    if (!s.equals("")) {
                        HomeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DataReader.getInstance().sendMessage(s);
                                    Database.getInstance().addLocalMessage(new Message(s, Database.getInstance().getCurrentMate().getFirstname(), new Timestamp(System.currentTimeMillis())), HomeActivity.this);
                                    //chatAdapter.add(m);
                                    lvMessages.setSelection(chatAdapter.getCount() - 1);
                                    txtMessage.setText("");
                                    chatAdapter.notifyDataSetChanged();
                                } catch (Exception ex) {
                                    Toast.makeText(getApplicationContext(), "Error while sending", Toast.LENGTH_SHORT);
                                }
                            }
                        });
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

     //   Database.getInstance().getMessages().addOnListChangedCallback(new OnItemChangedListener(this,chatAdapter));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public void getCurrClass(com.example.backend.Dto.M8 m8){

        // DataReader dr = new DataReader();
        // Database.getInstance().setCurrentSchoolclass(dr.getSchoolclassByUser(m8));

        if(Database.getInstance().getCurrentSchoolclass() != null) {
            showClass();
        }
        else{
            createClass();
        }
    }

    public void showClass(){
        tvCurrClass.setText(Database.getInstance().getCurrentSchoolclass().getName());
        txtMessage.setVisibility(View.VISIBLE);
        btnSendMessage.setVisibility(View.VISIBLE);
        tvYourM8s.setVisibility(View.VISIBLE);
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
        if (requestCode == ADDM8) {
            this.updateMateList();
        }
    }

    public void updateMateList(){
        try {
            System.out.println("updateMateList");
            DataReader.getInstance().getSchoolclassByUser(Database.getInstance().getCurrentMate());
            m8s = Database.getInstance().getCurrentSchoolclass().getClassMembers();
        } catch (NullPointerException npe) {
            System.out.println("schoolclass is null");
            m8s = new ArrayList<>();
        }
        final ArrayAdapter listViewArrayAdapter = new ArrayAdapter(this,
                R.layout.m8row, m8s);
        lvClassM8s.setAdapter(null);
        lvClassM8s.setAdapter(listViewArrayAdapter);
    }


}
