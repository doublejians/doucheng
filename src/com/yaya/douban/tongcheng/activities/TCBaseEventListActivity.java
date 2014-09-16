package com.yaya.douban.tongcheng.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.common.widgets.TCListViewEx;
import com.yaya.douban.common.widgets.TCListViewEx.ITCListViewCallBack;
import com.yaya.douban.tongcheng.adapter.TCEventListAdapter;
import com.yaya.douban.tongcheng.requests.TCEventListRequest;
import com.yaya.douban.tongcheng.types.TCEvent;

/**
 * 负责显示列表，处理请求的类
 * 
 * @author doublejian
 * 
 */
public abstract class TCBaseEventListActivity extends TCBaseActivity implements
    ITCListViewCallBack {
  private TCListViewEx eventList;// 活动列表
  protected TCEventListAdapter adapter;// 活动适配器
  protected TCEventListRequest request;// 请求
  protected int currentStart = 0;// 当前起始元素
  protected String type = "all", daytype = "future", loc = "118159",
      district = "", key = "";// 对应请求的几个参数 活动类型、时间类型、城市ID、区ID、查询关键字
  protected boolean isAppend = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    eventList = (TCListViewEx) findViewById(R.id.eventlist);

    adapter = new TCEventListAdapter(this);
    eventList.registListCallBack(this);
    eventList.setAdapter(adapter);

    setupListEmptyView();
  }

  private void setupListEmptyView() {
    ViewGroup parent = ((ViewGroup) eventList.getParent());
    View emptyView = LayoutInflater.from(this).inflate(
        R.layout.empty_event_list, parent, false);
    parent.addView(emptyView);
    eventList.setEmptyView(emptyView);
  }

  @Override
  public final void onLoadMore() {
    isAppend = true;
    requestEvents();
  }

  @Override
  public final void onItemClick(AdapterView<?> parent, View view, int position,
      long id) {
    if (position > adapter.getCount() - 1) {
      return;
    }
    // 跳转到活动详情页，将选中的活动对象带到详情页
    TCEvent event = (TCEvent) adapter.getItem(position);
    AppContext.getInstance().setCurrentEvent(event);
    Intent intent = new Intent();
    intent.setClass(TCBaseEventListActivity.this, TCEventDetailActivity.class);
    TCBaseEventListActivity.this.startActivity(intent);
    AppLog.e("xxxxxxx",
        "onItemCLick---->" + position + "    " + event.getTitle());
  }

  /**
   * 显示列表底部加载进度条
   */
  protected final void showListProgress() {
    eventList.showFooterProgress();
  }

  /**
   * 隐藏列表底部加载进度条
   */
  protected final void hideListProgress() {
    eventList.hideFooterProgress();
  }

  /**
   * 请求数据，各个子类实现对应的请求
   */
  protected abstract void requestEvents();

  protected abstract int getLayoutId();

}
