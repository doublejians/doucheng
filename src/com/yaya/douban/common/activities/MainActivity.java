package com.yaya.douban.common.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.yaya.douban.R;
import com.yaya.douban.tongcheng.activities.LocsChooseActivity;
import com.yaya.douban.tongcheng.activities.TCBaseActivity;
import com.yaya.douban.tongcheng.activities.TCEventListActivity;

public class MainActivity extends TCBaseActivity implements OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.bt_start).setOnClickListener(this);
    findViewById(R.id.bt_event_list).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.bt_start: {
      Intent intent = new Intent(MainActivity.this, LocsChooseActivity.class);
      startActivity(intent);
    }
      break;
    case R.id.bt_event_list: {
      Intent intent = new Intent(MainActivity.this, TCEventListActivity.class);
      startActivity(intent);
    }
      break;
    default:
      break;
    }
  }

}
