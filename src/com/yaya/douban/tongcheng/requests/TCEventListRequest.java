package com.yaya.douban.tongcheng.requests;

import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataRequest;
import com.yaya.douban.common.utils.Tools;
import com.yaya.douban.tongcheng.parsers.TCEventListParser;

public class TCEventListRequest extends BaseDataRequest {
  public static final int TYPE_MY_CREATED = 0;
  public static final int TYPE_MY_WISHED = 1;
  public static final int TYPE_MY_PARTICIPATED = 2;

  @Override
  protected BaseDataParser createParser() {
    return new TCEventListParser();
  }

  public void getEvents(String loc, String district, String type,
      String daytype, int start, int count) {
    path = PATH_EVENT_LIST;
    values.put(PARAM_LOC, loc);
    values.put(PARAM_TYPE, type);
    values.put(PARAM_DAY_TYPE, daytype);
    if (!Tools.isEmpty(district)) {
      values.put(PARAM_LOC_DISTRICT, district);
    }
    values.put(PARAM_START, start + "");
    values.put(PARAM_COUNT, count + "");
    startRequest();
  }

  public void searchEvents(String loc, String key, int start, int count) {
    path = PATH_EVENT_SEARCH;
    values.put(PARAM_LOC, loc);
    if (!Tools.isEmpty(key)) {
      values.put(PARAM_EVENT_KEY, key);
    }
    values.put(PARAM_START, start + "");
    values.put(PARAM_COUNT, count + "");
    startRequest();
  }

  /**
   * 获取当前用户创建、参加、感兴趣的活动
   * 
   * @param start
   * @param count
   */
  public void getMyEvents(int type, int start, int count) {
    if (type == TYPE_MY_CREATED) {
      path = PATH_EVENT_USER_CREATED;
    } else if (type == TYPE_MY_WISHED) {
      path = PATH_EVENT_USER_WISHED;
    } else if (type == TYPE_MY_PARTICIPATED) {
      path = PATH_EVENT_USER_PARTICIPATED;
    }
    if (AppContext.getInstance().getToken() == null) {
      return;
    }
    path = String.format(path, AppContext.getInstance().getToken()
        .getDouban_user_id());
    values.put(PARAM_START, start + "");
    values.put(PARAM_COUNT, count + "");
    startRequest();
  }
}
