package com.yaya.douban.tongcheng.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.common.widgets.TCMenuPopup;
import com.yaya.douban.common.widgets.TCMenuPopup.IMenuItemCallback;
import com.yaya.douban.tongcheng.adapter.TCEventListAdapter;
import com.yaya.douban.tongcheng.requests.TCEventListRequest;
import com.yaya.douban.tongcheng.responses.TCEventListResponse;
import com.yaya.douban.tongcheng.types.Loc;
import com.yaya.douban.tongcheng.types.TCEvent;

public class TCEventListActivity extends TCBaseActivity implements
    OnClickListener {
  private TextView typeBt, dayTypeBt, locTypeBt; // 三个筛选按钮
  private ListView eventList;// 活动列表
  private TCEventListAdapter adapter;// 活动适配器
  private TCEventListRequest request;// 请求
  private int currentStart = 0;// 当前起始元素
  private String type = "all", daytype = "future", loc = "118159",
      district = "";// 对应请求的几个参数 活动类型、时间类型、城市ID、区ID

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_eventlist);
    typeBt = (TextView) findViewById(R.id.type);
    dayTypeBt = (TextView) findViewById(R.id.daytype);
    locTypeBt = (TextView) findViewById(R.id.loc);
    eventList = (ListView) findViewById(R.id.eventlist);

    typeBt.setOnClickListener(this);
    dayTypeBt.setOnClickListener(this);
    locTypeBt.setOnClickListener(this);

    adapter = new TCEventListAdapter(this);
    eventList.setAdapter(adapter);
    eventList.setOnItemClickListener(new OnItemClickListener() {

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
    });
    requestEvents();
  }

  private void requestEvents() {
    request = new TCEventListRequest();
    request.registNetworkCallback(new NetworkCallBack() {

      @Override
      public void onRequestCompleted(BaseDataResponse dr) {
        if (dr instanceof TCEventListResponse) {
          if (dr.getResultCode() == 200) {
            TCEventListResponse result = (TCEventListResponse) dr;
            adapter.setData(result.getData());
            Toast.makeText(TCEventListActivity.this,
                "共为您找到" + result.getTotal() + "个活动", Toast.LENGTH_SHORT).show();
            currentStart += result.getData().size();
          }
        }
      }
    });
    loc = AppContext.getInstance().getCurrentLoc().getId();
    request.getEvents(loc, district, type, daytype, currentStart, 20);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.type: {
      final ArrayList<String> strs = getTypeArray();
      TCMenuPopup pop = new TCMenuPopup(this, typeBt, strs,
          new IMenuItemCallback() {
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
      pop.showAsDropDown();
    }
      break;
    case R.id.daytype: {
      final ArrayList<String> strs = getDayTypeArray();
      TCMenuPopup pop = new TCMenuPopup(this, dayTypeBt, strs,
          new IMenuItemCallback() {
            @Override
            public void onItemSelected(int position) {
              dayTypeBt.setText(strs.get(position));
              String selectdayType = AppContext.getInstance()
                  .getEventDayTypes().get(strs.get(position));

              if (!daytype.equals(selectdayType)) {
                currentStart = 0;
                daytype = selectdayType;
                requestEvents();
              }
            }
          });
      pop.showAsDropDown();
    }
      break;
    case R.id.loc: {
      final ArrayList<String> strs = getLocArray();
      AppLog.e("xxxLocs", "locs---->" + strs.size());
      TCMenuPopup pop = new TCMenuPopup(this, locTypeBt, strs,
          new IMenuItemCallback() {
            @Override
            public void onItemSelected(int position) {
              AppLog.e("xxxLocs", "locs position---->" + position);

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
      pop.showAsDropDown();
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
    ArrayList<String> array = new ArrayList<String>();
    array.add("全部地区");
    for (Loc loc : AppContext.getInstance().getDistricts()) {
      array.add(loc.getName());
    }
    return array;
  }

}
