package com.yaya.douban.tongcheng.activities;

import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.common.utils.Tools;
import com.yaya.douban.tongcheng.requests.WeeklyHotListRequest;
import com.yaya.douban.tongcheng.responses.TCEventListResponse;
import com.yaya.douban.tongcheng.types.TCEvent;

public class WeeklyHotActivity extends TCBaseActivity implements
    OnClickListener, OnLongClickListener, ITitleCallBackForMain {
  private static final String TAG = "WeeklyHotActivity";
  private static DisplayImageOptions options;
  private WeeklyHotListRequest request;
  private int position = 0;
  private EventViewHolder holder;
  private ArrayList<TCEvent> datas = new ArrayList<TCEvent>();
  private String type = "all", daytype = "future", loc = "118159",
      district = "";// 对应请求的几个参数 活动类型、时间类型、城市ID、区ID

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weeklyhotlist);
    requestEvents();
  }

  private void requestEvents() {
    request = new WeeklyHotListRequest();
    AppLog.e(TAG, "WeeklyHotActivity: requestEvents1");
    request.registNetworkCallback(new NetworkCallBack() {

      @Override
      public void onRequestCompleted(BaseDataResponse dr) {
        if (dr instanceof TCEventListResponse) {
          if (dr.getResultCode() == 200) {
            TCEventListResponse result = (TCEventListResponse) dr;
            datas = result.getData();

            ShowView();// 显示所获取的热门活动
            AppLog.e(TAG, "onRequestCompleted");
          }
        }
      }
    });
    loc = AppContext.getInstance().getCurrentLoc().getId();
    AppLog.e(TAG, "WeeklyHotActivity: requestEvents2"+loc);
    request.getEvents(loc, district, type, daytype, 0, 10);
  }

  protected void ShowView() {
    // 显示所获取的热门活动

    TCEvent event = datas.get(0);
    holder = new EventViewHolder();

    holder.titleTv = (TextView) findViewById(R.id.event_title);
    holder.titleTv.setOnClickListener(this);
    holder.titleTv.setOnLongClickListener(this);

    holder.dateTv = (TextView) findViewById(R.id.startend_date);
    holder.dateTv.setOnClickListener(this);
    holder.dateTv.setOnLongClickListener(this);

    holder.addressTv = (TextView) findViewById(R.id.event_address);
    holder.addressTv.setOnClickListener(this);
    holder.addressTv.setOnLongClickListener(this);

    holder.typeTv = (TextView) findViewById(R.id.event_category);
    holder.typeTv.setOnClickListener(this);
    holder.typeTv.setOnLongClickListener(this);

    holder.endTv = (TextView) findViewById(R.id.event_endtxt);
    holder.endTv.setOnClickListener(this);
    holder.endTv.setOnLongClickListener(this);

    holder.wisherTv = (TextView) findViewById(R.id.event_wishercount);
    holder.wisherTv.setOnClickListener(this);
    holder.wisherTv.setOnLongClickListener(this);

    holder.ownerTv = (TextView) findViewById(R.id.event_owner);
    holder.ownerTv.setOnClickListener(this);
    holder.ownerTv.setOnLongClickListener(this);

    holder.participantTv = (TextView) findViewById(R.id.event_participant);
    holder.participantTv.setOnClickListener(this);
    holder.participantTv.setOnLongClickListener(this);

    holder.previewIv = (ImageView) findViewById(R.id.event_preimg);
    holder.previewIv.setOnClickListener(this);
    holder.previewIv.setOnLongClickListener(this);

    fillEventListViewHolder(holder, event);

  }

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

  @Override
  public void onClick(View arg0) {
    // 显示详细信息

    TCEvent event = datas.get(position);
    AppContext.getInstance().setCurrentEvent(event);
    Intent intent = new Intent();
    intent.setClass(WeeklyHotActivity.this, TCEventDetailActivity.class);
    this.startActivity(intent);

  }

  public static class EventViewHolder {
    public TextView titleTv, dateTv, addressTv, typeTv, endTv, wisherTv,
        participantTv, ownerTv;
    public ImageView previewIv;
  }

  @Override
  public boolean onLongClick(View arg0) {
    // TODO Auto-generated method stub

    if (position < 9) {
      position++;
    }

    TCEvent event = datas.get(position);
    AppLog.e(TAG, "onClick" + position);
    fillEventListViewHolder(holder, event);
    return true;

  }

  @Override
  public boolean isLeftButtonShow() {
    return false;
  }

  @Override
  public void onLeftButtonClicked() {
    finish();
  }

  @Override
  public void onRightButtonClicked() {

  }

  @Override
  public Drawable getDrawableRightButton() {
    return null;
  }

  @Override
  public String getTitleTxt() {
    return "一周热点";
  }
}
