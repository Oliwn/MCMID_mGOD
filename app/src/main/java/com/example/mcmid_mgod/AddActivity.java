package com.example.mcmid_mgod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonSerializer;

public class AddActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Button mButtonSubmit = (Button)findViewById(R.id.button_submit);
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                Intent intent =getIntent();

                // Submit Input Data
                EditText mEditText_BP = findViewById(R.id.editText_BP);

                String BP = mEditText_BP.getText().toString();
                Log.d("ADD", BP);
                mEditText_BP.setInputType(InputType.TYPE_NULL);

                if (BP.length() > 0) {
                    Controller.addBloodPressure(BP);
                }

                EditText mEditText_weight = findViewById(R.id.editText_weight);

                String weight = mEditText_weight.getText().toString();
                Log.d("ADD", weight);
                mEditText_weight.setInputType(InputType.TYPE_NULL);

                if (weight.length() > 0) {
                    Controller.addWeight(weight);
                }

                EditText mEditText_additional = findViewById(R.id.editText_additional);

                String additional = mEditText_additional.getText().toString();
                Log.d("ADD", additional);
                mEditText_additional.setInputType(InputType.TYPE_NULL);

                if (additional.length() > 0) {
                    Controller.addAdditionalInformation(additional);
                }
            }
        });
        ImageButton buttonBluetooth = findViewById(R.id.imageButton_bluetooth);
        buttonBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // BLUETOOTH
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MainActivity.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);

        menu.getItem(1).setVisible(false);
        menu.getItem(0).setVisible(false);

        return true;
    }
}