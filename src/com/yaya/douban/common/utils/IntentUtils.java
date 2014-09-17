package com.yaya.douban.common.utils;

import android.content.Intent;

import com.yaya.douban.common.activities.AppContext;

public class IntentUtils {
  public static final String INTENT_DISTICTS_WEB_RESULT = "com.doutongcheng.change.disticts";
  public static final String ITNENT_APPLICATION_EXITED = "com.doutongcheng.appexit";

  public static void broadcastDistictsChange() {
    Intent intent = new Intent();
    intent.setAction(IntentUtils.INTENT_DISTICTS_WEB_RESULT);
    AppContext.getInstance().getApplicationContext().sendBroadcast(intent);
  }

  public static void broadcastAppFinished() {
    Intent intent = new Intent();
    intent.setAction(IntentUtils.ITNENT_APPLICATION_EXITED);
    AppContext.getInstance().getApplicationContext().sendBroadcast(intent);
  }
}
