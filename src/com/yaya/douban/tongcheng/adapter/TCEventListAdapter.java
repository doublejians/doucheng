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
  private static DisplayImageOptions options;

  private ArrayList<TCEvent> datas = new ArrayList<TCEvent>();

  public TCEventListAdapter(Context tcontext) {
    context = tcontext;
  }

  public void setData(ArrayList<TCEvent> tdatas) {
    if (tdatas == null) {
      return;
    }
    datas.clear();
    datas.addAll(tdatas);
    notifyDataSetChanged();
  }

  public void appendData(ArrayList<TCEvent> tdatas) {
    if (tdatas == null) {
      return;
    }
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
    EventViewHolder holder;
    TCEvent event = datas.get(position);
    if (convertView == null
        || !(convertView.getTag() instanceof EventViewHolder)) {
      holder = new EventViewHolder();
      convertView = LayoutInflater.from(context).inflate(
          R.layout.item_for_eventlist, parent, false);
      holder.titleTv = (TextView) convertView.findViewById(R.id.event_title);
      holder.dateTv = (TextView) convertView.findViewById(R.id.startend_date);
      holder.addressTv = (TextView) convertView
          .findViewById(R.id.event_address);

      holder.typeTv = (TextView) convertView.findViewById(R.id.event_category);

      holder.endTv = (TextView) convertView.findViewById(R.id.event_endtxt);

      holder.wisherTv = (TextView) convertView
          .findViewById(R.id.event_wishercount);
      holder.ownerTv = (TextView) convertView.findViewById(R.id.event_owner);

      holder.participantTv = (TextView) convertView
          .findViewById(R.id.event_participant);
      holder.previewIv = (ImageView) convertView
          .findViewById(R.id.event_preimg);
    } else {
      holder = (EventViewHolder) convertView.getTag();
    }
    fillEventListViewHolder(holder, event);
    convertView.setTag(holder);
    return convertView;
  }

  /**
   * 为了填充活动详情更省事，直接写出一个方法
   * 
   * @param holder
   * @param event
   */
  public static void fillEventListViewHolder(EventViewHolder holder,
      TCEvent event) {
    if (options == null) {
      options = new DisplayImageOptions.Builder()
          .showStubImage(R.drawable.post_photo_default)
          .showImageForEmptyUri(R.drawable.post_photo_default)
          .showImageOnFail(R.drawable.post_photo_default).cacheInMemory(true)
          .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
          .build();
    }
    holder.titleTv.setText(event.getTitle());
    String startStr = Tools.getStartEndStr(event.getBegin_time());
    String endStr = Tools.getStartEndStr(event.getEnd_time());
    String[] startStrs = startStr.split(" ");
    String[] endStrs = endStr.split(" ");
    StringBuilder dateBuilder = new StringBuilder();
    dateBuilder.append(startStr + "-");
    if (startStrs[0].equals(endStrs[0])) {
      dateBuilder.append(endStrs[1]);
    } else {
      dateBuilder.append(endStr);
    }
    holder.dateTv.setText(dateBuilder.toString());
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
    // 自己管理加载以及回调
    ImageLoader.getInstance().displayImage(event.getImage(), holder.previewIv,
        options);
    // boolean isFree = !event.isHas_ticket();
    // holder.feeTv.setText(Html
    // .fromHtml(isFree ? "<big><font color='#ba142b'>免费</font><big>" : "收费"));
    holder.ownerTv.setText("主办方：" + event.getOwner().getName());
  }

  private static String getDayStrFromToday(String timeStr) {
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

  public static class EventViewHolder {
    public TextView titleTv, dateTv, addressTv, typeTv, endTv, wisherTv,
        participantTv, ownerTv;
    public ImageView previewIv;
  }
}
