package com.example.tutorial2_intents;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity {

    TextView timeLabel;
    ConstraintLayout cl;
    private long startTime = System.currentTimeMillis();
    int initialTimer;
    Handler timeHandler, layoutColorHandler;
    Runnable timeRunnable, layoutColorRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timeLabel = (TextView)findViewById(R.id.txt_timer);
        cl = (ConstraintLayout)findViewById(R.id.timer_activity);

        //Get starting time from intent extras. In case of invalid value, use 10 sec
        Intent i = this.getIntent();
        initialTimer = i.getIntExtra("TimerStart", 10);
        //String timer = Integer.toString(initialTimer);
        //Toast.makeText(getApplicationContext(), timer, Toast.LENGTH_SHORT).show();
        //Log.d("timer:", timer);

        //Timer handle and runnables
        timeHandler = new Handler() {
            public void handleMessage(Message msg) {
                timeLabel.setText(msg.getData().getString("Time"));
            }
        };
        timeRunnable = new Runnable() {
            public void run() {
                updateTime();
            }
        };
        timeHandler.postDelayed(timeRunnable, 1000);

        //Handle and runnable for background flashing color
        layoutColorHandler = new Handler() {
            public void handleMessage(Message msg) {
                cl.setBackgroundColor(msg.getData().getInt("Color"));
                //timeLabel.setTextColor();
            }
        };
        layoutColorRunnable = new Runnable() {
            @Override
            public void run() {
                blinkBgColor();
            }
        };
        layoutColorHandler.postDelayed(layoutColorRunnable, 500);
    }

    public void updateTime() {
        // Calculate seconds elapsed since the start of the timer
        long mills = System.currentTimeMillis() - startTime;
        Log.d("millis:", Long.toString(mills));
        int seconds = (int) (mills / 1000);
        Log.d("seconds:", Integer.toString(seconds));

        // Calculate the remaining seconds
        int secondsLeft = initialTimer - seconds;
        Log.d("secondsLeft:", Integer.toString(secondsLeft));

        // Convert seconds to minutes
        int minutesLeft = secondsLeft / 60;
        Log.d("minutesLeft:", Integer.toString(minutesLeft));

        // Calculate the leftover to be used as seconds
        secondsLeft = secondsLeft % 60;

        Message message = new Message();
        Bundle bundle = new Bundle();

        String timeString = minutesLeft + ":" + secondsLeft;
        bundle.putString("Time", timeString);
        message.setData(bundle);
        timeHandler.sendMessage(message);
        timeHandler.postDelayed(timeRunnable, 1000);

        if (minutesLeft == 0 && secondsLeft == 0) {
            layoutColorHandler.postDelayed(layoutColorRunnable, 500);
        }
    }

        public void blinkBgColor() {
            Bundle bundle = new Bundle();
            Message message = new Message();
            //String timeString = minutesLeft + ":" + secondsLeft;
            bundle.putInt("Color", Color.RED);
            message.setData(bundle);
            layoutColorHandler.sendMessage(message);
            layoutColorHandler.postDelayed(layoutColorRunnable, 500);

            bundle.putInt("Color", Color.BLUE);
            message.setData(bundle);
            layoutColorHandler.sendMessage(message);
            layoutColorHandler.postDelayed(layoutColorRunnable, 500);
    }
}
