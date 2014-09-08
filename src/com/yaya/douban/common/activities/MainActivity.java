package com.yaya.douban.common.activities;

import android.app.Activity;
import android.os.Bundle;

import com.yaya.douban.R;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.tongcheng.requests.XSEventRequest;
import com.yaya.douban.tongcheng.responses.XSEventResponse;
import com.yaya.douban.tongcheng.types.XSEvent;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    super.onCreate(savedInstanceState);
    getEvetns();
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

}
