package com.example.wk2tutorial1;

import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv;
    Button btn;
    EditText input;
    LinearLayout layout;
    float hsv[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declarations for the views
        tv = (TextView)findViewById(R.id.textView);
        btn = (Button)findViewById(R.id.button);
        input = (EditText)findViewById(R.id.editText);
        layout = (LinearLayout)findViewById(R.id.linLay);

        btn.setOnClickListener(this); //makes this activity (the Main View) act as listener
        tv.setOnClickListener(this);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case (MotionEvent.ACTION_DOWN):
                        btn.setBackgroundColor(Color.RED);
                        return false;
                    case (MotionEvent.ACTION_UP):
                        btn.setBackgroundColor(Color.BLUE);
                        return false;
                }
                return false;
            }
        });
        input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i){
                    case (KeyEvent.KEYCODE_VOLUME_UP):
                        input.setBackgroundColor(Color.BLACK);
                        return false;
                    case (KeyEvent.KEYCODE_VOLUME_DOWN):
                        input.setBackgroundColor(Color.MAGENTA);
                        return false;
                }
                return false;
            }
        });

        hsv = new float[3];
        hsv[0] = 0.0f;//Hue
        hsv[1] = 0.0f;//Saturation
        hsv[2] = 1.0f;//Value

    }

    //Clicking functions
    public void copyText(){
        String text = input.getText().toString(); // get text
        tv.setText(text); // put it into the text view
        input.setText(""); // optional: clear the input field
        }
    public void clearText(){
        tv.setText(""); //set text to empty string to clear it
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button:
                copyText();
                Toast.makeText(getApplicationContext(), "Button Clicked!", Toast.LENGTH_LONG).show();
                break;
            case R.id.textView:
                clearText();
                Toast.makeText(getApplicationContext(), "textView Clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //Touch Event functions
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case (MotionEvent.ACTION_DOWN):
                Log.d("Touch", "Action was DOWN");
                changeBackgroundColour(event);
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d("Touch", "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d("Touch", "Action was UP");
                changeBackgroundColour(event);
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
    public void changeBackgroundColour(MotionEvent event){
        float eventX = event.getX();
        float eventY = event.getY();
        float height = layout.getHeight();
        float width = layout.getWidth();

        hsv[0] = eventY/height*360; //(0 to 360)
        hsv[1] = eventX/width+0.1f; //(0.1 to 1)

        layout.setBackgroundColor(Color.HSVToColor(hsv));
    }
    
    //Key press Events functions
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        Log.d("on key:", "onKeyDown function called");
        switch(keyCode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                Toast.makeText(getApplicationContext(), "Volume and hsv value increased!", Toast.LENGTH_LONG).show();
                hsv[2] += 0.1f;
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Toast.makeText(getApplicationContext(), "Volume and hsv value decreased!", Toast.LENGTH_SHORT).show();
                hsv[2] -= 0.1f;
                return true;
        }
        return false;
    }

}
