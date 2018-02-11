package com.example.tutorial3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    final int RESULT_SUCCESS = 200, RESULT_FAIL = 404;
    final String correctName = "emilian", correctPass = "emi25";
    EditText name, password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText)findViewById(R.id.userField);
        password = (EditText)findViewById(R.id.passwdField);
        loginButton = (Button)findViewById(R.id.loginBtn_act2);

        loginButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.loginBtn_act2):
                authenticate();
                break;
        }
    }

    public void authenticate() {
        String username = name.getText().toString();
        String passwd = password.getText().toString();
        //Toast.makeText(this, "username: "+username, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "password: "+passwd, Toast.LENGTH_SHORT).show();
        Log.d("username", username);
        Log.d("password", passwd);
        if(username.equals(correctName) && passwd.equals(correctPass)){
            setResult(RESULT_SUCCESS);
            finish();
        }
        else{
            setResult(RESULT_FAIL);
            finish();
        }

    }
}
