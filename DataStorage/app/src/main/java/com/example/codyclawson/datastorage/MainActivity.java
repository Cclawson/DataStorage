package com.example.codyclawson.datastorage;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    MainActivity activity;
    DbHandler db;
    Spinner prioritySpin;
    String priorityFilter = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        db = new DbHandler(this);

        prioritySpin = (Spinner) findViewById(R.id.toolSpinner);
        ArrayList<String> list = new ArrayList<String>();
        list.add("All" );
        list.add("1" );
        list.add("2" );
        list.add("3" );
        list.add("4" );
        list.add("5" );

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpin.setAdapter(dataAdapter);
        prioritySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                priorityFilter = parentView.getSelectedItem().toString();
                UpdateList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        UpdateList();
        db.close();

    }

    @Override
    protected void onResume() {
        super.onResume();

        final DbHandler db = new DbHandler(this);
        UpdateList();
        db.close();

    }


    private void UpdateList()
    {

        LinearLayout ll = (LinearLayout) findViewById(R.id.tasklist);
        ll.removeAllViews();

        // Reading all tasks
        Log.d("Reading: ", "Reading all tasks after adding.." );
        List<Task> tasks = db.getAllTasks();

        List<Task> filtered = new ArrayList<Task>();
        for(Task task : tasks)
        {
            if(Long.toString(task.getPriority()).equals(priorityFilter) || priorityFilter.equals("All"))
                filtered.add(task);
        }


        for (final Task task : filtered) {
            String log = "Id: " + task.getId() + " ,Parent ID: " + task.getParentId() + " ,Name: " + task.getName() + " ,Color: " + task.getColor() + " ,Time: " + task.getTime() + " ,Category: " + task.getCategoryId() + " ,Priority: " + task.getPriority() + " ,Completed: " + task.getCompleted();

            final TextView tv1 = new TextView(this);
            tv1.setText(task.getName() + ": Time(S):" + (task.getTime()/1000));
            tv1.setTextSize(14);
            tv1.setPadding(10,10,10,10);
            tv1.setTextColor(Color.BLACK);
            tv1.setBackgroundColor(Color.argb(100, 47, 79, 79));
            if(task.getCompleted() == 1){
                tv1.setBackgroundColor(Color.argb(100,48,153,58));
            }

            tv1.setOnClickListener(new TextView.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(activity, TaskActivity.class);
                    intent.putExtra("task",task);

                    startActivity(intent);                }
            });

            final Button b1 = new Button(this);
            String s = "Delete " + task.getName();
            b1.setText(s);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  db.deleteTask(task);
                  UpdateList();
                }
            });

            ll.addView(tv1);
            ll.addView(b1);
        }
    }


    public void openCreateTask(View view) {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);

    }


//    public void onTaskClick(int id) {
//        Intent intent = new Intent(this, TaskDetailActivity.class);
//        intent.putExtra("task_id", Integer.toString(id));
//        startActivity(intent);
//    }

}
