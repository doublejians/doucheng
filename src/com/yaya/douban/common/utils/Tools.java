package com.yaya.douban.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
