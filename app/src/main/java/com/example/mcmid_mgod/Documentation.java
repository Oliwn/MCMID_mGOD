package com.example.mcmid_mgod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Documentation extends AppCompatActivity {
    final int MY_REQUEST_CODE = 1;
    final int MY_REQUEST_CODE2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textViewName = findViewById(R.id.text_patname);
        textViewName.setText(Controller.currentPatient.firstname+" "+Controller.currentPatient.lastname);
        Intent intent =getIntent();

        setContentView(R.layout.activity_documentation);
        TextView patientname = findViewById(R.id.text_patname);
        Button newdoc = findViewById(R.id.btn_newdoc);
        Button viewdoc = findViewById(R.id.btn_viewdoc);

        newdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivityForResult(intent,MY_REQUEST_CODE);

            }
        });

        viewdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewDocument.class);
                startActivityForResult(intent,MY_REQUEST_CODE2);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}