package com.yaya.douban.tongcheng.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.common.utils.IntentUtils;
import com.yaya.douban.common.widgets.TCMenuPopup;
import com.yaya.douban.common.widgets.TCMenuPopup.IMenuItemCallback;
import com.yaya.douban.tongcheng.types.Loc;

public class TCEventListActivity extends TCBaseEventListActivity implements
    OnClickListener {
  private TextView typeBt, dayTypeBt, locTypeBt; // 三个筛选按钮
  private TCMenuPopup typePop, dayTypePop, locPop;
  private BroadcastReceiver locReceiver;
  private IntentFilter intentFilter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    typeBt = (TextView) findViewById(R.id.type);
    dayTypeBt = (TextView) findViewById(R.id.daytype);
    locTypeBt = (TextView) findViewById(R.id.loc);

    typeBt.setOnClickListener(this);
    dayTypeBt.setOnClickListener(this);
    locTypeBt.setOnClickListener(this);

    intentFilter = new IntentFilter();
    intentFilter.addAction(IntentUtils.INTENT_DISTICTS_WEB_RESULT);
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
    requestEvents();
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
  protected int getLayoutId() {
    return R.layout.activity_eventlist;
  }

  @Override
  protected void requestMethod() {
    request.getEvents(loc, district, type, daytype, currentStart, 20);
  }

  @Override
  protected boolean checkAndInitRequestParam() {
    loc = AppContext.getInstance().getCurrentLoc().getId();
    if (loc == null) {
      Toast.makeText(TCEventListActivity.this, "请选择城市后查看", Toast.LENGTH_SHORT)
          .show();
      return false;
    }
    return true;
  }
}
