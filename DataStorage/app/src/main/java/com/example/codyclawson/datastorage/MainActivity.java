package com.example.codyclawson.datastorage;

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

        //Create timer object with time
        taskTimer = new TaskTimer(4000);
        //Add listener

        taskTimer.setOnTimeUpdateListener(new TaskTimer.TimerListener() {
            @Override
            public void OnTimeUpdate(long timeMS) {
                System.out.println("Time(MS):"+(timeMS/1000));
            }
        });

    }

    public void announceAction(String msg) {
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); // display temporary Toast message
        System.out.println(msg);  // output to console
    }

    public void OnButtonClick(View view)
    {
        taskTimer.Start();
    }


    public void OnCancelClick(View view)
    {
        taskTimer.Pause();
    }



}
