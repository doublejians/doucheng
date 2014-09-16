package com.yaya.douban.common.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.yaya.douban.R;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.tongcheng.activities.LocsChooseActivity;
import com.yaya.douban.tongcheng.activities.TCEventListActivity;
import com.yaya.douban.tongcheng.activities.WeeklyHotActivity;
import com.yaya.douban.tongcheng.requests.XSEventRequest;
import com.yaya.douban.tongcheng.responses.XSEventResponse;
import com.yaya.douban.tongcheng.types.XSEvent;

public class MainActivity extends Activity implements OnClickListener {
  private String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    super.onCreate(savedInstanceState);
    // getEvetns();
    findViewById(R.id.bt_start).setOnClickListener(this);
    findViewById(R.id.bt_event_list).setOnClickListener(this);
    findViewById(R.id.bt_weekhot_list).setOnClickListener(this);
  }

  public void getEvetns() {
    XSEventRequest request = new XSEventRequest();
    request.registNetworkCallback(new NetworkCallBack() {

      @Override
      public void onRequestCompleted(BaseDataResponse dr) {
        if (dr.getResultCode() < 0) {
          AppLog.e("xxxEvent", "getResultCode<0 error");
        } else {
          if (dr instanceof XSEventResponse) {
            XSEventResponse data = (XSEventResponse) dr;
            AppLog
                .e("xxxEvent", data.getData().getCount() + " "
                    + data.getData().getStart() + " "
                    + data.getData().getOnlines());
            for (XSEvent event : data.getData().getOnlines()) {
              AppLog.e("xxxEvent", event.toString());
            }
          }
        }
      }
    });
    request.getAllXSEvent();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.bt_start: {
      Intent intent = new Intent(MainActivity.this, LocsChooseActivity.class);
      AppLog.e(TAG, "bt_start");
      startActivity(intent);
    }
      break;
    case R.id.bt_event_list: {
      Intent intent = new Intent(MainActivity.this, TCEventListActivity.class);
      startActivity(intent);
    }
      break;
    case R.id.bt_weekhot_list: {
      Intent intent = new Intent(MainActivity.this, WeeklyHotActivity.class);
      AppLog.e(TAG, "bt_weekhot_list");
      startActivity(intent);
    }
      break;
    default:
      break;
    }
  }

}
