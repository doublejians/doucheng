package com.yaya.douban.tongcheng.parsers;

import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.http.JsonSerializer;
import com.yaya.douban.tongcheng.responses.XSEventResponse;
import com.yaya.douban.tongcheng.types.json.XSEventJsonType;

public class XSEventParser extends BaseDataParser {

  @Override
  public BaseDataResponse parser(String json) {
    XSEventJsonType xsevent = JsonSerializer.getInstance().deserialize(json,
        XSEventJsonType.class);
    XSEventResponse response = new XSEventResponse();
    response.setData(xsevent);
    response.setResultCode(200);
    return response;
  }

}
