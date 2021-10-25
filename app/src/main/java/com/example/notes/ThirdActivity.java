package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirdActivity extends AppCompatActivity {

    int noteid = -1;
    EditText write;

    public void saveMethod(View view) {

        Log.i("message", "Button clicked");
        //1.
        write = (EditText) findViewById(R.id.write);
        String content = write.getText().toString();

        //2.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",
                Context.MODE_PRIVATE, null);

        //3.
        DBHelper helper = new DBHelper(sqLiteDatabase);

        //4.
        SharedPreferences sharedPref = getSharedPreferences("notes", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");

        //5.
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            Log.i("message", "INSIDE NOTEID");
            title = "NOTE_" + (Home.notes.size() + 1);
            helper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            helper.updateNote(title, date, content, username);
        }

        //6.
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //1.
        write = (EditText) findViewById(R.id.write);

        //2.
        Intent intent = getIntent();

        //3. and 4.
        int position = intent.getIntExtra("noteid", -1); //TODO correct use of getIntExtra?
        noteid = position;

        if (noteid != -1) {

            Note note = Home.notes.get(noteid);
            String noteContent = note.getContent();
            write.setText(noteContent);

        }
    }
}