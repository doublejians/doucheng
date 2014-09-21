package com.yaya.douban.common.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.yaya.douban.R;
import com.yaya.douban.tongcheng.adapter.MyPagerAdapter;

public class WelcomeActivity extends Activity implements OnClickListener {

  // 翻页控件
  private ViewPager mViewPager;

  // 这5个是底部显示当前状态点imageView
  private ImageView mPage0;
  private ImageView mPage1;
  private ImageView mPage2;
  private ImageView mPage3;
  private ImageView mPage4;
  private ImageView mPage5;
  private ImageView mPage6;
  private Button whats_new_start_btn;
  private String TAG = "WelcomeActivity";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    // 去掉标题栏全屏显示

    SharedPreferences references = getSharedPreferences("count",
        MODE_WORLD_READABLE);// 实现第一次登陆显示Guide效果
    int count = references.getInt("count", 0);
    Log.e(TAG, "onCreate" + count);
    // 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
    if (count != 0) {

      Log.e(TAG, "1onCreate" + count);
      Intent intent = new Intent(WelcomeActivity.this, MainActivityNew.class);
      startActivity(intent);
      this.finish();// 关闭WelcomeActivity
    }
    Editor editor = references.edit();
    // 存入数据
    editor.putInt("count", 1);
    // 提交修改
    editor.commit();
    count = references.getInt("count", 0);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);
    mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);
    mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    mPage0 = (ImageView) findViewById(R.id.page0);
    mPage1 = (ImageView) findViewById(R.id.page1);
    mPage2 = (ImageView) findViewById(R.id.page2);
    mPage3 = (ImageView) findViewById(R.id.page3);
    mPage4 = (ImageView) findViewById(R.id.page4);
    mPage5 = (ImageView) findViewById(R.id.page5);
    mPage6 = (ImageView) findViewById(R.id.page6);

    /*
     * 这里是每一页要显示的布局，根据应用需要和特点自由设计显示的内容 以及需要显示多少页等
     */
    LayoutInflater mLi = LayoutInflater.from(this);
    View view1 = mLi.inflate(R.layout.whats_news_gallery_one, null);
    View view2 = mLi.inflate(R.layout.whats_news_gallery_two, null);
    View view3 = mLi.inflate(R.layout.whats_news_gallery_three, null);
    View view4 = mLi.inflate(R.layout.whats_news_gallery_four, null);
    View view5 = mLi.inflate(R.layout.whats_news_gallery_five, null);
    View view6 = mLi.inflate(R.layout.whats_news_gallery_six, null);
    View view7 = mLi.inflate(R.layout.whats_news_gallery_seven, null);

    whats_new_start_btn = (Button) view7.findViewById(R.id.whats_new_start_btn);
    whats_new_start_btn.setOnClickListener(this);
    /*
     * 这里将每一页显示的view存放到ArrayList集合中 可以在ViewPager适配器中顺序调用展示
     */
    final ArrayList<View> views = new ArrayList<View>();
    views.add(view1);
    views.add(view2);
    views.add(view3);
    views.add(view4);
    views.add(view5);
    views.add(view6);
    views.add(view7);

    /*
     * 每个页面的Title数据存放到ArrayList集合中 可以在ViewPager适配器中调用展示
     */
    final ArrayList<String> titles = new ArrayList<String>();
    titles.add("tab1");
    titles.add("tab2");
    titles.add("tab3");
    titles.add("tab4");
    titles.add("tab5");
    titles.add("tab6");
    titles.add("tab7");

    // 填充ViewPager的数据适配器
    MyPagerAdapter mPagerAdapter = new MyPagerAdapter(views, titles);
    mViewPager.setAdapter(mPagerAdapter);
  }

  public class MyOnPageChangeListener implements OnPageChangeListener {

    public void onPageSelected(int page) {

      // 翻页时当前page,改变当前状态园点图片
      switch (page) {
      case 0:
        mPage0
            .setImageDrawable(getResources().getDrawable(R.drawable.page_now));
        mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
        break;
      case 1:
        mPage1
            .setImageDrawable(getResources().getDrawable(R.drawable.page_now));
        mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page));
        mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
        break;
      case 2:
        mPage2
            .setImageDrawable(getResources().getDrawable(R.drawable.page_now));
        mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
        mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
        break;
      case 3:
        mPage3
            .setImageDrawable(getResources().getDrawable(R.drawable.page_now));
        mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
        mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
        break;
      case 4:
        mPage4
            .setImageDrawable(getResources().getDrawable(R.drawable.page_now));
        mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
        mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
        break;
      case 5:
        mPage5
            .setImageDrawable(getResources().getDrawable(R.drawable.page_now));
        mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
        mPage6.setImageDrawable(getResources().getDrawable(R.drawable.page));
        break;
      case 6:
        mPage6
            .setImageDrawable(getResources().getDrawable(R.drawable.page_now));
        mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
        break;
      }
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    public void onPageScrollStateChanged(int arg0) {
    }
  }

  @Override
  public void onClick(View arg0) {
    // TODO Auto-generated method stub
    Intent intent = new Intent(WelcomeActivity.this, MainActivityNew.class);
     //Intent intent = new Intent(WelcomeActivity.this, LocationActivity.class);
    startActivity(intent);
    this.finish();// 关闭WelcomeActivity
  }

}
