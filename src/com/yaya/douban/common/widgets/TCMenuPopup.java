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
  private PopupWindow popwindow;
  private TextView anchorView;
  private ListView listview;
  private ArrayAdapter<String> adapter;
  private IMenuItemCallback callback;

  public TCMenuPopup(Context context, TextView anchor,
      ArrayList<String> strsToShow, IMenuItemCallback tcallback) {
    callback = tcallback;
    anchorView = anchor;
    popwindow = new PopupWindow(context);
    View coreView = LayoutInflater.from(context).inflate(
        R.layout.dropdown_list_layout, null);
    listview = (ListView) coreView.findViewById(R.id.menulist);
    adapter = new ArrayAdapter<String>(context,
        android.R.layout.simple_list_item_1, android.R.id.text1, strsToShow);
    listview.setAdapter(adapter);
    listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    listview.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
          long id) {
        popwindow.dismiss();
        callback.onItemSelected(position);
        AppLog.e("xxxxLoc", "onItemClick  " + position);
      }
    });
    popwindow.setWidth(anchor.getWidth());
    popwindow.setHeight(-2);
    popwindow.setContentView(coreView);
  }

  public void showAsDropDown() {
    popwindow.setOutsideTouchable(true);
    popwindow.setFocusable(true);
    popwindow.showAsDropDown(anchorView);
  }

  public interface IMenuItemCallback {
    void onItemSelected(int position);
  }
}
