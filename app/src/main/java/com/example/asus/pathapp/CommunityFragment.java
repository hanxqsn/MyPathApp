package com.example.asus.pathapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class CommunityFragment extends Fragment implements Runnable{

    private static final String TAG = "CommunityFragment";
    private ListView lvArtical;
    private List<Artical> articalList = new ArrayList<Artical>();//创建集合保存文章信息
    private Handler handler;
    String description;
    String avatarImage ;
    String username;
    String mobilePhoneNumber;
    String[] mobilePhoneNumbers=null;
    String prePic;
    String title ;
    String detail;

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
                Intent intent=new Intent(getActivity(), EditArticleActivity.class);
                intent.getStringExtra("username");
                startActivityForResult(intent,1);
            }
        });


        //开启线程
        Thread thread = new Thread(this);
        thread.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==7){
                    lvArtical = getActivity().findViewById(R.id.article_listView);  //获得子布局
                    ArticalAdapter articalAdapter = new ArticalAdapter(getActivity(),
                            R.layout.activity_artical_list, articalList);     //关联数据和子布局
                    lvArtical.setAdapter(articalAdapter);          //绑定数据和适配器
                }
            }
        };
//
//        lvArtical.setOnItemClickListener(new AdapterView.OnItemClickListener() { //点击每一行的点击事件
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position,
//                                    long id) {
//                Artical artical=articalList.get(position);     //获取点击的那一行
//                Log.i(TAG, "onItemClick: List"+artical.getAccountName());
//            }
//        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        Log.i(TAG, result);
    }
    @Override
    public void run() {

        String bql = "select * from ArticalTable";
        new BmobQuery<ArticalTable>().doSQLQuery(getActivity(), bql, new SQLQueryListener
                <ArticalTable>() {
            @Override
            public void done(BmobQueryResult<ArticalTable> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<ArticalTable> list = bmobQueryResult.getResults();
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            Log.i("select * from ArticalTable:", "查询成功，共" +
                                    list.size() + "条数据;");
                            mobilePhoneNumber = list.get(i).getMobilePhoneNumber();
                            mobilePhoneNumbers = new String[list.size()];
                            mobilePhoneNumbers[i] = new String(mobilePhoneNumber);
                            prePic = list.get(i).getPrePic().getUrl();
                            title = list.get(i).getTitle();
                            detail = list.get(i).getContext();
                            Log.i(TAG, "getData:mobilePhoneNumbers.length=" + mobilePhoneNumbers.length);
                        }
                        Log.i(TAG, "getData:mobilePhoneNumbers.length="+mobilePhoneNumbers.length);
                        String bql1 = "select * from _User where mobilePhoneNumber='" + mobilePhoneNumber+ "'";
                        new BmobQuery<User>().doSQLQuery(getActivity(), bql1, new SQLQueryListener<User>() {
                            @Override
                            public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                                if (e == null) {
                                    List<User> list1 = bmobQueryResult.getResults();
                                    if (list1 != null && list1.size() > 0) {
                                        Log.i("select from _User:", "查询成功，共" +
                                                list1.size() + "条数据;");
                                        for (int i=0;i<list1.size();i++) {
                                            description = list1.get(i).getUserDes();
                                            avatarImage = list1.get(i).getUserAvatar().getUrl();
                                            username = list1.get(i).getUsername();
//                                            Log.i(TAG, "done: " + avatarImage + username + description + prePic + title + detail);
                                            articalList.add(new Artical(avatarImage, username, description, prePic, title, detail));
                                        }
                                    } else {
                                        Log.i("smile", "查询成功，无数据返回");
                                    }
                                } else {
                                    Log.i("smile", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                                }
                            }
                        });
                    }
                }
            }
        });
        //获取msg对象，用于返回主线程
        Message msg =handler.obtainMessage(7);
        msg.obj=articalList;
        handler.sendMessage(msg);

    }



}
