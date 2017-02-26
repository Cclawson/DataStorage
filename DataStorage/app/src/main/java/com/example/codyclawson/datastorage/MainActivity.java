package com.example.codyclawson.datastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHandler db = new DbHandler(this);

// Inserting Shop/Rows
        Log.d("Insert: ", "Inserting .." );
        db.addTask(new Task("Task 1", "#ffffff", 120, 1, 2, 0));
        db.addTask(new Task("Task 2", "#eee", 1000, 2, 3, 0));
        db.addTask(new Task("Task 3", "#dddddd", 1, 1, 1, 0));
        db.addTask(new Task("Task 4", "#ccc", 6130, 3, 5, 0));


        // Reading all shops
        Log.d("Reading: ", "Reading all shops after adding.." );
        List<Task> tasks = db.getAllTasks();

        for (Task task : tasks) {
            String log = "Id: " + task.getId() + " ,Parent ID: " + task.getParentId() + " ,Name: " + task.getName() + " ,Color: " + task.getColor() + " ,Time: " + task.getTime() + " ,Category: " + task.getCategoryId() + " ,Priority: " + task.getPriority() + " ,Completed: " + task.getCompleted();
            // Writing shops to log
            Log.d("Task: : ", log);
            final TextView tv1 = new TextView(this);
            tv1.setText(task.getName());
            tv1.setTextSize(14);
            LinearLayout ll = (LinearLayout) findViewById(R.id.tasklist);
            ll.addView(tv1);
        }

    }


    public void openCreateTask(View view){
        Intent intent = new Intent(this, CreateTaskActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

}