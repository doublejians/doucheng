package com.yaya.douban.tongcheng.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataRequest;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.common.widgets.TCListViewEx;
import com.yaya.douban.common.widgets.TCListViewEx.ITCListViewCallBack;
import com.yaya.douban.common.widgets.TCMenuPopup;
import com.yaya.douban.common.widgets.TCMenuPopup.IMenuItemCallback;
import com.yaya.douban.tongcheng.adapter.TCEventListAdapter;
import com.yaya.douban.tongcheng.requests.TCEventListRequest;
import com.yaya.douban.tongcheng.responses.TCEventListResponse;
import com.yaya.douban.tongcheng.types.Loc;
import com.yaya.douban.tongcheng.types.TCEvent;

public class TCEventListActivity extends TCBaseActivity implements
    OnClickListener, ITCListViewCallBack {
  private TextView typeBt, dayTypeBt, locTypeBt; // 三个筛选按钮
  private TCListViewEx eventList;// 活动列表
  private TCEventListAdapter adapter;// 活动适配器
  private TCEventListRequest request;// 请求
  private int currentStart = 0;// 当前起始元素
  private String type = "all", daytype = "future", loc = "118159",
      district = "";// 对应请求的几个参数 活动类型、时间类型、城市ID、区ID
  private TCMenuPopup typePop, dayTypePop, locPop;
  private BroadcastReceiver locReceiver;
  private IntentFilter intentFilter;
  private boolean isAppend = false;
  private Dialog loadingDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_eventlist);
    typeBt = (TextView) findViewById(R.id.type);
    dayTypeBt = (TextView) findViewById(R.id.daytype);
    locTypeBt = (TextView) findViewById(R.id.loc);
    eventList = (TCListViewEx) findViewById(R.id.eventlist);

    typeBt.setOnClickListener(this);
    dayTypeBt.setOnClickListener(this);
    locTypeBt.setOnClickListener(this);

    adapter = new TCEventListAdapter(this);
    eventList.registListCallBack(this);
    eventList.setAdapter(adapter);
    loadingDialog = new Dialog(TCEventListActivity.this);
    loadingDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    loadingDialog.getWindow().setBackgroundDrawableResource(
        R.drawable.shape_menu_bg);
    loadingDialog.setCancelable(false);
    loadingDialog.setContentView(R.layout.footer_for_listview_loading);

    intentFilter = new IntentFilter();
    intentFilter.addAction(AppContext.INTENT_DISTICTS_WEB_RESULT);
    locReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        AppLog.e("xxxxLocReceiver", "onReceive-->" + intent.getAction());
        if (locPop != null && locPop.isPopShown()) {
          locPop.dismissPop();
          locTypeBt.performClick();
        }
      }
    };
    setupListEmptyView();
    requestEvents();
  }

  private void setupListEmptyView() {
    View emptyView = LayoutInflater.from(this).inflate(
        R.layout.empty_event_list, null);
    ((ViewGroup) eventList.getParent()).addView(emptyView);
    eventList.setEmptyView(emptyView);
  }

  @Override
  protected void onResume() {
    registerReceiver(locReceiver, intentFilter);
    super.onResume();
  }

  @Override
  protected void onStop() {
    unregisterReceiver(locReceiver);
    super.onStop();
  }

  private void requestEvents() {
    BaseDataRequest.unregistNetworkCallback(request);
    request = new TCEventListRequest();
    request.registNetworkCallback(new NetworkCallBack() {

      @Override
      public void onRequestCompleted(BaseDataResponse dr) {
        if (dr instanceof TCEventListResponse) {
          eventList.hideFooterProgress();
          if (dr.getResultCode() == 200) {
            TCEventListResponse result = (TCEventListResponse) dr;
            if (isAppend) {
              adapter.appendData(result.getData());
              AppLog.e("xxxxEvent", "append " + (result.getData() == null));
            } else {
              adapter.setData(result.getData());
              AppLog.e("xxxxEvent", "setData " + (result.getData() == null));
            }
            Toast.makeText(TCEventListActivity.this,
                "共为您找到" + result.getTotal() + "个活动", Toast.LENGTH_SHORT).show();
            currentStart += result.getData().size();
          }
        }
        isAppend = false;
        loadingDialog.dismiss();
      }
    });
    loc = AppContext.getInstance().getCurrentLoc().getId();
    if (isAppend) {
      if (loadingDialog.isShowing()) {
        loadingDialog.dismiss();
      }
    } else {
      loadingDialog.show();
    }
    request.getEvents(loc, district, type, daytype, currentStart, 20);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.type: {
      final ArrayList<String> strs = getTypeArray();
      typePop = new TCMenuPopup(this, typeBt, new IMenuItemCallback() {
        @Override
        public void onItemSelected(int position) {
          String typeStr = strs.get(position);
          typeBt.setText(typeStr);
          String selectType = AppContext.getInstance().getEventTypes()
              .get(typeStr);

          if (type == null || !type.equals(selectType)) {
            currentStart = 0;
            type = selectType;
            requestEvents();
          }
        }
      });
      typePop.setShownStrs(strs);
      typePop.showAsDropDown();
    }
      break;
    case R.id.daytype: {
      final ArrayList<String> strs = getDayTypeArray();
      dayTypePop = new TCMenuPopup(this, dayTypeBt, new IMenuItemCallback() {
        @Override
        public void onItemSelected(int position) {
          dayTypeBt.setText(strs.get(position));
          String selectdayType = AppContext.getInstance().getEventDayTypes()
              .get(strs.get(position));

          if (!daytype.equals(selectdayType)) {
            currentStart = 0;
            daytype = selectdayType;
            requestEvents();
          }
        }
      });
      dayTypePop.setShownStrs(strs);
      dayTypePop.showAsDropDown();
    }
      break;
    case R.id.loc: {
      final ArrayList<String> strs = getLocArray();
      locPop = new TCMenuPopup(this, locTypeBt, new IMenuItemCallback() {
        @Override
        public void onItemSelected(int position) {
          AppLog.e("xxxLocs", "locs position---->" + position);
          if (strs == null) {
            return;
          }
          locTypeBt.setText(strs.get(position));
          String selectLocid = "";
          if (position != 0) {
            Loc cloc = AppContext.getInstance().getDistricts()
                .get(position - 1);
            selectLocid = cloc.getId();
          }

          if (!district.equals(selectLocid)) {
            currentStart = 0;
            district = selectLocid;
            requestEvents();
          }
        }
      });
      if (strs == null) {
        // 如果没有在请求区，请求下
        if (!AppContext.getInstance().isDistictsRequesting()) {
          AppContext.getInstance().requestDisricts();
        }
      } else {
        locPop.setShownStrs(strs);
      }
      locPop.showAsDropDown();
    }
      break;
    default:
      break;
    }
  }

  private ArrayList<String> getTypeArray() {
    ArrayList<String> array = new ArrayList<String>();
    HashMap<String, String> types = AppContext.getInstance().getEventTypes();
    array.addAll(types.keySet());
    return array;
  }

  private ArrayList<String> getDayTypeArray() {
    ArrayList<String> array = new ArrayList<String>();
    HashMap<String, String> dayTypes = AppContext.getInstance()
        .getEventDayTypes();
    array.addAll(dayTypes.keySet());
    return array;
  }

  private ArrayList<String> getLocArray() {
    if (AppContext.getInstance().getDistricts() == null) {
      return null;
    }
    ArrayList<String> array = new ArrayList<String>();
    array.add("全部地区");
    for (Loc loc : AppContext.getInstance().getDistricts()) {
      array.add(loc.getName());
    }
    return array;
  }

  @Override
  public void onLoadMore() {
    isAppend = true;
    requestEvents();
    eventList.showFooterProgress();
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position,
      long id) {
    if (position > adapter.getCount() - 1) {
      return;
    }
    // 跳转到活动详情页，将选中的活动对象带到详情页
    TCEvent event = (TCEvent) adapter.getItem(position);
    AppContext.getInstance().setCurrentEvent(event);
    Intent intent = new Intent();
    intent.setClass(TCEventListActivity.this, TCEventDetailActivity.class);
    TCEventListActivity.this.startActivity(intent);
    AppLog.e("xxxxxxx",
        "onItemCLick---->" + position + "    " + event.getTitle());
  }

}
