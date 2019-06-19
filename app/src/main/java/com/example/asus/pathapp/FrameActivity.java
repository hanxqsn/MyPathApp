package com.example.asus.pathapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.bmob.v3.Bmob;

public class FrameActivity extends FragmentActivity {

    private RadioButton rbHome,rbCommunity,rbRunning,rbTeaching,rbSetting;
    private Fragment mFragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    String TAG="FrameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        Bmob.initialize(this, "Your Application ID");
        mFragments = new Fragment[5];
        fragmentManager = getSupportFragmentManager();
        mFragments[0]=fragmentManager.findFragmentById(R.id.home_fragment);
        mFragments[1]=fragmentManager.findFragmentById(R.id.community_fragment);
        mFragments[2]=fragmentManager.findFragmentById(R.id.running_fragment);
        mFragments[3]=fragmentManager.findFragmentById(R.id.teaching_fragment);
        mFragments[4]=fragmentManager.findFragmentById(R.id.setting_fragment);


        fragmentTransaction = fragmentManager.beginTransaction().
                hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2])
                .hide(mFragments[3]).hide(mFragments[4]);
        fragmentTransaction.show(mFragments[0]).commit();

        rbHome=findViewById(R.id.homeButton);
        rbCommunity=findViewById(R.id.communityButton);
        rbRunning=findViewById(R.id.runningButton);
        rbTeaching=findViewById(R.id.teachingButton);
        rbSetting=findViewById(R.id.settingButton);


        rbHome.setBackgroundResource(R.drawable.shape3);

        radioGroup=findViewById(R.id.RunBottomGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i(TAG, "onCheckedChanged: radioGroup,checkedId=" + checkedId);
                fragmentTransaction = fragmentManager.beginTransaction().
                        hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).
                        hide(mFragments[3]).hide(mFragments[4]);
                rbHome.setBackgroundResource(R.drawable.shape2);
                rbCommunity.setBackgroundResource(R.drawable.shape2);
                rbRunning.setBackgroundResource(R.drawable.shape2);
                rbTeaching.setBackgroundResource(R.drawable.shape2);
                rbSetting.setBackgroundResource(R.drawable.shape2);


                switch (checkedId){
                    case R.id.homeButton:
                        fragmentTransaction.show(mFragments[0]).commit();
                        rbHome.setBackgroundResource(R.drawable.shape3);
                        break;
                    case R.id.communityButton:
                        fragmentTransaction.show(mFragments[1]).commit();
                        rbCommunity.setBackgroundResource(R.drawable.shape3);
//                        Log.i(TAG, "onCheckedChanged: click 功能!");
                        break;
                    case R.id.runningButton:
                        fragmentTransaction.show(mFragments[2]).commit();
                        rbRunning.setBackgroundResource(R.drawable.shape3);
                        break;
                    case R.id.teachingButton:
                        fragmentTransaction.show(mFragments[3]).commit();
                        rbTeaching.setBackgroundResource(R.drawable.shape3);
                        break;
                    case R.id.settingButton:
                        fragmentTransaction.show(mFragments[4]).commit();
                        rbSetting.setBackgroundResource(R.drawable.shape3);
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
