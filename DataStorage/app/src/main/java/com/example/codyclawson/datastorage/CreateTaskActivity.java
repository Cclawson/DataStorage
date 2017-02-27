package com.example.codyclawson.datastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class CreateTaskActivity extends AppCompatActivity {

    Spinner spinner;
    EditText name;
    EditText color;
    DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        db = new DbHandler(this);

        addSpinnerOptions();
    }

    public void addSpinnerOptions() {

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> list = new ArrayList<String>();
        list.add("1" );
        list.add("2" );
        list.add("3" );
        list.add("4" );
        list.add("5" );

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void createTask(View view) {
        name = (EditText) findViewById(R.id.editName);
        color = (EditText) findViewById(R.id.editColor);
        spinner = (Spinner) findViewById(R.id.spinner);

        Task newTask = new Task(name.getText().toString(), color.getText().toString(), 0, 0, Integer.parseInt(spinner.getSelectedItem().toString()), 0 );


        db.addTask(newTask);

        db.close();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        db.close();
        finish();
    }

}
