package com.example.asus.pathapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ArticalAdapter extends ArrayAdapter<Artical> {

    private int resourceId;

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

        viewHolder.avatarImage.setImageResource(artical.getAvatarId());
        viewHolder.accountName.setText(artical.getAccountName());
        viewHolder.description.setText(artical.getDescription());
        viewHolder.articalPicImage.setImageResource(artical.getArticalPicId());
        viewHolder.title.setText(artical.getTitle());
        viewHolder.detail.setText(artical.getDetail());

        return view;
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
