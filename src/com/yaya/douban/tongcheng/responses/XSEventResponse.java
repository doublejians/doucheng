package com.yaya.douban.tongcheng.responses;

import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.tongcheng.types.json.XSEventJsonType;

public class XSEventResponse extends BaseDataResponse {
  private XSEventJsonType data;

  public XSEventJsonType getData() {
    return data;
  }

  public void setData(XSEventJsonType data) {
    this.data = data;
  }
}
