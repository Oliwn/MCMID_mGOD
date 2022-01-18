package com.example.mcmid_mgod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
}