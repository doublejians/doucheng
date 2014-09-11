package com.yaya.douban.tongcheng.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yaya.douban.R;
import com.yaya.douban.common.utils.Tools;
import com.yaya.douban.tongcheng.types.TCEvent;

public class TCEventListAdapter extends BaseAdapter {
  private Context context;
  private DisplayImageOptions options;
  private ImageLoader imgLoader;

  private ArrayList<TCEvent> datas = new ArrayList<TCEvent>();

  public TCEventListAdapter(Context tcontext) {
    context = tcontext;
    options = new DisplayImageOptions.Builder()
        .showStubImage(R.drawable.post_photo_default)
        .showImageForEmptyUri(R.drawable.post_photo_default)
        .showImageOnFail(R.drawable.post_photo_default).cacheInMemory(true)
        .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
        .build();
    imgLoader = ImageLoader.getInstance();
  }

  public void setData(ArrayList<TCEvent> tdatas) {
    if (tdatas == null) {
      return;
    }
    datas.clear();
    datas.addAll(tdatas);
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
    ViewHolder holder;
    TCEvent event = datas.get(position);
    if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
      holder = new ViewHolder();
      convertView = LayoutInflater.from(context).inflate(
          R.layout.item_for_eventlist, parent, false);
      holder.titleTv = (TextView) convertView.findViewById(R.id.event_title);
      holder.dateTv = (TextView) convertView.findViewById(R.id.startend_date);
      holder.addressTv = (TextView) convertView
          .findViewById(R.id.event_address);

      holder.typeTv = (TextView) convertView.findViewById(R.id.event_category);

      holder.endTv = (TextView) convertView.findViewById(R.id.event_endtxt);

      holder.feeTv = (TextView) convertView.findViewById(R.id.event_fee);
      holder.wisherTv = (TextView) convertView
          .findViewById(R.id.event_wishercount);

      holder.participantTv = (TextView) convertView
          .findViewById(R.id.event_participant);
      holder.previewIv = (ImageView) convertView
          .findViewById(R.id.event_preimg);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    holder.titleTv.setText(event.getTitle());
    holder.dateTv.setText(event.getBegin_time() + "-" + event.getEnd_time());
    holder.addressTv.setText(event.getAddress());
    String subCategory = event.getSubcategory_name();
    holder.typeTv.setText(event.getCategory_name()
        + (!Tools.isEmpty(subCategory) ? ("-" + subCategory) : ""));
    holder.endTv
        .setText(Html.fromHtml(getDayStrFromToday(event.getEnd_time())));

    holder.wisherTv.setText(Html.fromHtml("<big><font color='#ba142b'>"
        + event.getWisher_count() + "</font></big>感兴趣"));
    holder.participantTv.setText(Html.fromHtml("<big><font color='#ba142b'>"
        + event.getParticipant_count() + "</font></big>参加"));
    imgLoader.displayImage(event.getImage(), holder.previewIv, options);
    boolean isFree = !event.isHas_ticket();
    holder.feeTv.setText(Html
        .fromHtml(isFree ? "<big><font color='#ba142b'>免费</font><big>" : "收费"));
    convertView.setTag(holder);
    return convertView;
  }

  private String getDayStrFromToday(String timeStr) {
    Date endDate = Tools.getDateByStr(timeStr);
    Date today = new Date(System.currentTimeMillis());
    int distance = Tools.daysOfTwo(today, endDate);
    String disStr = Tools.getDistanceDayStr(distance);
    StringBuilder builder = new StringBuilder();
    builder.append("<big><font color='#ba142b'>" + disStr + "</font></big>");
    if (!disStr.endsWith("天")) {
      builder.append("天后");
    }
    builder.append("结束");
    return builder.toString();
  }

  class ViewHolder {
    TextView titleTv, dateTv, addressTv, typeTv, endTv, wisherTv,
        participantTv, feeTv;
    ImageView previewIv;
  }
}