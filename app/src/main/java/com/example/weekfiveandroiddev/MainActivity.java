package com.example.weekfiveandroiddev;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    private int count = 0;
    private ArrayList<String> titles = new ArrayList<String>();
    private ArrayList<String> notes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets the content to view to activity_main.xml
        setContentView(R.layout.activity_main);

        //Toolbar is the green tab at the top of the screen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create the listView object
        ListView note_list = (ListView)findViewById(R.id.note_list);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), titles, notes);
        note_list.setAdapter(customAdapter);

        note_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("mode", 3); // pass arbitrary data to launched activity
                i.putExtra("title", titles.get(pos));
                i.putExtra("note", notes.get(pos));
                i.putExtra("index", pos);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        //create_note button is the button at the bottom right of the screen
        FloatingActionButton create_note_button = findViewById(R.id.create_note);

        //Adds an Click Listener to the create_note_button
        create_note_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("mode", 2); // pass arbitrary data to launched activity
                i.putExtra("count", count); //pass in the number of notes
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.getBooleanExtra("isNew", false)) {
                // Extract name value from result extras
                String title = data.getExtras().getString("title");
                String note = data.getExtras().getString("body");
                titles.add(title);
                notes.add(note);

                //Create the listView object
                ListView note_list = (ListView) findViewById(R.id.note_list);
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), titles, notes);
                note_list.setAdapter(customAdapter);

                count++;

                // Let the user know their note has been created
                Toast.makeText(this, title + " created.", Toast.LENGTH_SHORT).show();
            } else {
                // Extract name value from result extras
                String title = data.getExtras().getString("title");
                String note = data.getExtras().getString("body");
                int i = data.getExtras().getInt("index");

                //Edit the values in titles and notes at that index
                titles.set(i, title);
                notes.set(i, note);

                //Create the listView object
                ListView note_list = (ListView) findViewById(R.id.note_list);
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), titles, notes);
                note_list.setAdapter(customAdapter);

                // Let the user know their note has been edited
                Toast.makeText(this, title + " has been updated.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
