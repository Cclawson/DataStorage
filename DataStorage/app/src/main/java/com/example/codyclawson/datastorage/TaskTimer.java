package com.example.codyclawson.datastorage;

import android.app.Activity;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
/**
 * Created by Kyle on 2/22/2017.
 */



public class TaskTimer {

    private TimerTask task;
    private Timer timer;
    private boolean mIsRunning;

    private long mLastTimeMS;
    private long mCurrentTimeMS;

    public Activity activity;

    public interface TimerListener
    {
        void OnTimeUpdate(long timeMS);
    }
    private TimerListener mTimerListener;


    Runnable uiInterface;



    private class MyTimer extends TimerTask
    {
        MyTimer()
        {
            super();
        }

        @Override
        public void run() {
            activity.runOnUiThread(uiInterface);
        }

    }




    public long getTimeMS()
    {
        return mCurrentTimeMS;
    }



    public void setOnTimeUpdateListener(TimerListener tListener)
    {
        mTimerListener = tListener;
    }


    public TaskTimer(Activity activity, long currentDuration)
    {
        mIsRunning = false;
        this.activity = activity;

        uiInterface = new Runnable() {
            @Override
            public void run() {
                mCurrentTimeMS = mCurrentTimeMS + (System.currentTimeMillis() - mLastTimeMS);
                mLastTimeMS = System.currentTimeMillis();
                mTimerListener.OnTimeUpdate(mCurrentTimeMS);
            }
        };

        task = new MyTimer();
        timer = new Timer();


        mCurrentTimeMS = currentDuration;
    }


    public void Start()
    {
        if(!mIsRunning)
        {
            mLastTimeMS = System.currentTimeMillis();
            timer.scheduleAtFixedRate(task, Calendar.getInstance().getTime(), 1000);
            mIsRunning = true;
        }
    }
    public void startWithNewTime(long timeMS)
    {

        if(!mIsRunning)
        {
            mCurrentTimeMS = timeMS;
            mLastTimeMS = System.currentTimeMillis();
            timer.scheduleAtFixedRate(task, Calendar.getInstance().getTime(), 1000);
            mIsRunning = true;
        }
    }


    public void Pause()
    {
        task.cancel();
        timer.cancel();

        task = new MyTimer();
        timer = new Timer();
        mIsRunning = false;

    }

    public void ResetTime(){
        mCurrentTimeMS = 0;
    }

}
