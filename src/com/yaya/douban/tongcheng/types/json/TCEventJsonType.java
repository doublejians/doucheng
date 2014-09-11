package com.yaya.douban.tongcheng.types.json;

import com.yaya.douban.tongcheng.types.TCEvent;

public class TCEventJsonType extends BaseCountableType {
  private TCEvent[] events;

  public TCEvent[] getEvents() {
    return events;
  }

  public void setEvents(TCEvent[] events) {
    this.events = events;
  }

}
