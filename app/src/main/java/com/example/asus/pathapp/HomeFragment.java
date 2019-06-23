package com.example.asus.pathapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;


public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener,Runnable{

    private static final String TAG = "HomeFragment";
    private SearchView mSearchView;
    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private ListView lvArtical;
    private List<Artical> articalList = new ArrayList<Artical>();//创建集合保存文章信息
    String description;
    String avatarImage ;
    String username;
    String mobilePhoneNumber;
    String[] mobilePhoneNumbers=null;
    String prePic;
    String title ;
    String detail;
    private Handler handler;


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_home,container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSearchView = getView().findViewById(R.id.search_home);
        mSearchView.bringToFront();
        Bmob.initialize(getActivity(), "1f251cee919fc7a00bc654ae6e566e46");

        // 初始化布局 View视图
        initViews();

        // Model数据
        initData();

        // Controller 控制器
        initAdapter();

        // 开启轮播
        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //System.out.println("设置当前位置: " + viewPager.getCurrentItem());
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });

                }
            }
        }.start();

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



//        lvArtical.setOnItemClickListener(new AdapterView.OnItemClickListener() { //点击每一行的点击事件
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position,
//                                    long id) {
//                Artical artical=articalList.get(position);     //获取点击的那一行
//                Log.i(TAG, "onItemClick: List"+artical.getAccountName());
//                }
//        });

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
                        for(String s:mobilePhoneNumbers) {
                            String bql1 = "select * from _User where mobilePhoneNumber='"+s+"'";
                            new BmobQuery<User>().doSQLQuery(getActivity(), bql1, new SQLQueryListener<User>() {
                                @Override
                                public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                                    if (e == null) {
                                        List<User> list1 = bmobQueryResult.getResults();
                                        if (list1 != null && list1.size() > 0) {
                                            Log.i("select from _User:", "查询成功，共" +
                                                    list1.size() + "条数据;");
                                            for (int i = 0; i < list1.size(); i++) {
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
            }
        });
        //获取msg对象，用于返回主线程
        Message msg =handler.obtainMessage(7);
        msg.obj=articalList;
        handler.sendMessage(msg);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }



    private void initViews() {
        viewPager = (ViewPager) getView().findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener((ViewPager.OnPageChangeListener) this);// 设置页面更新监听
//		viewPager.setOffscreenPageLimit(1);// 左右各保留几个对象
        ll_point_container = (LinearLayout) getView().findViewById(R.id.ll_point_container);

        tv_desc = (TextView) getView().findViewById(R.id.tv_desc);
    }

    private void initData() {
        // 图片资源id数组
        imageResIds = new int[]{R.drawable.a, R.drawable.b,
                R.drawable.c, R.drawable.d, R.drawable.e};

        // 文本描述
        contentDescs = new String[]{
                "1*",
                "2*",
                "3*",
                "4*",
                "5*"
        };

        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(getActivity());
            pointView.setBackgroundResource(R.drawable.whitepoint);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if (i != 0)
                layoutParams.leftMargin = 10;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//			System.out.println("isViewFromObject: "+(view == object));
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //System.out.println("instantiateItem初始化: " + position);
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 4

//			newPosition = position % 5
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            //System.out.println("destroyItem销毁: " + position);
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        //System.out.println("onPageSelected: " + position);
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);

//		for (int i = 0; i < ll_point_container.getChildCount(); i++) {
//			View childAt = ll_point_container.getChildAt(position);
//			childAt.setEnabled(position == i);
//		}
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);

        // 记录之前的位置
        previousSelectedPosition = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int i) {
        // 滚动状态变化时调用
    }


}
