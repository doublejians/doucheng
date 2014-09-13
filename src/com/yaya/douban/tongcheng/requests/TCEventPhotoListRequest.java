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

  public void getCurrentEventPhotos(int start, int count) {
    String eventId = AppContext.getInstance().getCurrentEvent().getId();
    path = String.format(PATH_EVENT_PHOTOS, eventId);
    values.put(PARAM_START, start + "");
    values.put(PARAM_COUNT, count + "");
    startRequest();
  }
}
