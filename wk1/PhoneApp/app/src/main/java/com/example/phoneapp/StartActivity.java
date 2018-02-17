package com.example.phoneapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    Button dialerButton, browserButton;
    TextView timeField;
    Handler timeHandler;
    Runnable timeRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        dialerButton = (Button)findViewById(R.id.dialerBtn);
        dialerButton.setOnClickListener(this);
        browserButton = (Button)findViewById(R.id.browserBtn);
        browserButton.setOnClickListener(this);
        timeField = (TextView)findViewById(R.id.clockView);

        timeHandler = new Handler() {
            public void handleMessage(Message msg) {
                timeField.setText(msg.getData().getString("Time"));
            }
        };
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
            }
        };
        timeHandler.postDelayed(timeRunnable, 1000);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.dialerBtn:
                openDialer();
                break;
            case R.id.browserBtn:
                openBrowsingActivity();
                break;
        }
    }

    public void openDialer(){
        Intent openDialerIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(openDialerIntent);
    }

    public void openBrowsingActivity(){
        Intent openBrowsingIntent = new Intent(getApplicationContext(), BrowsingActivity.class);
        startActivity(openBrowsingIntent);
    }

    public void updateTime() {
        Message message = new Message();
        Bundle bundle = new Bundle();

        //get the current time of day, minutes and hours
        Calendar now = Calendar.getInstance();
        int hours = (int)now.get(Calendar.HOUR_OF_DAY);
        int minutes = (int)now.get(Calendar.MINUTE);
        int seconds = (int)now.get(Calendar.SECOND);

        String sHours, sMinutes, secondsBlink, timeStamp;

        //make sure to have leading zeroes for hour and minutes if < 9, and set them
        if(hours <= 9){
            sHours = "0" + Integer.toString(hours);
        }
        else{
            sHours = Integer.toString(hours);
        }

        if(minutes <=9){
            sMinutes = "0"+Integer.toString(minutes);
        }
        else{
            sMinutes = Integer.toString(minutes);
        }

        //when the seconds are even, make the ':' separator appear, otherwise not
        if(seconds%2 == 0){
            secondsBlink = ":";
        }
        else{
            secondsBlink = " ";
        }

        //send the time string to the handler, every 1 second
        timeStamp = sHours + secondsBlink + sMinutes;
        bundle.putString("Time", timeStamp);
        message.setData(bundle);
        timeHandler.sendMessage(message);
        timeHandler.postDelayed(timeRunnable, 1000);
    }
}
