package com.yaya.douban.tongcheng.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.common.widgets.TCListViewEx;
import com.yaya.douban.common.widgets.TCListViewEx.ITCListViewCallBack;
import com.yaya.douban.tongcheng.adapter.TCLocsAdapter;
import com.yaya.douban.tongcheng.requests.TCLocListRequest;
import com.yaya.douban.tongcheng.responses.TCLocListResponse;
import com.yaya.douban.tongcheng.types.Loc;

public class LocsChooseActivity extends TCBaseActivity implements
    ITCListViewCallBack {
  private final static int PERPAGE_COUNT = 20;
  private TCLocListRequest request;
  private EditText searchET;
  private TCListViewEx citylist;
  private TCLocsAdapter adapter;
  private int currentStart = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_locslist);
    citylist = (TCListViewEx) findViewById(R.id.citylist);
    searchET = (EditText) findViewById(R.id.city_search);
    searchET.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
          int after) {

      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
    adapter = new TCLocsAdapter(this);

    citylist.registListCallBack(this);
    citylist.setAdapter(adapter);
    citylist.setLoadingViewBgColor(getResources().getColor(R.color.white));
    onLoadMore();
  }

  private void requestCities() {
    request = new TCLocListRequest();
    request.registNetworkCallback(new NetworkCallBack() {
      @Override
      public void onRequestCompleted(BaseDataResponse dr) {
        if (dr instanceof TCLocListResponse) {
          citylist.hideFooterProgress();
          TCLocListResponse response = (TCLocListResponse) dr;
          ArrayList<Loc> datas = new ArrayList<Loc>();
          for (Loc city : response.getData().getLocs()) {
            datas.add(city);
          }
          currentStart += datas.size();
          adapter.appendData(datas);
        } else {
          AppLog.e("xxxx", "null or error");
        }
      }
    });
    request.getCities(currentStart, PERPAGE_COUNT);
  }

  @Override
  public void onLoadMore() {
    requestCities();
    citylist.showFooterProgress();
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position,
      long id) {
    if (position >= adapter.getCount()) {
      return;
    }
    // 选中城市后 将城市存储到全局Appcontext里面并关闭Activity
    AppContext.getInstance().setCurrentLoc((Loc) adapter.getItem(position));
    setResult(RESULT_OK);
    finish();
  }
}
