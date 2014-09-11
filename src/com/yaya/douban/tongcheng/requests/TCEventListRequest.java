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

}
