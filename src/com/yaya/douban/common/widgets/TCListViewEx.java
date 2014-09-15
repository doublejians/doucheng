package com.yaya.douban.common.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yaya.douban.R;

/**
 * 功能，下拉到底部时显示加载并且进行回调,后面完成
 * 
 * @author doublejian
 * 
 */
public class TCListViewEx extends ListView {
  private Context context;
  private View emptyView;// 结果为空的时候显示
  private View loadingView;// 加载时底部显示
  private ITCListViewCallBack listener;

  public TCListViewEx(Context tcontext, AttributeSet attrs) {
    super(tcontext, attrs);
    context = tcontext;
    loadingView = LayoutInflater.from(tcontext).inflate(
        R.layout.footer_for_listview_loading, this, false);
    loadingView.setVisibility(View.GONE);
    addFooterView(loadingView);
    setOnScrollListener(new OnScrollListener() {

      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
        case OnScrollListener.SCROLL_STATE_IDLE:
          if (getLastVisiblePosition() == (getCount() - 1)) {
            if (listener != null) {
              listener.onLoadMore();
            }
          }
          break;
        }
      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem,
          int visibleItemCount, int totalItemCount) {

      }
    });
    setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
          long id) {
        if (listener != null) {
          listener.onItemClick(parent, view, position, id);
        }
      }
    });
  }

  public void setLoadingViewBgColor(int color) {
    loadingView.setBackgroundColor(color);
  }

  public void showFooterProgress() {
    if (indexOfChild(loadingView) < 0) {
      addFooterView(loadingView);
    }
    loadingView.setVisibility(View.VISIBLE);
  }

  public void hideFooterProgress() {
    if (indexOfChild(loadingView) < 0) {
      removeFooterView(loadingView);
    }
  }

  public void registListCallBack(ITCListViewCallBack lisen) {
    listener = lisen;
  }

  public void setupEmptyView(View empty) {
    emptyView = empty;
    setEmptyView(emptyView);
  }

  public interface ITCListViewCallBack {
    /**
     * 下拉倒底部加载更多
     */
    void onLoadMore();

    void onItemClick(AdapterView<?> parent, View view, int position, long id);
  }
}
