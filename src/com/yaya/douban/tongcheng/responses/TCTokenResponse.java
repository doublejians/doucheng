package com.yaya.douban.tongcheng.responses;

import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.tongcheng.types.Token;

public class TCTokenResponse extends BaseDataResponse {
  private Token token;

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }

}
