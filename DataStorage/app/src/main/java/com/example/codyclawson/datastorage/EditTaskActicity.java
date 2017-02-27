package com.example.codyclawson.datastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class EditTaskActicity extends AppCompatActivity {

    Task task;

    EditText name;
    EditText color;
    Spinner priority;

    DbHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_acticity);

        Intent intent = getIntent();
        task = (Task)intent.getSerializableExtra("task");

        db = new DbHandler(this);


        name = (EditText) findViewById(R.id.editName);
        color = (EditText) findViewById(R.id.editColor);

        priority = (Spinner) findViewById(R.id.spinner);

        ArrayList<String> list = new ArrayList<String>();
        list.add("1" );
        list.add("2" );
        list.add("3" );
        list.add("4" );
        list.add("5" );

        name.setText(task.getName());
        color.setText(task.getColor());


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priority.setAdapter(dataAdapter);

        for (int i = 0; i < priority.getCount(); i++) {
            System.out.println(priority.getItemAtPosition(i));
            if (priority.getItemAtPosition(i).equals(Long.toString(task.getPriority()))) {
                priority.setSelection(i);
                break;
            }
        }

    }

    public void updateTask(View view){

        task.setName(name.getText().toString());
        task.setColor(color.getText().toString());
        task.setPriority(Integer.parseInt(priority.getSelectedItem().toString()));

        db.updateTask(task);

        db.close();
        finish();

        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra("task",task);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {

        db.close();
        finish();

        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra("task",task);
        startActivity(intent);
    }

}
