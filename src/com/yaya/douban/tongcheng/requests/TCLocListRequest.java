package com.yaya.douban.tongcheng.requests;

import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataRequest;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.tongcheng.parsers.TCLocListParser;

public class TCLocListRequest extends BaseDataRequest {

  @Override
  protected BaseDataParser createParser() {
    return new TCLocListParser();
  }

  public void getCities(int start, int count) {
    AppLog.e("xxx", "getLocs");
    path = PATH_LOCS_CITIES;
    if (start > 0) {
      values.put(PARAM_START, start + "");
    }
    if (count > 0) {
      values.put(PARAM_COUNT, count + "");
    }
    startRequest();
  }

  public void getDistricts(String city, int start, int count) {
    path = String.format(PATH_LOCS_DISTRICTS, city);
    if (start > 0) {
      values.put(PARAM_START, start + "");
    }
    if (count > 0) {
      values.put(PARAM_COUNT, count + "");
    }
    startRequest();
  }
}
