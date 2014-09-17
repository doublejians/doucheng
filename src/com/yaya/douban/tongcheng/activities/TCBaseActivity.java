package com.yaya.douban.tongcheng.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Window;

import com.yaya.douban.R;
import com.yaya.douban.common.utils.IntentUtils;

/**
 * 所有activity的基类，负责处理共同的特性，后期扩展。。。
 * 
 * @author doublejian
 * 
 */
public class TCBaseActivity extends Activity {
  protected Dialog loadingDialog;
  protected boolean isCustomTitle = false;
  private BroadcastReceiver exitReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!isCustomTitle) {
      requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    // 去除标题栏
    loadingDialog = new Dialog(TCBaseActivity.this);
    loadingDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    loadingDialog.getWindow().setBackgroundDrawableResource(
        R.drawable.shape_menu_bg);
    loadingDialog.setCancelable(false);
    loadingDialog.setContentView(R.layout.footer_for_listview_loading);
    IntentFilter exitFilter = new IntentFilter();
    exitFilter.addAction(IntentUtils.ITNENT_APPLICATION_EXITED);
    exitReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if (IntentUtils.ITNENT_APPLICATION_EXITED.equals(intent.getAction())) {
          finish();
        }
      }
    };
    registerReceiver(exitReceiver, exitFilter);
  }

  protected void showProgressDialog() {
    if (!loadingDialog.isShowing()) {
      loadingDialog.show();
    }
  }

  protected void hideProgeressDialog() {
    if (loadingDialog.isShowing()) {
      loadingDialog.dismiss();
    }
  }

  // @Override
  // public boolean onKeyDown(int keyCode, KeyEvent event) {
  // if (keyCode == KeyEvent.KEYCODE_BACK) {
  // finish();
  // return true;
  // }
  // return super.onKeyDown(keyCode, event);
  // }

  @Override
  protected void onDestroy() {
    unregisterReceiver(exitReceiver);
    super.onDestroy();
  }

}
