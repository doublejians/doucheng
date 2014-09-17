package com.yaya.douban.tongcheng.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.utils.Tools;
import com.yaya.douban.tongcheng.requests.TCEventListRequest;

/**
 * 处理
 * 
 * @author doublejian
 * 
 */
public class TCEventMyActivity extends TCBaseEventListActivity implements
    OnClickListener {
  private static final int[] IDS_TAB = { R.id.tg_my_created, R.id.tg_my_wished,
      R.id.tg_my_participant };// togglebutton的ID，对应TCEventListRequest.TYPE_My_xxx的值
  private int reqType = TCEventListRequest.TYPE_MY_CREATED; // 类型，默认为我创建的
  private ToggleButton[] tabs = new ToggleButton[IDS_TAB.length];

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    int index = -1;
    for (int id : IDS_TAB) {
      tabs[++index] = (ToggleButton) findViewById(id);
      tabs[index].setTag(index);
      tabs[index].setOnClickListener(this);
      tabs[index].setChecked(false);
    }
    if (AppContext.getInstance().getToken() != null
        && !Tools
            .isEmpty(AppContext.getInstance().getToken().getAccess_token())) {
      tabs[reqType].performClick();
    }
  }

  @Override
  protected void onResume() {
    if (AppContext.getInstance().getToken() == null
        || Tools.isEmpty(AppContext.getInstance().getToken().getAccess_token())) {
      Intent intent = new Intent(TCEventMyActivity.this, LoginActivity.class);
      startActivityForResult(intent, 111);
    }
    super.onResume();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      tabs[reqType].performClick();
    }
  }

  private void updateToggleButtons() {
    for (int i = 0; i < tabs.length; i++) {
      tabs[i].setChecked(i == reqType ? true : false);
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_event_my;
  }

  @Override
  public void onClick(View v) {
    if (v.getTag() instanceof Integer) {
      reqType = (Integer) v.getTag();
      updateToggleButtons();
      requestEvents();
    }
  }

  @Override
  protected void requestMethod() {
    request.getMyEvents(reqType, currentStart, 10);
  }

  @Override
  protected boolean checkAndInitRequestParam() {
    if (reqType > -1 && reqType < tabs.length) {
      return true;
    }
    return false;
  }
}
