package com.yaya.douban.tongcheng.adapter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yaya.douban.tongcheng.types.XSEvent;

public class XSEventAdapter extends BaseAdapter {
  private ArrayList<XSEvent> events;

  @Override
  public int getCount() {
    return events.size();
  }

  @Override
  public Object getItem(int pos) {
    return events.get(pos);
  }

  @Override
  public long getItemId(int pos) {
    return pos;
  }

  @Override
  public View getView(int pos, View convertView, ViewGroup parent) {
    return null;
  }

}
