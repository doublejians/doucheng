package com.yaya.douban.common.activities;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.yaya.douban.R;
import com.yaya.douban.tongcheng.activities.LocsChooseActivity;
import com.yaya.douban.tongcheng.activities.TCEventListActivity;
import com.yaya.douban.tongcheng.activities.WeeklyHotActivity;

@SuppressWarnings("deprecation")
public class MainActivityNew extends ActivityGroup implements
    View.OnClickListener {
  private static final int TAB_COUNT = 3;
  private static final int[] ID_ToggleButton = { R.id.btnModule1,
      R.id.btnModule2, R.id.btnModule3 };
  private static final Class<?>[] classes = { WeeklyHotActivity.class,
      TCEventListActivity.class, LocsChooseActivity.class };

  private LinearLayout container = null;
  private ToggleButton[] tabButtons = new ToggleButton[TAB_COUNT];

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main_new);
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
  public void onClick(View v) {
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
    container.removeAllViews();
    container.addView(
        getLocalActivityManager().startActivity(
            "Module1",
            new Intent(MainActivityNew.this, classes[tag])
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView(), -1,
        -1);
  }
}
