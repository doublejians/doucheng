package com.yaya.douban.tongcheng.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import com.yaya.douban.R;

/**
 * 所有activity的基类，负责处理共同的特性，后期扩展。。。
 * 
 * @author doublejian
 * 
 */
public class TCBaseActivity extends Activity {
  protected Dialog loadingDialog;
  protected boolean isCustomTitle = false;

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
}
