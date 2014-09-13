package com.yaya.douban.tongcheng.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * 所有activity的基类，负责处理共同的特性，后期扩展。。。
 * 
 * @author doublejian
 * 
 */
public class TCBaseActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // 去除标题栏
    requestWindowFeature(Window.FEATURE_NO_TITLE);
  }
}
