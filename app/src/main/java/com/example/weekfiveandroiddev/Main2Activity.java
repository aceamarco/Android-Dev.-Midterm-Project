package com.example.weekfiveandroiddev;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int mode = getIntent().getIntExtra("mode", 0);
        EditText note_title = findViewById(R.id.note_title);
        final int count_tmp = getIntent().getIntExtra("count", 0);

        //Mode 2 makes a new note, Mode 3 opens an old one
        if (mode == 2){
            note_title.setText("Note" + count_tmp);
        } else if (mode == 3){
            String title = getIntent().getStringExtra("title");
            String note = getIntent().getStringExtra("note");
            note_title.setText(title);
            EditText note_body = findViewById(R.id.note_body);
            note_body.setText(note);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitText(mode, getIntent());
            }
        });
    }

    private void submitText(final int mode, Intent i) {
        if (mode == 2) {
            //Get the two editText objects
            EditText note_title = findViewById(R.id.note_title);
            EditText note_body = findViewById((R.id.note_body));
            // Prepare data intent
            Intent data = new Intent();

            // Gather the text from the two editText
            String note_title_string = note_title.getText().toString();
            String note_body_string = note_body.getText().toString();

            //Put the strings into the data-intent object
            data.putExtra("title", note_title_string);
            data.putExtra("body", note_body_string);
            data.putExtra("isNew", true);

            // Activity finished ok, return the data
            setResult(RESULT_OK, data); // set result code and bundle data for response
            finish(); // closes the activity, pass data to parent
        }
        else if (mode == 3){
            //Get the two editText objects
            EditText note_title = findViewById(R.id.note_title);
            EditText note_body = findViewById((R.id.note_body));
            // Prepare data intent
            Intent data = new Intent();

            // Gather the text from the two editText
            String note_title_string = note_title.getText().toString();
            String note_body_string = note_body.getText().toString();

            //Put the strings into the data-intent object
            data.putExtra("title", note_title_string);
            data.putExtra("body", note_body_string);
            data.putExtra("isNew", false);
            data.putExtra("index", i.getExtras().getInt("index"));

            // Activity finished ok, return the data
            setResult(RESULT_OK, data); // set result code and bundle data for response
            finish(); // closes the activity, pass data to parent
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
