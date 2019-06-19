package com.example.asus.pathapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private static final String TAG = "CommunityFragment";
    private ListView lvArtical;
    private List<Artical> articalList = new ArrayList<Artical>();//创建集合保存文章信息

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_community,container);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //找到控件
        FloatingActionButton addBtn =getView().findViewById(R.id.add_article);
        //设置监听
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditArticleActivity.class));                 }
        });

        lvArtical = getView().findViewById(R.id.article_listView);  //获得子布局
        getData();
        ArticalAdapter articalAdapter = new ArticalAdapter(getActivity(),
                R.layout.activity_artical_list, articalList);     //关联数据和子布局
        lvArtical.setAdapter(articalAdapter);          //绑定数据和适配器

        lvArtical.setOnItemClickListener(new AdapterView.OnItemClickListener() { //点击每一行的点击事件

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                Artical artical=articalList.get(position);     //获取点击的那一行
                Log.i(TAG, "onItemClick: List"+artical.getAccountName());
            }
        });

    }

    private void getData() {
        int[] avatarImage = { R.drawable.a, R.drawable.b,
                R.drawable.c, R.drawable.d,
                R.drawable.e};
        String[] accountName = { "accountName1", "accountName2", "accountName3",
                "accountName4", "accountName5"};
        String[] description={ "description1", "description2", "description3",
                "description4", "description5"};
        int[] articalPicId = { R.drawable.a, R.drawable.b,
                R.drawable.c, R.drawable.d,
                R.drawable.e};
        String[] title={ "title1", "title2", "title3",
                "title4", "title5"};
        String[] detail={ "detail1", "detail2", "detail3",
                "detail4", "detail5"};

        for(int i=0;i<avatarImage.length;i++){         //将数据添加到集合中
            articalList.add(new Artical(avatarImage[i],accountName[i],
                    description[i],articalPicId[i],title[i],detail[i])); //将图片id和对应的name存储到一起
        }
    }
}
