package com.example.asus.pathapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ArticalAdapter extends ArrayAdapter<Artical> {

    private int resourceId;
    String TAG="ArticalAdapter";

    // 第一个参数是上下文环境，第二个参数是每一项的子布局，第三个参数是数据
    public ArticalAdapter(Context context, int textViewResourceId, List<Artical> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;          //获取子布局
    }

    @Override
    //getView方法在每个子项被滚动到屏幕内的时候都会被调用，每次都将布局重新加载一边
    public View getView(int position, View convertView, ViewGroup parent) {
        //第一个参数表示位置，第二个参数表示缓存布局，第三个表示绑定的view对象
        View view;

        ViewHolder viewHolder;         //实例ViewHolder，当程序第一次运行，保存获取到的控件，提高效率
        if(convertView==null){
            viewHolder=new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(//convertView为空代表布局没有被加载过，即getView方法没有被调用过，需要创建
                    resourceId, null);     // 得到子布局，非固定的，和子布局id有关
            //获取控件,只需要调用一遍，调用过后保存在ViewHolder中
            viewHolder.avatarImage = view.findViewById(R.id.avatar_pic);
            viewHolder.accountName =view.findViewById(R.id.account_name);
            viewHolder.description =view.findViewById(R.id.description);
            viewHolder.articalPicImage = view.findViewById(R.id.article_pic);
            viewHolder.title = view.findViewById(R.id.article_title);
            viewHolder.detail = view.findViewById(R.id.article_detail);
            view.setTag(viewHolder);
        }else{
            view=convertView;      //convertView不为空代表布局被加载过，只需要将convertView的值取出即可
            viewHolder=(ViewHolder) view.getTag();
        }


        Artical artical = getItem(position);//实例指定位置的文章

        viewHolder.avatarImage.setImageBitmap(getHttpBitmap(artical.getAvatarId()));
//        Log.i(TAG, "getView: artical.getAvatarId()="+artical.getAvatarId());
//        Log.i(TAG, "getView: getHttpBitmap(artical.getAvatarId())="+getHttpBitmap(artical.getAvatarId()));
        viewHolder.accountName.setText(artical.getAccountName());
        viewHolder.description.setText(artical.getDescription());
        viewHolder.articalPicImage.setImageBitmap(getHttpBitmap(artical.getArticalPicId()));
        viewHolder.title.setText(artical.getTitle());
        viewHolder.detail.setText(artical.getDetail());

        return view;
    }
    public Bitmap returnBitMap(String url){
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
//            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
//            Log.i(TAG, "getHttpBitmap:-------- myFileURL="+myFileURL);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
//            Log.i(TAG, "getHttpBitmap:------conn="+conn);
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(0);
//            Log.i(TAG, "getHttpBitmap:-------setConnectTimeout ");
            //连接设置获得数据流
            conn.setDoInput(true);
//            Log.i(TAG, "getHttpBitmap: ------setDoInput");
            //不使用缓存
            conn.setUseCaches(true);
//            Log.i(TAG, "getHttpBitmap: ------setUseCaches");
            //这句可有可无，没有影响
            conn.connect();
//            Log.i(TAG, "getHttpBitmap: ------connect");
            //得到数据流
            InputStream is = conn.getInputStream();
//            Log.i(TAG, "getHttpBitmap: ------is="+is);
            //解析得到图片
//            bitmap = BitmapFactory.decodeStream(is);
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 10;   //width，hight设为原来的十分一
            bitmap =BitmapFactory.decodeStream(is,null,options);
//            Log.i(TAG, "getHttpBitmap:------- bitmap="+bitmap);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    class ViewHolder{   //当布局加载过后，保存获取到的控件信息。
        ImageView avatarImage;
        TextView accountName;
        TextView description;
        ImageView articalPicImage;
        TextView title;
        TextView detail;
    }



}
