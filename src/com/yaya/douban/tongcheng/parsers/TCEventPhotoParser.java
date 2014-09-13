package com.yaya.douban.tongcheng.parsers;

import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.http.JsonSerializer;
import com.yaya.douban.tongcheng.responses.TCEventPhotoListResponse;
import com.yaya.douban.tongcheng.types.json.AlbumPhotoJsonType;

public class TCEventPhotoParser extends BaseDataParser {

  @Override
  public BaseDataResponse parser(String json) {
    AlbumPhotoJsonType data = JsonSerializer.getInstance().deserialize(json,
        AlbumPhotoJsonType.class);
    TCEventPhotoListResponse response = new TCEventPhotoListResponse();
    response.setResultCode(200);
    response.setData(data);
    response.setTotal(data.getTotal());
    return response;
  }

}
