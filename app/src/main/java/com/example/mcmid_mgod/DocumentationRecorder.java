package com.example.mcmid_mgod;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class DocumentationRecorder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_recorder);

        final Button randBPButton = (Button) findViewById(R.id.buttonRandBP);
        final Button backButton = (Button) findViewById(R.id.buttonReturnBP);


        randBPButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Random rand;
                rand = new Random();
                String sys = ""+(rand.nextInt(150) + 90);
                String dia = ""+(rand.nextInt(100) + 50);
                Intent sendBP = new Intent(DocumentationRecorder.this, MainActivity.class);
                sendBP.putExtra("last","BP: "+sys+"/"+dia);
                startActivity(sendBP);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final EditText sysEditText = findViewById(R.id.sysEditText);
                final EditText diaEditText = findViewById(R.id.diaEditText);

                if (!TextUtils.isEmpty(sysEditText.getText().toString()) && !TextUtils.isEmpty(diaEditText.getText().toString())) {
                    String sys = sysEditText.getText().toString();
                    String dia = diaEditText.getText().toString();
                    Intent sendBP = new Intent(DocumentationRecorder.this, MainActivity.class);
                    sendBP.putExtra("last","BP: "+sys+"/"+dia);
                    startActivity(sendBP);
                }
                //do weight
            }
        });
    }
}