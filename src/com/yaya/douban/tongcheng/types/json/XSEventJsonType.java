package com.yaya.douban.tongcheng.types.json;

import com.yaya.douban.tongcheng.types.XSEvent;

public class XSEventJsonType {
  private int count;
  private int start;
  private int total;
  private XSEvent[] onlines;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public XSEvent[] getOnlines() {
    return onlines;
  }

  public void setOnlines(XSEvent[] onlines) {
    this.onlines = onlines;
  }

}
