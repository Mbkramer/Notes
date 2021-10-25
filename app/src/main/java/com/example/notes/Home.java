package com.example.notes;

import static com.example.notes.R.menu.menu_items;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    TextView welcome;
    public static ArrayList <Note> notes = new ArrayList<>();

    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        SharedPreferences sharedPref = getSharedPreferences("notes", Context.MODE_PRIVATE);
        sharedPref.edit().remove("username").apply();
        startActivity(intent);
    }

    public void AddNote() {
        Intent intent = new Intent(this, ThirdActivity.class);
        //SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPref = getSharedPreferences("notes", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");

        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Welcome " + username);

        //TODO (2.) ADDED THIS TO GET SQLITEDATABASE INSTANCE
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",
                Context.MODE_PRIVATE, null);

        //TODO (3. ) GET NOTES FROM NOTELIST IN HELPER? "WORKED" before i added this,
        //TODO       what might that tell me?
        DBHelper helper = new DBHelper(sqLiteDatabase);
        notes = helper.readNotes(username);



        ArrayList <String> displayNotes = new ArrayList<>();
        Log.i("message", "MADE IT TO LOOP");
        for (Note note: notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));

            Log.i("message", note.getTitle().toString());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                //TODO in line below I wrote Note.class, not NoteActivity.class?
                Intent intent = new Intent(getApplicationContext(), Note.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Logout:
                goToMain();
                return true;
            case R.id.Note:
                AddNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}