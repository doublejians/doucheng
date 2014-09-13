package com.yaya.douban.tongcheng.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.tongcheng.adapter.TCLocsAdapter;
import com.yaya.douban.tongcheng.requests.TCLocListRequest;
import com.yaya.douban.tongcheng.responses.TCLocListResponse;
import com.yaya.douban.tongcheng.types.Loc;

public class LocsChooseActivity extends TCBaseActivity {
  private final static int PERPAGE_COUNT = 20;
  private TCLocListRequest request;
  private EditText searchET;
  private ListView citylist;
  private TCLocsAdapter adapter;
  private int currentStart = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_locslist);
    citylist = (ListView) findViewById(R.id.citylist);
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
    citylist.setOnScrollListener(new OnScrollListener() {

      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
        case OnScrollListener.SCROLL_STATE_IDLE:
          if (citylist.getLastVisiblePosition() == (citylist.getCount() - 1)) {
            AppLog.e("xxxxScroll", "------last position--------");
            requestCities();
          }
          break;
        }
      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem,
          int visibleItemCount, int totalItemCount) {
      }
    });
    citylist.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
          long id) {
        AppContext.getInstance().setCurrentLoc((Loc) adapter.getItem(position));
        setResult(RESULT_OK);
        finish();
      }
    });
    adapter = new TCLocsAdapter(this);
    citylist.setAdapter(adapter);
    requestCities();
  }

  private void requestCities() {
    request = new TCLocListRequest();
    request.registNetworkCallback(new NetworkCallBack() {
      @Override
      public void onRequestCompleted(BaseDataResponse dr) {
        if (dr instanceof TCLocListResponse) {
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
}
