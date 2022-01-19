package com.example.mcmid_mgod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean currentUser = false;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button weightButton = (Button) findViewById(R.id.button);
        final Button bloodPressureButton = (Button) findViewById(R.id.button2);

        TextView lastRecording = (TextView) findViewById(R.id.textViewLastRecording);
        lastRecording.setText(getIntent().getStringExtra("last"));

        //onclick for weight
        weightButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startWeight = new Intent(MainActivity.this, weightRecorder.class);
                startActivity(startWeight);
            }
        });

        //onclick for BP
        bloodPressureButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startBP = new Intent(MainActivity.this, DocumentationRecorder.class);
                startActivity(startBP);
                //do weight
            }
        });
        //if an intent started this activity display the extra "last"
        if(getIntent().hasExtra("last")) {
            Intent intent = getIntent();
            String message = intent.getStringExtra("last");

            // TextView textView = findViewById(R.id.textViewLastRecording);
            // textView.setText(message);
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        //menu.getItem(1).setVisible(false);

        return true;
    }

    public  boolean onClickLogout(MenuItem item){

        item.setVisible(false);
        //menu.getItem(1).setVisible(true);

        return true;
    }
    public boolean onClickLogin(MenuItem item){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        item.setVisible(false);
        menu.getItem(0).setVisible(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){ // LOGIN
            case R.id.LoginItem:
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                currentUser = true;

                item.setVisible(false);


                return true;
            case R.id.LogoutItem:
                Toast toastLogout = Toast.makeText(getApplicationContext(), "Youre logged out", Toast.LENGTH_SHORT);
                toastLogout.show();
                item.setVisible(false);
                // do log out user
                return true;


        }
        return true;

    }



}