package com.example.mcmid_mgod;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PatientView extends AppCompatActivity {
    final int MY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvName = findViewById(R.id.textView_patientName);
        TextView tvDob = findViewById(R.id.textView_birthDate);
        TextView tvSvnr = findViewById(R.id.textView_svnr);

        tvName.setText(Controller.currentPatient.firstname + " " + Controller.currentPatient.lastname);
        tvDob.setText(Controller.currentPatient.dob.toString());
        tvSvnr.setText(Controller.currentPatient.insuranceNr);


        Intent intent =getIntent();
        Toast.makeText(this, "intent:"+ intent,Toast.LENGTH_SHORT).show();
        /*Button button = findViewById(R.id.button2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivityForResult(intent,MY_REQUEST_CODE);
            }
        });*/
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

    //Get a readable Patient Object
    //read the data gotten from the model into the textviews
    //display everything
}