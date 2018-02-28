package com.example.internalstorage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button saveButton, clearButton, loadButton;
    EditText multiTextField, filenameTextField;
    FileHelper fileHelper;
    String currentFilename;
    Runnable saveRunnable, loadRunnable;
    Handler loadHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileHelper = new FileHelper(getApplicationContext());

        saveButton = (Button)findViewById(R.id.button_save);
        clearButton = (Button)findViewById(R.id.button_clear);
        loadButton = (Button)findViewById(R.id.button_load);
        multiTextField = (EditText)findViewById(R.id.multiline_text_field);
        filenameTextField = (EditText)findViewById(R.id.filename_text_field);

        saveButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);

        //runnable and handler for loading back the saved text in parallel
        loadHandler = new Handler(){
            public void handleMessage(Message msg){
                multiTextField.setText(msg.getData().getString("Text"));
            }
        };
        loadRunnable = new Runnable() {
            @Override
            public void run() {
                loadText(currentFilename);
            }
        };

        //runnable for performing the text save operation in a separate thread
        saveRunnable = new Runnable() {
            @Override
            public void run() {
                saveText(currentFilename);
            }
        };
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_clear:
                multiTextField.setText("");
                filenameTextField.setText("");
                break;

            case R.id.button_save:
                if(multiTextField.getText().toString().isEmpty()){
                    Toast.makeText(this, "SAVE: Text Field is empty", Toast.LENGTH_SHORT).show();
                    //Log.d("SAVE: ", "Text Field is empty");
                }
                else if(filenameTextField.getText().toString().isEmpty()) {
                    Toast.makeText(this, "SAVE: Filename Field is empty", Toast.LENGTH_SHORT).show();
                    //Log.d("SAVE: ", "Filename Field is empty");
                }
                else
                {
                    currentFilename = filenameTextField.getText().toString();
                    saveRunnable.run();
                    //Log.d("SAVE: ", "Save runnable activated");
                }
                break;

            case R.id.button_load:
                if(filenameTextField.getText().toString().isEmpty()) {
                    Toast.makeText(this, "LOAD: Filename Field is empty", Toast.LENGTH_SHORT).show();
                    //Log.d("SAVE: ", "Filename Field is empty");
                }
                else{
                    currentFilename = filenameTextField.getText().toString();
                    loadHandler.post(loadRunnable);
                    //Toast.makeText(this, "LOAD: Load handler activated", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void saveText(String file){
        fileHelper.saveToInternalStorage(file, multiTextField.getText().toString());
    }

    public void loadText(String file){
        currentFilename = filenameTextField.getText().toString();
        Bundle loadTextBundle = new Bundle();
        Message loadTextMessage = new Message();
        String loadedText = fileHelper.loadFromInternalStorage(file);
        loadTextBundle.putString("Text", loadedText);
        loadTextMessage.setData(loadTextBundle);
        loadHandler.sendMessage(loadTextMessage);
    }
}
