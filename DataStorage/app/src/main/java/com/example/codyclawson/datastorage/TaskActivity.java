package com.example.codyclawson.datastorage;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InvalidClassException;

public class TaskActivity extends AppCompatActivity {
    TaskTimer taskTimer;
    DbHandler db;
    Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        // long time, int categoryId, int priority, int completed)
        //task = new Task(1,1,"Test task", "#ff00ff",1000,1,1,0);
        task = (Task)intent.getSerializableExtra("task");

        db = new DbHandler(this);


        long startTime = task.getTime();


        taskTimer = new TaskTimer(this, startTime);
        final RelativeLayout layout = (RelativeLayout)findViewById(R.id.activity_task);

        final TextView timeText = (TextView) findViewById(R.id.textViewTime);
        final EditText taskName = (EditText) findViewById(R.id.editTextTaskName);
        final Button startButton = (Button) findViewById(R.id.buttonStart);
        final Button pauseButton = (Button) findViewById(R.id.buttonPause);
        final Button completeButton = (Button) findViewById(R.id.buttonCompleteTask);
        final Button updateNameButton = (Button) findViewById(R.id.buttonUpdateName);
        final TextView completedText = (TextView) findViewById(R.id.completedText);
        final Button editButton = (Button) findViewById(R.id.editBtn);
        final Button resetButton = (Button) findViewById(R.id.resetTimeBtn);


        completedText.setVisibility(View.INVISIBLE);
        try {
            layout.setBackgroundColor(Color.parseColor(task.getColor()));
        }
        catch(IllegalArgumentException e)
        {

        }

        if(task.getCompleted() == 1){
            startButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.INVISIBLE);
            completeButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.INVISIBLE);
            completedText.setVisibility(View.VISIBLE);
            layout.setBackgroundColor(Color.argb(100,48,153,58));
        }

        String tempTime = "Time(S): "+(startTime/1000);
        timeText.setText(tempTime);
        taskName.setText(task.getName());

        taskName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() != 0)
                    {
                        updateNameButton.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        updateNameButton.setVisibility(View.INVISIBLE);
                    }
            }
        });

        updateNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Name before: " + db.getTask(task.getId()).getName());

                task.setName(taskName.getText().toString());
                db.updateTask(task);

                System.out.println("Name after: " + db.getTask(task.getId()).getName());
                updateNameButton.setVisibility(View.INVISIBLE);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskTimer.Start();
            }
        });

       pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskTimer.Pause();


            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskTimer.Pause();
                task.setCompleted(1);
                onBackPressed();
            }
        });

        taskTimer.setOnTimeUpdateListener(new TaskTimer.TimerListener() {
            @Override
            public void OnTimeUpdate(long timeMS) {
                String s = "Time(S):"+(timeMS/1000);
                timeText.setText(s);
            }
        });



    }

    @Override
    public void onBackPressed() {
        taskTimer.Pause();
        task.setTime(taskTimer.getTimeMS());
        db.updateTask(task);

        db.close();
        finish();
    }

    public void deleteTask(View view){
        db.deleteTask(task);


        db.close();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void editTask(View view){
        taskTimer.Pause();
        task.setTime(taskTimer.getTimeMS());
        db.updateTask(task);

        db.close();
        finish();

        Intent intent = new Intent(this, EditTaskActicity.class);
        intent.putExtra("task",task);
        startActivity(intent);
    }

    public void resetTime(View view){
        final TextView timeText = (TextView) findViewById(R.id.textViewTime);

        taskTimer.Pause();
        taskTimer.ResetTime();
        String s = "Time(S):"+(taskTimer.getTimeMS()/1000);
        timeText.setText(s);
        task.setTime(taskTimer.getTimeMS());
        db.updateTask(task);
    }

}
