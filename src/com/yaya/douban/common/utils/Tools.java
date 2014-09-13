package com.yaya.douban.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;

/**
 * 各种工具方法
 * 
 * @author doublejian
 * 
 */
public class Tools {
  public static boolean isEmpty(CharSequence string) {
    return string == null || string.length() == 0;
  }

  public static Date getDateByStr(String dateStr) {
    Date result = null;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
        Locale.ENGLISH);
    try {
      result = format.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static int getPixelByDip(Context context, int dip) {
    return (int) (context.getResources().getDisplayMetrics().density * dip + 0.5f);
  }

  public static int getPixelByDip(Context context, float dip) {
    return (int) (context.getResources().getDisplayMetrics().density * dip + 0.5f);
  }

  // 通过pixel获得字体大小的sp值
  public static int getTextSpByPixel(Context context, int pixel) {
    return (int) (pixel / context.getResources().getDisplayMetrics().scaledDensity);
  }

  /**
   * 
   * @param dateStr
   * @return
   */
  public static String getStartEndStr(String dateStr) {
    Date date = getDateByStr(dateStr);
    Date today = new Date(System.currentTimeMillis());
    String formaterStr = "yyyy/MM/dd HH:mm";
    if (date.getYear() == today.getYear()) {
      formaterStr = "MM/dd HH:mm";
    }
    return new SimpleDateFormat(formaterStr, Locale.ENGLISH).format(date);
  }

  public static int daysOfTwo(Date fDate, Date oDate) {
    Calendar aCalendar = Calendar.getInstance();
    aCalendar.setTime(fDate);
    int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
    aCalendar.setTime(oDate);
    int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
    return day2 - day1;
  }

  public static String getDistanceDayStr(int distance) {
    if (distance == 0) {
      return "今天";
    } else if (distance == 1) {
      return "明天";
    } else if (distance == 2) {
      return "后天";
    }
    return distance + "";
  }
}
