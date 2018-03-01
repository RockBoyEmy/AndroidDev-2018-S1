package com.example.internalstorage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Switch storageToggle;
    Button saveButton, clearButton, loadButton;
    EditText multiTextField, filenameTextField;
    FileHelper fileHelper;
    String currentFilename;
    Runnable saveRunnable, loadRunnable;
    Handler loadHandler;
    private final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 5;

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
        storageToggle = (Switch) findViewById(R.id.storage_switch);

        saveButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
        storageToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //toggle is enabled, deal only with the external memory
                    //permissions requests
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        // Permission has already been granted
                        // Start the intended actions
                        multiTextField.setText("");
                        filenameTextField.setText("");
                        saveLoadExternal();
                    }
                    else{
                        // Should we show an explanation if not granted?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(MainActivity.this,
                                    "Permissions are needed for this functionality. Please accept them",
                                    Toast.LENGTH_LONG)
                                    .show();
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
                        }
                        else {
                            // No explanation needed; request the permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);

                            // MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    }
                }
                else {
                    //toggle is disabled - deal only with internal storage
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
            }
        });
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
    public void saveTextExternal(String file){
        if(fileHelper.isExternalStorageWritable()){
            fileHelper.saveToExternalStorage(file, multiTextField.getText().toString());
        }
        else{
            Toast.makeText(this, "Save External: storageNotWritable", Toast.LENGTH_SHORT).show();
        }
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
    public void loadTextExternal(String file){
        if(fileHelper.isExternalStorageReadable()){
            currentFilename = filenameTextField.getText().toString();
            Bundle loadTextBundle = new Bundle();
            Message loadTextMessage = new Message();
            String loadedText = fileHelper.loadFromInternalStorage(file);
            loadTextBundle.putString("Text", loadedText);
            loadTextMessage.setData(loadTextBundle);
            loadHandler.sendMessage(loadTextMessage);
        }
        else{
            Toast.makeText(this, "Load External: storageNotWritable nor readable", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "onRequestPermissionResult: granted", Toast.LENGTH_SHORT).show();
                    storageToggle.setChecked(true);

                } else {
                    Toast.makeText(this, "onRequestPermissionResult: denied", Toast.LENGTH_SHORT).show();
                    storageToggle.setChecked(false);
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void saveLoadExternal(){
        loadHandler = new Handler(){
            public void handleMessage(Message msg){
                multiTextField.setText(msg.getData().getString("Text"));
            }
        };
        loadRunnable = new Runnable() {
            @Override
            public void run() {
                loadTextExternal(currentFilename);
            }
        };

        //runnable for performing the text save operation in a separate thread
        saveRunnable = new Runnable() {
            @Override
            public void run() {
                saveTextExternal(currentFilename);
            }
        };
    }


}
