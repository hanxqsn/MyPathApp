package com.example.asus.pathapp;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


public class EditArticleActivity extends AppCompatActivity {



    private Button sureBtn;
    private EditText title;
    private EditText context;
    private String TAG ="EditArticleActivity";
    private List<Bitmap> data = new ArrayList<Bitmap>();
    private GridView mGridView;
    private SubmitAdapter adapter;
    private String photoPath;
    private List<String> imgList = new ArrayList<>();
    private static final int REQUEST_PICK = 101; // 需要
    String mobilePhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);
        Bmob.initialize(this, "1f251cee919fc7a00bc654ae6e566e46");
        mGridView = findViewById(R.id.gridView);
        context = findViewById(R.id.et_context);
        title=findViewById(R.id.et_title);
        sureBtn= findViewById(R.id.find_comment_submit);

        Bitmap bp=BitmapFactory.decodeResource(getResources(),R.drawable.icon_add);
        data.add(bp);
        initOnClick();
        adapter=new SubmitAdapter(getApplicationContext(),data,mGridView,4);
        mGridView.setAdapter(adapter);

        //确定button事件处理提交发布
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String contextStr=context.getText().toString();
                final String titleStr=title.getText().toString();
                final String imagePath = imgList.get(0);

//                Toast.makeText(EditArticleActivity.this, "确定--"
//                        +contextStr+titleStr+imagePath, Toast.LENGTH_SHORT).show();
//                Log.i(TAG, "onClick: "+"确定--"
//                        +contextStr+titleStr+imagePath);

                File file=new File(imagePath);
                final BmobFile bmobFile = new BmobFile(file);
                bmobFile.uploadblock(EditArticleActivity.this,new UploadFileListener(){
                    @Override
                    public void onFailure(int i, String s) {
                        Log.i(TAG, "onFailure: upload失败原因"+s);
                    }
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "onSuccess: contextStr="+contextStr+",titleStr="+
                                titleStr+",imagePath="+imagePath);
                        ArticalTable at=new ArticalTable();
                        at.setTitle(titleStr);
                        at.setContext(contextStr);
                        at.setPrePic(bmobFile);
                        at.setMobilePhoneNumber(BmobUser.getCurrentUser(
                                EditArticleActivity.this,User.class).
                                getMobilePhoneNumber());
                        Log.i(TAG, "onSuccess: 已加入ArticalTable"+BmobUser.getCurrentUser(
                                EditArticleActivity.this,User.class).
                                getMobilePhoneNumber());

                        at.save(EditArticleActivity.this,new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Log.i(TAG, "onSuccess: 加入数据库成功！！！！");
                            }
                            @Override
                            public void onFailure(int i, String s) {
                                Log.i(TAG, "onFailure: 加入数据库失败，原因："+s);
                            }
                        });
                    }
                });
                //数据是使用Intent返回
                Intent intentt = new Intent();
                //把返回数据存入Intent
                intentt.putExtra("result", "from EditArticleActivity");
                //设置返回数据
                EditArticleActivity.this.setResult(RESULT_OK, intentt);
                //关闭Activity
                EditArticleActivity.this.finish();
            }

        });
    }

    private void initOnClick(){
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data.size() == 5) {
                    Toast.makeText(EditArticleActivity.this, "图片已满4张",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (position == data.size() - 1) {
                        // 选择图片
                        if (ActivityCompat.checkSelfPermission(EditArticleActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                                .PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(EditArticleActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    1);
                        }
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.
                                EXTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(intent, "请选择图片"),
                                REQUEST_PICK);
                    } else {
//                        Toast.makeText(InDynamicActivity.this, "点击第" + (position + 1) + "
// 号图片", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != data.size()-1){
                    dialog(position);
                }
                return true;
            }

        });

    }
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    // 响应startActivityForResult，获取图片路径
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datas) {
        if (requestCode == REQUEST_PICK && resultCode == RESULT_OK) {
            if (datas != null) {
                try {
                    Uri uri = datas.getData();
                    // 这里开始的第二部分，获取图片的路径：
                    String[] proj = { MediaStore.Images.Media.DATA };
                    Cursor cursor = null;
                    if (Build.VERSION.SDK_INT < 11){
                        cursor = managedQuery(uri, proj, null, null,
                                null);
                    }else {
                        CursorLoader cursorLoader = new CursorLoader(this, uri, proj,
                                null, null, null);
                        cursor = cursorLoader.loadInBackground();
                    }
                    // 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.
                            Media.DATA);
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    photoPath = cursor.getString(column_index);
                    if (!photoPath.equals("") || photoPath != null){
                        imgList.add(photoPath);
                    }
                    Log.i("photoPath","-----str---->路径  " + photoPath);
                    Log.i("photoPath","----uri---->路径  " + Uri.parse(photoPath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("photoPath","-------------->  运行到这里");
        if (!TextUtils.isEmpty(photoPath)) {
            Bitmap newBp = decodeSampledBitmapFromFd(photoPath,300,300);
            data.remove(data.size() - 1);
//            bg2.setImageBitmap(newBp);
            Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_add);
            data.add(newBp);
            data.add(bp);
            //将路径设置为空，防止在手机休眠后返回Activity调用此方法时添加照片
            photoPath = null;
            adapter.notifyDataSetChanged();
            if (data.size() == 5){
                data.remove(data.size() - 1);
            }
        }

    }
    public static Bitmap decodeSampledBitmapFromFd(String pathName, int reqWidth,
                                                   int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        //避免出现内存溢出的情况，进行相应的属性设置。
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return src;
    }
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize)
                    > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    }
