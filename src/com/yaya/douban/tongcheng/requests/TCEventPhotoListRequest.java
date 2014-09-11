package com.yaya.douban.tongcheng.requests;

import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataRequest;

public class TCEventPhotoListRequest extends BaseDataRequest {

  @Override
  protected BaseDataParser createParser() {
    return null;
  }

  public void getCurrentEventPhotos() {
    String eventId = AppContext.getInstance().getCurrentEvent().getId();
    path = String.format(PATH_EVENT_PHOTOS, eventId);
    startRequest();
  }
}
