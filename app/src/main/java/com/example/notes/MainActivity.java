package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText user_name;
    EditText password;
    //public static final String SHARED_PREFS = "CS407Notes";
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "passwords";

    public void clickFunction(View view) {
        user_name = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        String userName = user_name.getText().toString();
        String pass = password.getText().toString();

        SharedPreferences sharedPref = getSharedPreferences("notes", Context.MODE_PRIVATE);

        sharedPref.edit().putString("username", userName).apply();
        //sharedPref.edit().putString("password", pass).apply();
        goToHome(sharedPref.getString(USER_NAME, ""));
    }

    public void goToHome(String str) {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("message", str);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getSharedPreferences("notes", Context.MODE_PRIVATE);

        if (!sharedPref.getString(USER_NAME, "").equals("")) {
            String username = sharedPref.getString("username", "");
            Intent intent = new Intent(this, Home.class);
            intent.putExtra("message", username);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
        }
    }
}