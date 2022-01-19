package com.example.mcmid_mgod;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class LoginActivity extends MainActivity {
    EditText mEditTextUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button mButtonLogin = findViewById(R.id.button_Login);

        mButtonLogin.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View view){
                mEditTextUsername = findViewById(R.id.editText_username);
                EditText editTextPassword = findViewById(R.id.editText_password);

                if (Controller.login(mEditTextUsername.getText().toString(), editTextPassword.getText().toString())) {
                    MainActivity.currentUser = mEditTextUsername.getText().toString();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    Toast toastNoUser = Toast.makeText(getApplicationContext(), "You are logged in", Toast.LENGTH_SHORT);
                    toastNoUser.show();
                }

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