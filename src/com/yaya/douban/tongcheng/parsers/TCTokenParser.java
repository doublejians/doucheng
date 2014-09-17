package com.yaya.douban.tongcheng.parsers;

import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.http.JsonSerializer;
import com.yaya.douban.tongcheng.responses.TCTokenResponse;
import com.yaya.douban.tongcheng.types.Token;

public class TCTokenParser extends BaseDataParser {

  @Override
  public BaseDataResponse parser(String json) {
    Token token = JsonSerializer.getInstance().deserialize(json, Token.class);
    TCTokenResponse response = new TCTokenResponse();
    response.setToken(token);
    if (token.getCode() != 0) {
      // 如果code有错误码，则认为请求失败
      response.setResultCode(-100);
    } else {
      response.setResultCode(200);
    }
    return response;
  }

}
