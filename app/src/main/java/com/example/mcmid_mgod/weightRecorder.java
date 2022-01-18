package com.example.mcmid_mgod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class weightRecorder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_recorder);

        final Button randWeightButton = (Button) findViewById(R.id.buttonRandWeight);
        final Button backButton = (Button) findViewById(R.id.buttonReturnWeight);


        randWeightButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Random rand;
                rand = new Random();
                String weight = "Weight: "+(rand.nextInt(70) + 50);
                Intent sendWeight = new Intent(weightRecorder.this, MainActivity.class);
                sendWeight.putExtra("last",weight);
                startActivity(sendWeight);
                //do weight
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final EditText weightEditText = findViewById(R.id.editTextWeight);

                if (!TextUtils.isEmpty(weightEditText.getText().toString())) {
                    String weight = weightEditText.getText().toString();
                    Intent sendWeight = new Intent(weightRecorder.this, MainActivity.class);
                    sendWeight.putExtra("last",weight);
                    startActivity(sendWeight);
                }
                //do weight
            }
        });
    }
}