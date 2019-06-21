package com.example.asus.pathapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity {

    EditText logTel;
    EditText logPwd;
    Button submitLogBtn;
    String telStr;
    String pwdStr;
    private String TAG="LogInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }
}
