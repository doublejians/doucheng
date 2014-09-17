package com.yaya.douban.tongcheng.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.utils.Tools;

public class TCEventSearchActivity extends TCBaseEventListActivity implements
    OnClickListener {
  private Button searchBt;
  private EditText keyEt;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    keyEt = (EditText) findViewById(R.id.key_event_search);
    searchBt = (Button) findViewById(R.id.search_bt);
    searchBt.setOnClickListener(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_event_search;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.search_bt) {
      currentStart = 0;
      requestEvents();
    }
  }

  @Override
  protected void requestMethod() {
    request.searchEvents(loc, key, currentStart, 10);
  }

  @Override
  protected boolean checkAndInitRequestParam() {
    key = keyEt.getText() != null ? keyEt.getText().toString() : null;
    loc = AppContext.getInstance().getCurrentLoc().getId();
    if (loc == null) {
      Toast
          .makeText(TCEventSearchActivity.this, "请选择城市后查看", Toast.LENGTH_SHORT)
          .show();
      return false;
    }
    if (Tools.isEmpty(key)) {
      Toast.makeText(TCEventSearchActivity.this, "请输入关键字", Toast.LENGTH_SHORT)
          .show();
      return false;
    }
    return true;
  }

}
