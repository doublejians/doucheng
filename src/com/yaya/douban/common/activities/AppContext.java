package com.yaya.douban.common.activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Application;
import android.content.Intent;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yaya.douban.R;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.tongcheng.requests.TCLocListRequest;
import com.yaya.douban.tongcheng.responses.TCLocListResponse;
import com.yaya.douban.tongcheng.types.Loc;
import com.yaya.douban.tongcheng.types.TCEvent;

public class AppContext extends Application {
  public final static String INTENT_DISTICTS_WEB_RESULT = "com.doutongcheng.change.disticts";

  private static AppContext instance = new AppContext();

  private boolean distictsRequesting = false;
  private Loc currentLoc;// 当前城市
  private TCEvent currentEvent;// 正在查看或者操作的活动
  private LinkedHashMap<String, ArrayList<Loc>> disticts = new LinkedHashMap<String, ArrayList<Loc>>();// 缓存所有查看过的城市的区
  private static LinkedHashMap<String, String> eventTypes = new LinkedHashMap<String, String>();// 活动类型
  private static LinkedHashMap<String, String> eventDayTypes = new LinkedHashMap<String, String>();// 活动时间类型

  @Override
  public void onCreate() {
    super.onCreate();
    loadEventTypes();
    loadEventDayTypes();
    @SuppressWarnings("deprecation")
    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
        getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
        .denyCacheImageMultipleSizesInMemory()
        .discCacheFileNameGenerator(new Md5FileNameGenerator())
        .tasksProcessingOrder(QueueProcessingType.LIFO).build();
    ImageLoader.getInstance().init(config);
    instance = this;
  }

  private void loadEventTypes() {
    String[] types = getResources().getStringArray(R.array.tc_event_types);
    for (String type : types) {
      String[] splitStr = type.split(" ");
      AppLog.e("xxxEventTypes", splitStr[0] + " " + splitStr[1]);
      eventTypes.put(splitStr[0], splitStr[1]);
    }
  }

  private void loadEventDayTypes() {
    String[] dayTypes = getResources()
        .getStringArray(R.array.tc_event_day_type);
    for (String type : dayTypes) {
      String[] splitStr = type.split(" ");
      AppLog.e("xxxEventDayTypes", splitStr[0] + " " + splitStr[1]);
      eventDayTypes.put(splitStr[0], splitStr[1]);
    }
  }

  public static AppContext getInstance() {
    return instance;
  }

  public Loc getCurrentLoc() {
    return currentLoc;
  }

  public void setCurrentLoc(Loc currentLoc) {
    this.currentLoc = currentLoc;
  }

  public ArrayList<Loc> getDistricts(String key) {
    return disticts.get(key);
  }

  public ArrayList<Loc> getDistricts() {
    String key = currentLoc.getName();
    return getDistricts(key);
  }

  public LinkedHashMap<String, String> getEventTypes() {
    return eventTypes;
  }

  public static void setEventTypes(LinkedHashMap<String, String> eventTypes) {
    AppContext.eventTypes = eventTypes;
  }

  public LinkedHashMap<String, String> getEventDayTypes() {
    return eventDayTypes;
  }

  public void setEventDayTypes(LinkedHashMap<String, String> eventDayTypes) {
    AppContext.eventDayTypes = eventDayTypes;
  }

  public TCEvent getCurrentEvent() {
    return currentEvent;
  }

  public void setCurrentEvent(TCEvent currentEvent) {
    this.currentEvent = currentEvent;
  }

  public boolean isDistictsRequesting() {
    return distictsRequesting;
  }

  public void setDistictsRequesting(boolean distictsRequesting) {
    this.distictsRequesting = distictsRequesting;
  }

  // 请求城市对应的区
  public void requestDisricts() {
    if (distictsRequesting) {
      return;
    }
    distictsRequesting = true;
    TCLocListRequest request = new TCLocListRequest();
    request.registNetworkCallback(new NetworkCallBack() {
      @Override
      public void onRequestCompleted(BaseDataResponse dr) {
        if (dr instanceof TCLocListResponse) {
          TCLocListResponse rs = (TCLocListResponse) dr;
          String key = currentLoc.getName();
          if (!disticts.containsKey(key) && rs.getData().getLocs() != null) {
            ArrayList<Loc> locs = new ArrayList<Loc>();
            for (Loc loc : rs.getData().getLocs()) {
              locs.add(loc);
              AppLog.e("xxxDisticts", "-===========" + loc.getName());
            }
            disticts.put(key, locs);
            broadcastDistictsChange();
          }
        }
        distictsRequesting = false;
      }
    });
    request.getDistricts(currentLoc.getId(), 0, 30);
  }

  private void broadcastDistictsChange() {
    Intent intent = new Intent();
    intent.setAction(INTENT_DISTICTS_WEB_RESULT);
    AppContext.getInstance().getApplicationContext().sendBroadcast(intent);
  }
}
