package com.yaya.douban.common.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * 功能，下拉到底部时显示加载并且进行回调,后面完成
 * 
 * @author doublejian
 * 
 */
public class TCListViewEx extends ListView {
  private View emptyView;

  public TCListViewEx(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public void setEmptyView(View view) {
    emptyView = view;
  }
}
