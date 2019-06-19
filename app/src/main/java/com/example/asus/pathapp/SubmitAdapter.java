package com.example.asus.pathapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class SubmitAdapter extends BaseAdapter {
    private Context context;
    private List<Bitmap> data;
    private LayoutInflater inflater;
    private GridView mGridView;
    private int gridViewH;
    private int imageViewH;
    private int number;

    public SubmitAdapter(Context context, List<Bitmap> data, GridView mGridView, int i){
        this.context = context;
        this.data = data;
        this.mGridView = mGridView;
        this.number = number;
        inflater = LayoutInflater.from(context);
        ViewGroup.LayoutParams params = mGridView.getLayoutParams();
        gridViewH = params.height;
    }

    @Override
    public int getCount() {
        return  data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            convertView =inflater.inflate(R.layout.gridview_item,null);
            holder =new Holder();
            holder.imageView=convertView.findViewById(R.id.imageView1);
            // 获取到ImageView的显示高度
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
            imageViewH=params.height;
            convertView.setTag(holder);
        }else{
            setGridView(); //  注意加上，此方法是让选择回来的图片按照正方形显示，一排显示4个
            holder =(Holder) convertView.getTag();
        }
        // 绑定图片原始尺寸，方便以后应用
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        int[] parameter = { 300, 300 };
        holder.imageView.setTag(parameter);
        holder.imageView.setImageBitmap(data.get(position));
        return convertView;

    }
    class Holder {
        private ImageView imageView;
    }
    private void setGridView() {
        ViewGroup.LayoutParams lp =mGridView.getLayoutParams();
        if (number == 4){
            if (data.size() < 4) {
                lp.height = gridViewH;
            }
        }
        if (number == 8){
            if (data.size() < 4) {
                lp.height = gridViewH;
            } else if (data.size() < number) {
                lp.height = gridViewH * 2 - (gridViewH - imageViewH) / 2;
            } else {
                lp.height = gridViewH * 3 - (gridViewH - imageViewH);
            }
        }

        mGridView.setLayoutParams(lp);
    }

}
