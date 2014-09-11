package com.yaya.douban.tongcheng.responses;

import java.util.ArrayList;

import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.tongcheng.types.TCEvent;

public class TCEventListResponse extends BaseDataResponse {
  private ArrayList<TCEvent> data = new ArrayList<TCEvent>();

  public ArrayList<TCEvent> getData() {
    return data;
  }

  public void setData(ArrayList<TCEvent> data) {
    this.data = data;
  }

}
