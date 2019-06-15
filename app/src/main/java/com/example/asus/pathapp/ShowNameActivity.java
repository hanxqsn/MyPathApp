package com.example.asus.pathapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowNameActivity extends ListActivity implements Runnable{

    String TAG="ShowNameActivity";
    private org.jsoup.nodes.Element h4;
    private Handler handler;
    private List<HashMap<String,String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;//配适器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_name);

        //Intent intent=getIntent();
        //String herf=intent.getStringExtra("herf");
        //String name=intent.getStringExtra("name");
        //String time=intent.getStringExtra("time");
        //String place=intent.getStringExtra("place");
        //Log.i(TAG, "onCreate: "+",name="+name+",time="+time+",place="+place);

        //开启线程
        Thread t = new Thread(this);
        t.start();

        this.setListAdapter(listItemAdapter);

        //Handler的作用：与其他线程协同工作，接收其他线程的消息并通过接收到的消息更新主UI线程的内容。
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==8){
                    listItems= (List<HashMap<String,String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(ShowNameActivity.this, listItems,//ListItems数据源
                            R.layout.activity_show_name, //ListItem的XML布局
                            new String[]{"name", "detail"},//key
                            new int[]{R.id.name, R.id.detail}
                    );
                }
                setListAdapter(listItemAdapter);
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        List<HashMap<String,String>> comList = new ArrayList<HashMap<String,String>>();

        //获取网络数据，放入list带回到主线程中
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.51guanfang.com/race/").get();
            Elements tables = doc.getElementsByTag("table");
//            Log.i(TAG, "run: "+tables);


            org.jsoup.nodes.Element table2 = tables.get(0);
//            Log.i(TAG,"run: table2=" + table2);

            Elements h4s=table2.getElementsByTag("h4");
            Elements tds=table2.getElementsByTag("td");
            //Elements as=table2.getElementsByTag("a");


            org.jsoup.nodes.Element td2=null;
            //String atext=null;
            String name = null;

            //Log.i(TAG, "run: timeChoose="+timeChoose[0]);

            for(int i =0;i<=23;i+=1) {
                h4 = h4s.get(i);
                name = h4.text();

                td2 = tds.get(i*2 + 1);
                String val2=td2.html();

                //atext = as.get(i+8).text();

                String timeStr=val2.substring(0,11);
                String placeStr=val2.substring(11);

                Intent intent=getIntent();
                String dateChoose=intent.getStringExtra("chooseDate");

                HashMap<String,String> map = new HashMap<String, String>();
                if (dateChoose.equals(timeStr)) {
                    //Log.i(TAG, "run: time:"+timeStr+"  "+"place:"+placeStr);

                    map.put("name",name);
                    map.put("detail",val2);

                    comList.add(map);
                    //Log.i(TAG, "run: comlist"+comList);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取msg对象，用于返回主线程
        Message msg =handler.obtainMessage(8);
        // msg.obj="Hello from what()";
        msg.obj=comList;
        handler.sendMessage(msg);

    }

}

