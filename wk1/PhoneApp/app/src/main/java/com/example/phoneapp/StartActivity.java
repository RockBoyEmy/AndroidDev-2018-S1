package com.example.phoneapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    Button dialerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        dialerButton = (Button)findViewById(R.id.dialerBtn);
        dialerButton.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.dialerBtn:
                openDialer();
                break;
        }
    }

    public void openDialer(){
        Intent openDialerIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(openDialerIntent);
    }
}
