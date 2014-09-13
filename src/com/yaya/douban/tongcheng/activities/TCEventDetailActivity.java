package com.yaya.douban.tongcheng.activities;

import java.io.File;
import java.net.URISyntaxException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.tongcheng.adapter.ScrolledImageAdapter;
import com.yaya.douban.tongcheng.adapter.TCEventListAdapter;
import com.yaya.douban.tongcheng.adapter.TCEventListAdapter.EventViewHolder;
import com.yaya.douban.tongcheng.requests.TCEventPhotoListRequest;
import com.yaya.douban.tongcheng.responses.TCEventPhotoListResponse;
import com.yaya.douban.tongcheng.types.TCEvent;

public class TCEventDetailActivity extends TCBaseActivity implements
    OnClickListener {
  private TextView descTv, photoNumTv;
  private TCEvent event;
  private EventViewHolder holder;
  private TCEventPhotoListRequest request;
  private HorizontalScrollView photosContainer;
  private ScrolledImageAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_eventdetail);
    event = AppContext.getInstance().getCurrentEvent();
    if (event == null) {
      finish();
      return;
    }

    holder = new EventViewHolder();
    holder.titleTv = (TextView) findViewById(R.id.event_title);
    holder.dateTv = (TextView) findViewById(R.id.startend_date);
    holder.addressTv = (TextView) findViewById(R.id.event_address);
    holder.typeTv = (TextView) findViewById(R.id.event_category);
    holder.endTv = (TextView) findViewById(R.id.event_endtxt);
    holder.wisherTv = (TextView) findViewById(R.id.event_wishercount);
    holder.ownerTv = (TextView) findViewById(R.id.event_owner);
    holder.participantTv = (TextView) findViewById(R.id.event_participant);
    holder.previewIv = (ImageView) findViewById(R.id.event_preimg);

    descTv = (TextView) findViewById(R.id.event_desc);
    photoNumTv = (TextView) findViewById(R.id.event_photo_num);
    photosContainer = (HorizontalScrollView) findViewById(R.id.photos_container);
    TCEventListAdapter.fillEventListViewHolder(holder, event);
    adapter = new ScrolledImageAdapter(this, photosContainer);
    descTv.setText(event.getContent());
    holder.addressTv.setOnClickListener(this);

    holder.titleTv.post(new Runnable() {

      @Override
      public void run() {
        requestAlbum();
      }
    });
  }

  private void requestAlbum() {
    request = new TCEventPhotoListRequest();
    request.registNetworkCallback(new NetworkCallBack() {

      @Override
      public void onRequestCompleted(BaseDataResponse dr) {
        if (dr instanceof TCEventPhotoListResponse) {
          if (dr.getResultCode() == 200) {
            adapter.setData(((TCEventPhotoListResponse) dr).getData());
            String txt = "活动图片(%1$s)";
            photoNumTv.setText(String.format(txt, dr.getTotal()));
          }
        }
      }
    });
    request.getCurrentEventPhotos();
  }

  @Override
  public void onClick(View v) {
    try {
      Intent intent = Intent
          .getIntent("intent://map/marker?location="
              + event.getGeo().replace(" ", ",")
              + "&title=活动位置&content="
              + event.getAddress()
              + "&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
      if (isInstallByread("com.baidu.BaiduMap")) {
        startActivity(intent); // 启动调用
      } else {
        Toast.makeText(this, "未安装百度地图,无法显示具体位置", Toast.LENGTH_SHORT).show();
      }
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  private boolean isInstallByread(String packageName) {
    return new File("/data/data/" + packageName).exists();
  }
}
