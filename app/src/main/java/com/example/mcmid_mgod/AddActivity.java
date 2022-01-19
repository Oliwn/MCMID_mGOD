package com.example.mcmid_mgod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
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
                // Submit Input Data
                EditText mEditText_BP = findViewById(R.id.editText_BP);
                if (mEditText_BP.getText() != null){
                    String BP = mEditText_BP.getText().toString();
                }
                mEditText_BP.setInputType(InputType.TYPE_NULL);

                EditText mEditText_weight = findViewById(R.id.editText_weight);
                if (mEditText_weight.getText() != null){
                    //int weight = Integer.parseInt(mEditText_BP.getText().toString()); !!!!!!!!!!!CRASHED !!!!!!!!!!!!!!
                }

                mEditText_weight.setInputType(InputType.TYPE_NULL);

                EditText mEditText_additional = findViewById(R.id.editText_additional);
                if (mEditText_additional.getText() != null){
                    String additional = mEditText_additional.getText().toString();
                }
                mEditText_additional.setInputType(InputType.TYPE_NULL);
                menu.getItem(0).setVisible(true);
                Toast toastLogout = Toast.makeText(getApplicationContext(), "Successfully submitted", Toast.LENGTH_SHORT);
                mButtonSubmit.setEnabled(false);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MainActivity.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);

        menu.getItem(1).setVisible(false);
        menu.getItem(0).setVisible(false);

        return true;
    }
}