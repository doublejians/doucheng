package com.yaya.douban.tongcheng.requests;

import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataRequest;
import com.yaya.douban.tongcheng.parsers.TCEventPhotoParser;

public class TCEventPhotoListRequest extends BaseDataRequest {

  @Override
  protected BaseDataParser createParser() {
    return new TCEventPhotoParser();
  }

  public void getCurrentEventPhotos() {
    String eventId = AppContext.getInstance().getCurrentEvent().getId();
    path = String.format(PATH_EVENT_PHOTOS, eventId);
    startRequest();
  }
}
