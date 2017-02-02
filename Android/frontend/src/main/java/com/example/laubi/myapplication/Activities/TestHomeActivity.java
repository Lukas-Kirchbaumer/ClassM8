package com.example.laubi.myapplication.Activities;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backend.Database.Database;
import com.example.backend.Dto.M8;
import com.example.backend.Dto.MappedChat;
import com.example.backend.Dto.Message;
import com.example.backend.Interfaces.DataReader;
import com.example.laubi.myapplication.Adapters.ChatArrayAdapter;
import com.example.laubi.myapplication.Fragments.NavigationDrawerFragment;
import com.example.laubi.myapplication.Polling.OnItemChangedListener;
import com.example.laubi.myapplication.Polling.ReceiveMessageTask;
import com.example.laubi.myapplication.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class TestHomeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    static final int UPDATE_DELETE_CLASS = 1;
    static final int ADDM8 = 3;
    private static ListView lvMessages;
    private ListView lvClassM8s;
    private TextView tvCurrClass = null;
    private TextView tvYourM8s;
    private ArrayList<M8> m8s;
    private Button btnSendMessage;
    private EditText txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_home);
        MainActivity.mainActivity.finish();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();


        mNavigationDrawerFragment.setTestHomeActivity(this);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));



        final Button btnM8Settings = (Button) findViewById(R.id.btnM8Settings);
        tvCurrClass = (TextView) findViewById(R.id.tvCurrClass);
        tvYourM8s = (TextView) findViewById(R.id.tvYourM8s);
        lvClassM8s = (ListView) findViewById(R.id.lvClassM8s);
        lvMessages = (ListView) findViewById(R.id.lvMessages);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessages);
        txtMessage = (EditText) findViewById(R.id.txtMessage);

        M8 m8 = Database.getInstance().getCurrentMate();

        System.out.println(m8);
        getCurrClass(m8);
        try {
            m8s = (ArrayList<M8>) Database.getInstance().getCurrentSchoolclass().getClassMembers();
        } catch (NullPointerException npe) {
            System.out.println("schoolclass is null");
            m8s = new ArrayList<>();
        }
        final ArrayAdapter listViewArrayAdapter = new ArrayAdapter(this,
                R.layout.m8row, m8s);
        lvClassM8s.setAdapter(listViewArrayAdapter);

        ArrayList<Message> msgs = new ArrayList<Message>();
        try {
            msgs = DataReader.getInstance().receiveMessage();
        }catch(Exception ex){
            System.out.println("error while receiving messages");
        }

        System.out.println("Total messages: " + msgs.size());
        MappedChat.getInstance().addMultipleMessages(msgs);

        final ChatArrayAdapter chatAdapter = new ChatArrayAdapter(this, MappedChat.getInstance().getMessages());

        lvMessages.setAdapter(chatAdapter);

        MappedChat.getInstance().getMessages().addOnListChangedCallback(new OnItemChangedListener(new WeakReference<Activity>(this)));

        if (Database.getInstance().getCurrentSchoolclass() != null) {
            ReceiveMessageTask rmt = new ReceiveMessageTask();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(rmt, 0, 5000);
        }else{
            txtMessage.setVisibility(View.INVISIBLE);
            btnSendMessage.setVisibility(View.INVISIBLE);
            tvYourM8s.setVisibility(View.INVISIBLE);
        }

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
                Intent intentSettings = new Intent(TestHomeActivity.this, AddM8Activity.class);
                startActivityForResult(intentSettings, ADDM8);
                return true;
            }
        });

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
        txtMessage.setVisibility(View.VISIBLE);
        btnSendMessage.setVisibility(View.VISIBLE);
        tvYourM8s.setVisibility(View.VISIBLE);
        tvCurrClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClassSettings = new Intent(TestHomeActivity.this, ClassSettingsActivity.class);
                startActivityForResult(intentClassSettings, UPDATE_DELETE_CLASS);
            }
        });
    }

    public void createClass(){
        tvCurrClass.setText("Neue Klasse...");
        tvCurrClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClassSettings = new Intent(TestHomeActivity.this, NewClassActivity.class);
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
    }

    public void updateMateList(){
        try {
            DataReader.getInstance().getSchoolclassByUser(Database.getInstance().getCurrentMate());
            m8s = (ArrayList<M8>) Database.getInstance().getCurrentSchoolclass().getClassMembers();
        } catch (NullPointerException npe) {
            System.out.println("schoolclass is null");
            m8s = new ArrayList<>();
        }
        final ArrayAdapter listViewArrayAdapter = new ArrayAdapter(this,
                R.layout.m8row, m8s);
        lvClassM8s.setAdapter(null);
        lvClassM8s.setAdapter(listViewArrayAdapter);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_test_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((TestHomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
