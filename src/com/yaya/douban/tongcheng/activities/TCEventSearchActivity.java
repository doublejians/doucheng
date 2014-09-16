package com.yaya.douban.tongcheng.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataRequest;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.common.utils.Tools;
import com.yaya.douban.tongcheng.requests.TCEventListRequest;
import com.yaya.douban.tongcheng.responses.TCEventListResponse;

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
  protected void requestEvents() {
    BaseDataRequest.unregistNetworkCallback(request);
    key = keyEt.getText() != null ? keyEt.getText().toString() : null;
    if (Tools.isEmpty(key)) {
      Toast.makeText(TCEventSearchActivity.this, "请输入关键字", Toast.LENGTH_SHORT)
          .show();
      return;
    }
    request = new TCEventListRequest();
    request.registNetworkCallback(new NetworkCallBack() {

      @Override
      public void onRequestCompleted(BaseDataResponse dr) {
        if (dr instanceof TCEventListResponse) {
          hideListProgress();
          if (dr.getResultCode() == 200) {
            TCEventListResponse result = (TCEventListResponse) dr;
            if (isAppend) {
              adapter.appendData(result.getData());
              AppLog.e("xxxxEvent", "append " + (result.getData() == null));
            } else {
              adapter.setData(result.getData());
              AppLog.e("xxxxEvent", "setData " + (result.getData() == null));
            }
            currentStart += result.getData().size();

            Toast.makeText(
                TCEventSearchActivity.this,
                "共为您找到" + result.getTotal() + "个活动,当前" + currentStart + "/"
                    + result.getTotal(), Toast.LENGTH_SHORT).show();
          }
        }
        isAppend = false;
        hideListProgress();
        hideProgeressDialog();
      }
    });
    loc = AppContext.getInstance().getCurrentLoc().getId();
    if (isAppend) {
      showListProgress();
    }
    showProgressDialog();
    request.searchEvents(loc, key, currentStart, 10);
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

}
