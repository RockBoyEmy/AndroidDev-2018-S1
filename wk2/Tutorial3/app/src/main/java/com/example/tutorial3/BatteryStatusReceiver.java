package com.example.tutorial3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BatteryStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_BATTERY_LOW)){
            Toast.makeText(context, "Battery low!", Toast.LENGTH_SHORT).show();
            //Log.d("battery: ", "low");
        }
        else if(action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
            Toast.makeText(context, "Airplanes!", Toast.LENGTH_SHORT).show();
        }
    }
}
