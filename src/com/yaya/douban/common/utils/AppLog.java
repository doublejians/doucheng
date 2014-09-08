package com.yaya.douban.common.utils;

import android.util.Log;

public class AppLog {
  private final static boolean DEBUG = true;

  public static void e(String tag, String msg) {
    if (Tools.isEmpty(msg)) {
      return;
    }
    if (DEBUG) {
      Log.e(tag, msg);
    }
  }

  public static void e(String tag, String msg, Throwable t) {
    if (DEBUG) {
      Log.e(tag, msg, t);
    }
  }

  public static void v(String tag, String msg) {
    if (Tools.isEmpty(msg)) {
      return;
    }
    if (DEBUG) {
      Log.v(tag, msg);
    }
  }

  public static void v(String tag, String msg, Throwable t) {
    if (DEBUG) {
      Log.v(tag, msg, t);
    }
  }

  public static void w(String tag, String msg) {
    if (Tools.isEmpty(msg)) {
      return;
    }
    if (DEBUG) {
      Log.w(tag, msg);
    }
  }

  public static void w(String tag, String msg, Throwable t) {
    if (DEBUG) {
      Log.w(tag, msg, t);
    }
  }

  public static void i(String tag, String msg) {
    if (Tools.isEmpty(msg)) {
      return;
    }
    if (DEBUG) {
      Log.i(tag, msg);
    }
  }

  public static void i(String tag, String msg, Throwable t) {
    if (DEBUG) {
      Log.i(tag, msg, t);
    }
  }

  public static void d(String tag, String msg) {
    if (Tools.isEmpty(msg)) {
      return;
    }
    if (DEBUG) {
      Log.d(tag, msg);
    }
  }

  public static void d(String tag, String msg, Throwable t) {
    if (DEBUG) {
      Log.d(tag, msg, t);
    }
  }
}
