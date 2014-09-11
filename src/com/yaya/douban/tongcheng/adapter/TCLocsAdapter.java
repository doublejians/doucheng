package com.yaya.douban.tongcheng.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yaya.douban.tongcheng.types.Loc;

public class TCLocsAdapter extends BaseAdapter {
  private Context context;
  private ArrayList<Loc> datas = new ArrayList<Loc>();

  public TCLocsAdapter(Context tcontext) {
    context = tcontext;
  }

  public void setData(ArrayList<Loc> cdatas) {
    if (cdatas == null || cdatas.size() == 0) {
      return;
    }
    datas.clear();
    datas.addAll(cdatas);
    notifyDataSetChanged();
  }

  public void appendData(ArrayList<Loc> cdatas) {
    if (cdatas == null || cdatas.size() == 0) {
      return;
    }
    datas.addAll(cdatas);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return datas.size();
  }

  @Override
  public Object getItem(int position) {
    return datas.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(
          android.R.layout.simple_list_item_1, parent, false);
    }
    TextView tv = ((TextView) convertView);
    tv.setText(datas.get(position).getName());
    tv.setBackgroundColor(Color.WHITE);
    tv.setTextColor(Color.BLACK);
    return tv;
  }
}
