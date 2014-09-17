package com.yaya.douban.tongcheng.activities;

import android.graphics.drawable.Drawable;

public interface ITitleCallBackForMain {
  /**
   * 回退图标是否显示
   * 
   * @return
   */
  boolean isLeftButtonShow();

  /**
   * 左侧图标调用
   */
  void onLeftButtonClicked();

  /**
   * 右侧图标回调
   */
  void onRightButtonClicked();

  /**
   * 返回右侧的图片，如果没有，则隐藏图片
   * 
   * @return
   */
  Drawable getDrawableRightButton();

  /**
   * 获取标题
   * 
   * @return
   */
  String getTitleTxt();

}
