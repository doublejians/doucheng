package com.yaya.douban.tongcheng.requests;

import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataRequest;
import com.yaya.douban.tongcheng.parsers.TCTokenParser;

public class TCTokenRequest extends BaseDataRequest {

  @Override
  protected BaseDataParser createParser() {
    return new TCTokenParser();
  }

  public void getToken(String code, String granttype) {
    requestMethod = REQUEST_POST;
    valuesAppendInUrl = true;
    url = "https://www.douban.com/service";
    path = "/auth2/token";
    values.put(PARAM_CLIENT_ID, AppContext.TC_API_KEY);
    values.put(PARAM_CLIENT_SECRET, AppContext.TC_CLIENT_SECRET);
    values.put(PARAM_REDIRECT_URI, AppContext.OAUTH_REDIRECT_URI);
    values.put(PARAM_GRANT_TYPE, granttype);
    if ("authorization_code".equals(granttype)) {
      values.put(PARAM_CODE, code);
    } else {
      values.put(PARAM_REFRESH_TOKEN, code);

    }
    startRequest();
  }

}
