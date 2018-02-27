package com.example.internalstorage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button saveButton, clearButton, loadButton;
    EditText multiTextField;
    FileHelper fileHelper;
    String filename = "myfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileHelper = new FileHelper(getApplicationContext());

        saveButton = (Button)findViewById(R.id.button_save);
        clearButton = (Button)findViewById(R.id.button_clear);
        loadButton = (Button)findViewById(R.id.button_load);
        multiTextField = (EditText)findViewById(R.id.multiline_text_field);

        saveButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_clear:
                multiTextField.setText("");
                break;
            case R.id.button_save:
                if(multiTextField.getText().toString().isEmpty()){
                    Toast.makeText(this, "SAVE: Text Field is empty", Toast.LENGTH_SHORT).show();
                    break;
                }
                else{
                    fileHelper.saveToInternalStorage(filename, multiTextField.getText().toString());
                    break;
                }
            case R.id.button_load:
                String loadedText = fileHelper.loadFromInternalStorage(filename);
                multiTextField.setText(loadedText);
        }

    }
}
