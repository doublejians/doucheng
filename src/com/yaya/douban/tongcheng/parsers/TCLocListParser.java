package com.yaya.douban.tongcheng.parsers;

import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.http.JsonSerializer;
import com.yaya.douban.tongcheng.responses.TCLocListResponse;
import com.yaya.douban.tongcheng.types.json.TCLocsJsonType;

public class TCLocListParser extends BaseDataParser {

  @Override
  public BaseDataResponse parser(String json) {
    TCLocsJsonType data = JsonSerializer.getInstance().deserialize(json,
        TCLocsJsonType.class);
    TCLocListResponse response = new TCLocListResponse();
    response.setData(data);
    response.setResultCode(200);
    return response;
  }

}
