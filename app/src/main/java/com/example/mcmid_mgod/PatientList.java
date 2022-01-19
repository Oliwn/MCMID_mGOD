package com.example.mcmid_mgod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PatientList extends AppCompatActivity {
    final int MY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        ListView listView = findViewById(R.id.list_patientlist);

        ArrayList<PatientLocal> patients = Controller.getAllPatients();

        ArrayList<String> arrayList = new ArrayList<>();
        for (PatientLocal p : patients) {
            arrayList.add(p.toString());
        }

        ArrayAdapter arrayadpater = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayadpater);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Controller.currentPatient = patients.get(i);
                Toast.makeText(PatientList.this,"clicked item"+i+""+arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),PatientView.class);
                intent.putExtra("Listindex", i);
                startActivityForResult(intent,MY_REQUEST_CODE);
            }
        });
    }
}