package com.yaya.douban.tongcheng.responses;

import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.tongcheng.types.json.TCLocsJsonType;

public class TCLocListResponse extends BaseDataResponse {
  private TCLocsJsonType data;

  public TCLocsJsonType getData() {
    return data;
  }

  public void setData(TCLocsJsonType data) {
    this.data = data;
  }

}
