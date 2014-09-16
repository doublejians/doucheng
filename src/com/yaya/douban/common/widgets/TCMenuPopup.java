package com.yaya.douban.common.widgets;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yaya.douban.R;
import com.yaya.douban.common.utils.AppLog;

/**
 * 下拉菜单
 * 
 * @author doublejian
 * 
 */
public class TCMenuPopup {
  private Context context;
  protected PopupWindow popwindow;
  protected TextView anchorView;
  protected View loadingView;
  protected ListView listview;
  protected ArrayAdapter<String> adapter;
  protected IMenuItemCallback callback;
  private LayoutInflater inflater;

  public TCMenuPopup(Context tcontext, TextView anchor,
      IMenuItemCallback tcallback) {
    context = tcontext;
    callback = tcallback;
    anchorView = anchor;
    inflater = LayoutInflater.from(context);
    popwindow = new PopupWindow(context);
    View coreView = inflater.inflate(R.layout.dropdown_list_layout, null);
    listview = (ListView) coreView.findViewById(R.id.menulist);
    loadingView = coreView.findViewById(R.id.loading);
    listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    listview.setBackgroundResource(R.drawable.shape_menu_bg);
    listview.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
          long id) {
        popwindow.dismiss();
        callback.onItemSelected(position);
        AppLog.e("xxxxLoc", "onItemClick  " + position);
      }
    });
    listview.setVisibility(View.INVISIBLE);
    loadingView.setVisibility(View.VISIBLE);
    popwindow.setWidth(anchor.getWidth());
    popwindow.setHeight(-2);
    popwindow.setBackgroundDrawable(context.getResources().getDrawable(
        R.drawable.shape_menu_bg));
    popwindow.setContentView(coreView);
  }

  public void setShownStrs(ArrayList<String> strsToShow) {
    adapter = new ArrayAdapter<String>(context, R.layout.item_for_tcmenu,
        R.id.menu_text, strsToShow);
    listview.setAdapter(adapter);
    listview.setVisibility(View.VISIBLE);
    loadingView.setVisibility(View.GONE);
    popwindow.setWidth(anchorView.getWidth());
    popwindow.setHeight(-2);
  }

  public void showAsDropDown() {
    popwindow.setOutsideTouchable(true);
    popwindow.setFocusable(true);
    popwindow.showAsDropDown(anchorView);
  }

  public boolean isPopShown() {
    return popwindow.isShowing();
  }

  public void dismissPop() {
    popwindow.dismiss();
  }

  public interface IMenuItemCallback {
    void onItemSelected(int position);
  }
}
