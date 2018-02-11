package com.example.tutorial3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class CustomMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getExtras().get("Message").toString(), Toast.LENGTH_LONG).show();
        Log.d("intent broadcast: ", intent.getExtras().get("Message").toString());
    }
}
