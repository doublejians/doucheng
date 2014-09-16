package com.yaya.douban.tongcheng.requests;

import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataRequest;
import com.yaya.douban.common.utils.Tools;
import com.yaya.douban.tongcheng.parsers.TCEventListParser;

public class TCEventListRequest extends BaseDataRequest {

  @Override
  protected BaseDataParser createParser() {
    return new TCEventListParser();
  }

  public void getEvents(String loc, String district, String type,
      String daytype, int start, int count) {
    path = PATH_EVENT_LIST;
    values.put(PARAM_LOC, loc);
    values.put(PARAM_TYPE, type);
    values.put(PARAM_DAY_TYPE, daytype);
    if (!Tools.isEmpty(district)) {
      values.put(PARAM_LOC_DISTRICT, district);
    }
    values.put(PARAM_START, start + "");
    values.put(PARAM_COUNT, count + "");
    startRequest();
  }

  public void searchEvents(String loc, String key, int start, int count) {
    path = PATH_EVENT_SEARCH;
    values.put(PARAM_LOC, loc);
    if (!Tools.isEmpty(key)) {
      values.put(PARAM_EVENT_KEY, key);
    }
    values.put(PARAM_START, start + "");
    values.put(PARAM_COUNT, count + "");
    startRequest();
  }

}
