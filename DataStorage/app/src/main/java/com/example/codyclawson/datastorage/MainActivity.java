package com.example.codyclawson.datastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        final DbHandler db = new DbHandler(this);

// Inserting Shop/Rows
        //Log.d("Insert: ", "Inserting .." );
        //db.addTask(new Task("Task 1", "#ffffff", 120, 1, 2, 0));
        //db.addTask(new Task("Task 2", "#eee", 1000, 2, 3, 0));
        db.addTask(new Task("Magenta", "#ff00ff", 1, 1, 1, 0));
        //db.addTask(new Task("Task 4", "#ccc", 6130, 3, 5, 0));

        UpdateList(db);
        db.close();

    }

    @Override
    protected void onResume() {
        super.onResume();

        final DbHandler db = new DbHandler(this);
        UpdateList(db);
        db.close();

    }


    private void UpdateList(DbHandler db)
    {

        LinearLayout ll = (LinearLayout) findViewById(R.id.tasklist);
        ll.removeAllViews();
        // Reading all tasks
        Log.d("Reading: ", "Reading all tasks after adding.." );
        List<Task> tasks = db.getAllTasks();
        for (final Task task : tasks) {
            //TEMP


            String log = "Id: " + task.getId() + " ,Parent ID: " + task.getParentId() + " ,Name: " + task.getName() + " ,Color: " + task.getColor() + " ,Time: " + task.getTime() + " ,Category: " + task.getCategoryId() + " ,Priority: " + task.getPriority() + " ,Completed: " + task.getCompleted();
            // Writing tasks to log
            Log.d("Task: : ", log);
            final TextView tv1 = new TextView(this);
            tv1.setText(task.getName());
            tv1.setTextSize(14);

            final Button b1 = new Button(this);
            String s = "Open " + task.getName();
            b1.setText(s);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, TaskActivity.class);
                    intent.putExtra("task",task);

                    startActivity(intent);
                }
            });




            ll.addView(tv1);
            ll.addView(b1);
        }
    }


    public void openCreateTask(View view){
        Intent intent = new Intent(this, CreateTaskActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_ message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }


}
