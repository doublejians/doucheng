package com.yaya.douban.common.activities;

import java.util.ArrayList;
import java.util.HashMap;

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

public class AppContext extends Application {
  public final static String INTENT_DISTICTS_WEB_RESULT = "com.doutongcheng.change.disticts";

  public static HashMap<String, String> eventTypes = new HashMap<String, String>();
  public static HashMap<String, String> eventDayTypes = new HashMap<String, String>();

  private static AppContext instance = new AppContext();

  private Loc currentLoc;
  private HashMap<String, ArrayList<Loc>> disticts = new HashMap<String, ArrayList<Loc>>();

  @Override
  public void onCreate() {
    super.onCreate();
    loadEventTypes();
    loadEventDayTypes();
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
    boolean needRequest = false;
    if (this.currentLoc == null) {
      needRequest = true;
    } else if (!this.currentLoc.equals(currentLoc)
        && !disticts.containsKey(currentLoc.getName())) {
      needRequest = true;
    }
    this.currentLoc = currentLoc;
    if (needRequest) {
      requestDisricts();
    }
  }

  public ArrayList<Loc> getDistricts(String key) {
    if (disticts.containsKey(key)) {
      return disticts.get(key);
    } else {
      requestDisricts();
      return null;
    }
  }

  public ArrayList<Loc> getDistricts() {
    String key = currentLoc.getName();
    return getDistricts(key);
  }

  private void requestDisricts() {
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
