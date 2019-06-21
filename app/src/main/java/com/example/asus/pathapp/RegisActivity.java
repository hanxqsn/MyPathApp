package com.example.asus.pathapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class RegisActivity extends AppCompatActivity {

    EditText regisTel;
    EditText regisPwd;
    Button submitRegisBtn;
    Button submitLogBtn;
    String telStr;
    String pwdStr;
    private String TAG="RegisActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        regisTel=findViewById(R.id.regisTel);
        regisPwd=findViewById(R.id.regisPwd);
        submitRegisBtn=findViewById(R.id.submitRegisBtn);
        submitLogBtn=findViewById(R.id.submitLogBtn);

        telStr = String.valueOf(regisTel.getText());
        pwdStr = String.valueOf(regisPwd.getText());
        Bmob.initialize(this, "1f251cee919fc7a00bc654ae6e566e46");
        Log.i(TAG, "onCreate: "+"telStr="+telStr+",pwdStr="+pwdStr);

        submitRegisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmobUser bu = new BmobUser();
                bu.setUsername(telStr);
                bu.setPassword(pwdStr);
                bu.signUp(RegisActivity.this, new SaveListener(){

                    @Override
                    public void onSuccess() {
                        Toast.makeText(RegisActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i(TAG, "onFailure: "+"telStr="+telStr+",pwdStr="+pwdStr);
                        Toast.makeText(RegisActivity.this, "telStr="+telStr+",pwdStr="+pwdStr, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(RegisActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onFailure: s:"+s);
                    }
                });
            }
        });

        submitLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bu2 = new BmobUser();
                bu2.setUsername(telStr);
                bu2.setPassword(pwdStr);
                bu2.login(RegisActivity.this,new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(RegisActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisActivity.this,FrameActivity.class);
                        intent.putExtra("username",telStr);
                        intent.putExtra("password",pwdStr);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(RegisActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "登陆onFailure: s:"+s);

                    }
                });
            }
        });
    }

}
