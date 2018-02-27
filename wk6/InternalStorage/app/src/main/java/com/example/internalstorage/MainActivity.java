package com.example.internalstorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button saveButton, clearButton, loadButton;
    EditText multiTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveButton = (Button)findViewById(R.id.button_save);
        clearButton = (Button)findViewById(R.id.button_clear);
        loadButton = (Button)findViewById(R.id.button_load);
        multiTextField = (EditText)findViewById(R.id.multiline_text_field);

        saveButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
    }
    
    public void onClick(View v) {

    }
}
