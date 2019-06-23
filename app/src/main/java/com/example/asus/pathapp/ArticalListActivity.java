package com.example.asus.pathapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ArticalListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imgv1=findViewById(R.id.avatar_pic);
        imgv1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ImageView imgv2=findViewById(R.id.article_pic);
        imgv2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }
}
