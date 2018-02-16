package com.example.admin.phonekeypadwk1;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GestureDetectorCompat gDetector;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bAsterisk, bHash, bCall;
    TextView phoneField;
    String number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting up the dial buttons to their respective IDs
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);
        b5 = (Button)findViewById(R.id.button5);
        b6 = (Button)findViewById(R.id.button6);
        b7 = (Button)findViewById(R.id.button7);
        b8 = (Button)findViewById(R.id.button8);
        b9 = (Button)findViewById(R.id.button9);
        b0 = (Button)findViewById(R.id.button0);
        bAsterisk = (Button)findViewById(R.id.buttonAsterix);
        bHash = (Button)findViewById(R.id.buttonHash);
        bCall = (Button)findViewById(R.id.buttonCall);
        phoneField = (TextView)findViewById(R.id.phoneNumberField);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b0.setOnClickListener(this);
        bAsterisk.setOnClickListener(this);
        bHash.setOnClickListener(this);
        bCall.setOnClickListener(this);
        gDetector = new GestureDetectorCompat(this, new GesturesListener());
        phoneField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (view.getId()){
                    case R.id.phoneNumberField:
                        gDetector.onTouchEvent(motionEvent);
                        break;
                }
                return false;
            }
        });

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button1:
                number = number+1;
                break;
            case R.id.button2:
                number = number+2;
                break;
            case R.id.button3:
                number = number+3;
                break;
            case R.id.button4:
                number = number+4;
                break;
            case R.id.button5:
                number = number+5;
                break;
            case R.id.button6:
                number = number+6;
                break;
            case R.id.button7:
                number = number+7;
                break;
            case R.id.button8:
                number = number+8;
                break;
            case R.id.button9:
                number = number+9;
                break;
            case R.id.button0:
                number = number+0;
                break;
            case R.id.buttonAsterix:
                number = number+"*";
                break;
            case R.id.buttonHash:
                number = number+"#";
                break;
            case R.id.buttonCall:
                Toast.makeText(getApplicationContext(), "Calling...", Toast.LENGTH_SHORT).show();
                break;
        }
        phoneField.setText(number);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    class GesturesListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event){
            return true;
        }

        @Override
        public boolean onFling(MotionEvent m1, MotionEvent m2, float velX, float velY){
            number = "";
            phoneField.setText(" ");
            return true;
        }

    }
}
