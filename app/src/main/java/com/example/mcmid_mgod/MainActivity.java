package com.example.mcmid_mgod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String currentUser = "";
    public static Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button weightButton = (Button) findViewById(R.id.button_showPList);
        final Button bloodPressureButton = (Button) findViewById(R.id.button2);

        if (currentUser.equals("")) {
            weightButton.setClickable(false);
            bloodPressureButton.setClickable(false);
        } else {
            weightButton.setClickable(true);
            bloodPressureButton.setClickable(true);
        }

        TextView lastRecording = (TextView) findViewById(R.id.textViewLastRecording);
        lastRecording.setText(getIntent().getStringExtra("last"));

        //onclick for weight
        /*weightButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startWeight = new Intent(MainActivity.this, weightRecorder.class);
                startActivity(startWeight);
            }
        });*/

        //onclick for BP
        bloodPressureButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (currentUser.equals("")){
                    Toast toastNoUser = Toast.makeText(getApplicationContext(), "Please log in", Toast.LENGTH_SHORT);
                    toastNoUser.show();
                }
                else{
                    Intent startBP = new Intent(MainActivity.this, AddActivity.class);
                    startActivity(startBP);
                    //do weight
                }
            }
        });
        //if an intent started this activity display the extra "last"
        if(getIntent().hasExtra("last")) {
            Intent intent = getIntent();
            String message = intent.getStringExtra("last");

            // TextView textView = findViewById(R.id.textViewLastRecording);
            // textView.setText(message);
        }
        Button mButtonShowPList =  (Button) findViewById(R.id.button_showPList);
        mButtonShowPList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser.equals("")){
                    Toast toastNoUser = Toast.makeText(getApplicationContext(), "Please log in", Toast.LENGTH_SHORT);
                    toastNoUser.show();
                }
                else{
                    Intent i = new Intent(getApplicationContext(), PatientList.class);
                    startActivity(i);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);

        if (currentUser.equals("")) {
            menu.getItem(1).setVisible(false);
            menu.getItem(0).setVisible(true);
        } else {
            menu.getItem(1).setVisible(true);
            menu.getItem(0).setVisible(false);
        }

        return true;
    }

    public  boolean onClickLogout(MenuItem item){
        currentUser = "";
        item.setVisible(false);
        menu.getItem(0).setVisible(true);
        Toast toastLogout = Toast.makeText(getApplicationContext(), "You're logged out", Toast.LENGTH_SHORT);
        toastLogout.show();

        return true;
    }
    public boolean onClickLogin(MenuItem item){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        item.setVisible(false);
        menu.getItem(0).setVisible(true);
        return true;
    }

    public boolean onClickHome(MenuItem item){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        return true;
    }





}