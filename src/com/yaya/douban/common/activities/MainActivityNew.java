package com.yaya.douban.common.activities;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.yaya.douban.R;
import com.yaya.douban.tongcheng.activities.LocsChooseActivity;
import com.yaya.douban.tongcheng.activities.TCEventListActivity;
import com.yaya.douban.tongcheng.activities.TCEventSearchActivity;
import com.yaya.douban.tongcheng.activities.WeeklyHotActivity;

@SuppressWarnings("deprecation")
public class MainActivityNew extends ActivityGroup implements
    View.OnClickListener {
  private static final int TAB_COUNT = 3;
  private static final int[] ID_ToggleButton = { R.id.btnModule1,
      R.id.btnModule2, R.id.btnModule3 };
  private static final String[] TAB_TITLES = { "一周热点", "活动列表", "活动搜索" };
  private static final Class<?>[] classes = { WeeklyHotActivity.class,
      TCEventListActivity.class, TCEventSearchActivity.class };

  private LinearLayout container = null;
  private TextView titleTv, cityTv;
  private ImageButton backIb, refreshIb;
  private ToggleButton[] tabButtons = new ToggleButton[TAB_COUNT];

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.AppTheme);
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.activity_main_new);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
        R.layout.title_customer_default);
    titleTv = (TextView) findViewById(R.id.page_title);
    cityTv = (TextView) findViewById(R.id.page_title_2);
    backIb = (ImageButton) findViewById(R.id.left_arrow);
    refreshIb = (ImageButton) findViewById(R.id.refresh);
    cityTv.setOnClickListener(this);
    backIb.setOnClickListener(this);
    refreshIb.setOnClickListener(this);
    backIb.setVisibility(View.INVISIBLE);

    container = (LinearLayout) findViewById(R.id.containerBody);

    for (int i = 0; i < TAB_COUNT; i++) {
      tabButtons[i] = (ToggleButton) findViewById(ID_ToggleButton[i]);
      tabButtons[i].setOnClickListener(this);
      tabButtons[i].setTag(i);
    }
    container.post(new Runnable() {

      @Override
      public void run() {
        tabButtons[0].performClick();
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    cityTv.setText(AppContext.getInstance().getCurrentLoc().getName());
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    boolean isTabs = false;
    for (int tid : ID_ToggleButton) {
      if (id == tid) {
        isTabs = true;
        break;
      }
    }
    if (isTabs) {
      int tag = -1;
      if (v.getTag() == null) {
        return;
      }
      tag = (Integer) v.getTag();
      for (int i = 0; i < tabButtons.length; i++) {
        if (tag != i) {
          tabButtons[i].setChecked(false);
        } else {
          tabButtons[i].setChecked(true);
        }
      }
      titleTv.setText(TAB_TITLES[tag]);

      container.removeAllViews();
      container.addView(
          getLocalActivityManager().startActivity(
              "Module1",
              new Intent(MainActivityNew.this, classes[tag])
                  .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView(),
          -1, -1);
    } else if (id == R.id.left_arrow) {
      // TODO

    } else if (id == R.id.refresh) {
      // TODO
    } else if (id == R.id.page_title_2) {
      Intent intent = new Intent(MainActivityNew.this, LocsChooseActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
    }
  }
}
