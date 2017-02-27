package com.example.codyclawson.datastorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    Task task;
    DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        db = new DbHandler(this);
        String taskId = getIntent().getExtras().getString("task_id");
        Log.d("task_id: ", taskId);

        task = db.getTask(Integer.parseInt(taskId));

        String log = "Id: " + task.getId() + " ,Parent ID: " + task.getParentId() + " ,Name: " + task.getName() + " ,Color: " + task.getColor() + " ,Time: " + task.getTime() + " ,Category: " + task.getCategoryId() + " ,Priority: " + task.getPriority() + " ,Completed: " + task.getCompleted();

        Log.d("Task: ", log);

        createTaskDetails();
    }

    public void createTaskDetails(){
        final TextView tv1 = new TextView(this);
        tv1.setText(task.getName());
        tv1.setTextSize(14);
        LinearLayout ll = (LinearLayout) findViewById(R.id.taskDetails);
        ll.addView(tv1);
    }
}
