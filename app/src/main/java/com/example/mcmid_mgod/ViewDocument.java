package com.example.mcmid_mgod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewDocument extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_document);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        //Toast.makeText(this, "intent:" + intent, Toast.LENGTH_SHORT).show();

        TextView patientname = findViewById(R.id.text_patientname);


        if (Controller.currentPatient != null) {
            patientname.setText(Controller.currentPatient.firstname + " " + Controller.currentPatient.lastname);

            ListView listView = findViewById(R.id.listView_Recordings);
            ArrayList<String> observations = Controller.getAllObservationsAsString();

            ArrayAdapter arrayadpater = new ArrayAdapter(this, android.R.layout.simple_list_item_1, observations);
            listView.setAdapter(arrayadpater);

        } else {
            Toast.makeText(this, "There was an error while loading the patient data", Toast.LENGTH_SHORT).show();
        }
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