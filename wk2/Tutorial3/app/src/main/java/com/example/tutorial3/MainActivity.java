package com.example.tutorial3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView loginStatus;
    Button loginButton;
    final static int LOGIN_REQUEST = 100, RESULT_SUCCESS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginStatus = (TextView) findViewById(R.id.statusTextView);
        loginButton = (Button)findViewById(R.id.loginBtn_act1);

        loginButton.setOnClickListener(this);

        broadcastMessage("Here is a message to be broadcast live!");
    }

    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.loginBtn_act1):
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(loginIntent, LOGIN_REQUEST);
                //Toast.makeText(this, "Everything good so far", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == LOGIN_REQUEST) {
            if(resultCode == RESULT_SUCCESS) {
                loginStatus.setText("Status: Logged in");
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            }
            else {
                loginStatus.setText("Status: Login failure!");
                Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void broadcastMessage(String msg) {
        Intent intent = new Intent();
        intent.setAction("uk.ac.abertay.CUSTOM_MESSAGE");
        intent.putExtra("Message", msg);
        sendBroadcast(intent);
    }
}

