package com.example.gesturestutorial1;

import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private GestureDetectorCompat mDetector;
    float hsv[];
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        layout = (LinearLayout)findViewById(R.id.linLay);
        hsv = new float[3];
        hsv[0] = 0.0f;//Hue
        hsv[1] = 0.0f;//Saturation
        hsv[2] = 1.0f;//Value
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float
                velocityX, float velocityY) {
            hsv[0] = (float) (Math.random() * 360); // random hue
            hsv[1] = (float) Math.random(); // random saturation
            hsv[2] = (float) Math.random(); // random value/lightness
            layout.setBackgroundColor(Color.HSVToColor(hsv));
            return false;
        }

        @Override
        public void onLongPress(MotionEvent event){
            //black
            hsv[0] = 0f;
            hsv[1] = 0f;
            hsv[2] = 0f;
            layout.setBackgroundColor(Color.HSVToColor(hsv));
            //layout.setBackgroundColor(Color.BLACK);
        }
    }
}
