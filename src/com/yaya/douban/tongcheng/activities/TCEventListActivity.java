package com.yaya.douban.tongcheng.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

public class TCEventListActivity extends TCBaseActivity implements
    OnClickListener {
  private TextView typeBt, dayTypeBt, locTypeBt;
  private ListView eventList;
  private TCEventListAdapter adapter;
  private TCEventListRequest request;
  private int currentStart = 0;
  private String type = "all", daytype = "today", loc = "118159",
      district = "";

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
    case R.id.type:
      break;
    case R.id.daytype:
      break;
    case R.id.loc:
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
      break;
    default:
      break;
    }
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
