package com.example.codyclawson.datastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TaskTimer taskTimer;

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
        List<Task> tasks = db.getAllTasks();

        for (final Task task : tasks) {
            String log = "Id: " + task.getId() + " ,Parent ID: " + task.getParentId() + " ,Name: " + task.getName() + " ,Color: " + task.getColor() + " ,Time: " + task.getTime() + " ,Category: " + task.getCategoryId() + " ,Priority: " + task.getPriority() + " ,Completed: " + task.getCompleted();

            final TextView tv1 = new TextView(this);
            tv1.setText(task.getName());
            tv1.setTextSize(14);
            tv1.setOnClickListener(new TextView.OnClickListener() {
                public void onClick(View v) {
                    onTaskClick(task.getId());
                }
            });
            LinearLayout ll = (LinearLayout) findViewById(R.id.tasklist);
            ll.addView(tv1);
//            db.deleteTask(task);
        }

        //Create timer object with time
        taskTimer = new TaskTimer(4000);
        //Add listener

        taskTimer.setOnTimeUpdateListener(new TaskTimer.TimerListener() {
            @Override
            public void OnTimeUpdate(long timeMS) {
//                System.out.println("Time(MS):" + (timeMS / 1000));
            }
        });
    }


    public void openCreateTask(View view) {
        Intent intent = new Intent(this, CreateTaskActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }


    public void announceAction(String msg) {
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); // display temporary Toast message
//        System.out.println(msg);  // output to console
    }

    public void OnButtonClick(View view) {
        taskTimer.Start();
    }


    public void OnCancelClick(View view) {
        taskTimer.Pause();
    }

    public void onTaskClick(int id) {
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra("task_id", Integer.toString(id));
        startActivity(intent);
    }

}
