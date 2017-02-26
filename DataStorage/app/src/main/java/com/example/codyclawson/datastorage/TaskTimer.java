package com.example.codyclawson.datastorage;

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

    public interface TimerListener
    {
        void OnTimeUpdate(long timeMS);
    }
    private TimerListener mTimerListener;





    private class MyTimer extends TimerTask
    {
        MyTimer()
        {
            super();
        }

        @Override
        public void run() {

            mCurrentTimeMS = mCurrentTimeMS + (System.currentTimeMillis() - mLastTimeMS);
            mLastTimeMS = System.currentTimeMillis();
            mTimerListener.OnTimeUpdate(mCurrentTimeMS);
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


    public TaskTimer(long currentDuration)
    {
        mIsRunning = false;
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

}
