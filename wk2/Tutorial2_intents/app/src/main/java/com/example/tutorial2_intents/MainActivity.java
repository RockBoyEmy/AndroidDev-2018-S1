package com.example.tutorial2_intents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button timerButton, dialButton, browserButton, mapButton;
    EditText inputBox, textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //associating the views to their objects, by id
        timerButton = (Button)findViewById(R.id.btn_timer);
        dialButton = (Button)findViewById(R.id.btn_dial);
        browserButton = (Button)findViewById(R.id.btn_browser);
        mapButton = (Button)findViewById(R.id.btn_map);
        inputBox = (EditText)findViewById(R.id.inputBox);
        textInput = (EditText)findViewById(R.id.textInput);

        //setting up listener for the buttons as "this" main view
        timerButton.setOnClickListener(this);
        dialButton.setOnClickListener(this);
        browserButton.setOnClickListener(this);
        mapButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case (R.id.btn_timer):
                start_timer();
                break;
            case(R.id.btn_dial):
                dial_number();
                break;
            case(R.id.btn_browser):
                open_browser();
                break;
            case(R.id.btn_map):
                show_map();
                //testInputReplacement();
                break;
            default:
                break;
        }
    }

    public void start_timer(){
        final String timeStr = inputBox.getText().toString();
        Intent i = new Intent(MainActivity.this, TimerActivity.class);

        //Convert String to int, but only if it is a valid
        int t = 10;
        if(!timeStr.equals("") && timeStr != null){
            t = Integer.valueOf(timeStr);
        }

        //put starting time into intent extras
        i.putExtra("TimerStart", t);
        startActivity(i);
    }
    public void dial_number(){
        String number = inputBox.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }
    public void open_browser(){
        String input = textInput.getText().toString();
        String httpTest = "http://", httpsTest = "https://";
        if(!input.startsWith(httpTest)||!input.startsWith(httpsTest)){
            input = "http://"+input;
        }
        //Uri webpage = Uri.parse("http://www.abertay.ac.uk");
        Uri webpage = Uri.parse(input);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }
    public void show_map(){
        Uri location;
        String input = textInput.getText().toString();
        if(input.isEmpty()){
            //Or map point based on latitude/longitude
            //z param is zoom level
            location = Uri.parse("geo:56.4633099, -2.976105?z=16");
        }
        else{
            //Map point based on address
            input = spaceCharReplacer(input);
            //location = Uri.parse("geo:0,0?q=Abertay+University,+Dundee,+UK");
            location = Uri.parse("geo:0,0?q="+input);
        }

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);
    }

    public String spaceCharReplacer(String s){
        String input = s;
        //String input = "Some search string";
        input = input.replace(" ", "+");
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        return input;
    }






}
