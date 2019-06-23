package com.example.asus.pathapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class RegisActivity extends AppCompatActivity {

    EditText regisTel;
    EditText regisPwd;
    Button submitRegisBtn;
    Button getRegisBtn;
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
        getRegisBtn=findViewById(R.id.getRegisBtn);
        submitLogBtn=findViewById(R.id.submitLogBtn);


        Bmob.initialize(this, "1f251cee919fc7a00bc654ae6e566e46");
//        Log.i(TAG, "onCreate: "+"telStr="+telStr+",pwdStr="+pwdStr);

        getRegisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                telStr = String.valueOf(regisTel.getText());
                //验证码注册与登陆
                BmobSMS.requestSMSCode(RegisActivity.this, telStr,
                        "message_PA", new RequestSMSCodeListener() {

                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        // TODO Auto-generated method stub
                        if(ex==null){//验证码发送成功
                            Log.i(TAG, "短信id："+smsId);//用于查询本次短信发送详情
                        }else {
                            Log.i(TAG, "发送失败");
                        }
                    }
                });
            }
        });
        submitRegisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telStr = String.valueOf(regisTel.getText());
                pwdStr = String.valueOf(regisPwd.getText());//验证码

                BmobUser.signOrLoginByMobilePhone(RegisActivity.this, telStr,
                        pwdStr, new LogInListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                // TODO Auto-generated method stub
                                if(user!=null){
                                    Log.i("smile","用户登陆成功");
                                    Intent intent=new Intent(RegisActivity.this,
                                            FrameActivity.class);
                                    intent.putExtra("telStr",telStr);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
        submitLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telStr = String.valueOf(regisTel.getText());
                pwdStr = String.valueOf(regisPwd.getText());//密码
                BmobUser.loginByAccount(RegisActivity.this, telStr,
                        pwdStr, new LogInListener<User>() {

                            @Override
                            public void done(User user, BmobException e) {
                                if(user!=null){
                                    Log.i(TAG,"用户登陆成功");
                                    Intent intent=new Intent(RegisActivity.this,
                                            FrameActivity.class);
                                    intent.putExtra("telStr",telStr);
                                    startActivity(intent);
                                }else{
                                    Log.i(TAG,"用户登陆失败"+e);
                                }
                            }
                        });
            }
        });

    }

}
