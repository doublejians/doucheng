package com.yaya.douban.tongcheng.activities;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.yaya.douban.tongcheng.types.Loc;

public class Location {
  private LocationClient mLocationClient;
  private LocationMode tempMode = LocationMode.Battery_Saving;
  private String tempcoor = "gcj02";
  public GeofenceClient mGeofenceClient;
  public MyLocationListener mMyLocationListener;
  private Loc gpsLoc;

  public Location(Context context) {
    mLocationClient = new LocationClient(context);
    mMyLocationListener = new MyLocationListener();
    mLocationClient.registerLocationListener(mMyLocationListener);
    mGeofenceClient = new GeofenceClient(context);
    Log.i("BaiduLocationApiDem", "");
    InitLocation();
    mLocationClient.start();

  }

  private void InitLocation() {
    LocationClientOption option = new LocationClientOption();
    option.setLocationMode(tempMode);// 设置定位模式
    option.setCoorType(tempcoor);// 返回的定位结果是百度经纬度，默认值gcj02
    option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
    option.setIsNeedAddress(true);
    mLocationClient.setLocOption(option);
  }

  public class MyLocationListener implements BDLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location) {
      // Receive Location
      gpsLoc = new Loc();
      String tempstr = location.getCity();

      int length = tempstr.length();

      tempstr = tempstr.substring(0, length - 1) + "GPS";

      gpsLoc.setName(tempstr);
      gpsLoc.setId(location.getCityCode());
      Log.i("BaiduLocationApiDem", ""+location.getCityCode());
      mLocationClient.stop();
    }
  }

  public Loc getLoc() {
    return gpsLoc;
  }
}
