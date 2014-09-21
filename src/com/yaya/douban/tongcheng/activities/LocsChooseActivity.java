package com.yaya.douban.tongcheng.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
  protected static final String TAG = "LocsChooseActivity";
  private TCLocListRequest request;
  private EditText searchET;
  private TCListViewEx citylist;
  private TCLocsAdapter adapter;
  private int currentStart = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    isCustomTitle = true;
    setTheme(R.style.AppTheme);
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.activity_locslist);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
        R.layout.title_customer_default);
    super.onCreate(savedInstanceState);
    TextView titleTv = (TextView) findViewById(R.id.page_title);
    titleTv.setText("选择城市");
    findViewById(R.id.page_title_2).setVisibility(View.GONE);
    findViewById(R.id.left_arrow).setVisibility(View.GONE);
    findViewById(R.id.refresh).setVisibility(View.GONE);
    citylist = (TCListViewEx) findViewById(R.id.citylist);
    searchET = (EditText) findViewById(R.id.city_search);
    searchET.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
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
        citylist.hideFooterProgress();
        if (dr instanceof TCLocListResponse) {
          TCLocListResponse response = (TCLocListResponse) dr;
          ArrayList<Loc> datas = new ArrayList<Loc>();
          if (currentStart == 0) {// 第一次搜索城市
          
            
            Loc tempLoc = ((AppContext)getApplication()).getLocation().getLoc();
          if(tempLoc!=null)
            datas.add(tempLoc);
          }

          for (Loc city : response.getData().getLocs()) {
            datas.add(city);
          }
          currentStart += datas.size();
          Log.e(TAG, "onCreate3"+currentStart);
          adapter.appendData(datas);
          Log.e(TAG, "onCreate4");
        } else {
          AppLog.e("xxxx", "null or error");
          Toast.makeText(LocsChooseActivity.this, "发生错误", Toast.LENGTH_SHORT)
              .show();
        }
      }
    });
      request.getCities(currentStart, PERPAGE_COUNT);
  }

  @Override
  public void onLoadMore() {
    citylist.showFooterProgress();
    requestCities();
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
