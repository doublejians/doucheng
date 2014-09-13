package com.yaya.douban.tongcheng.responses;

import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.tongcheng.types.json.AlbumPhotoJsonType;

public class TCEventPhotoListResponse extends BaseDataResponse {
  private AlbumPhotoJsonType data;

  public AlbumPhotoJsonType getData() {
    return data;
  }

  public void setData(AlbumPhotoJsonType data) {
    this.data = data;
  }

}
