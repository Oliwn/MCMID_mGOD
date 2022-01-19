package com.example.mcmid_mgod;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends MainActivity {
    EditText mEditTextUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button mButtonLogin = findViewById(R.id.button_Login);

        mButtonLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){



                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                mEditTextUsername = findViewById(R.id.editText_username);
                MainActivity.currentUser = mEditTextUsername.getText().toString();



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