package com.yaya.douban.tongcheng.requests;

import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataRequest;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.tongcheng.parsers.XSEventParser;

public class XSEventRequest extends BaseDataRequest {

  @Override
  protected BaseDataParser createParser() {
    return new XSEventParser();
  }

  public void getAllXSEvent() {
    url = "http://api.douban.com/v2/onlines";
    AppLog.e("xxx", "getAllXSEvent");
    startRequest();
  }

}
