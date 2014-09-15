package com.yaya.douban.common.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import android.os.Handler;
import android.os.Message;

import com.yaya.douban.common.utils.AppLog;

public abstract class BaseDataRequest implements Runnable {
  public static String[] REQUEST_METHOD = { "GET", "POST", "PUT", "DELETE" };

  public static final String URL_DEFAULT = "https://api.douban.com";
  // 各种路径
  public static final String PATH_LOCS_CITIES = "/v2/loc/list"; // 城市列表
  public static final String PATH_LOCS_DISTRICTS = "/v2/loc/%1$s/districts";// 城市的区
  public static final String PATH_EVENT_DETAIL = "/v2/event/%1$s";// 活动详细信息
  public static final String PATH_EVENT_LIST = "/v2/event/list";// 活动列表
  public static final String PATH_EVENT_PHOTOS = "/v2/event/%1$s/photos";// 活动图片

  // 请求的参数们。。。
  public static final String PARAM_START = "start"; // 开始元素
  public static final String PARAM_COUNT = "count"; // 想要请求元素个数
  public static final String PARAM_LOC = "loc"; // 城市
  public static final String PARAM_TYPE = "type"; // 活动类型
  public static final String PARAM_DAY_TYPE = "day_type"; // 时间类型
  public static final String PARAM_LOC_DISTRICT = "district"; // 区

  private static final int MESSAGE_REQUEST_COMPLETE = 1;

  protected String url = URL_DEFAULT;
  protected String path;
  protected Map<String, String> values = new HashMap<String, String>();// 各种参数集合

  protected int requestMethod = 0;// 请求方式
  protected int start;// 起始元素
  protected int count;// 返回结果的数量
  protected BaseDataResponse response = new BaseDataResponse();
  private BaseDataParser parser;// 解析器
  private NetworkCallBack callback;// 数据请求后的回调
  private Handler handler;

  public BaseDataRequest() {
    handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == MESSAGE_REQUEST_COMPLETE) {
          if (callback != null) {
            callback.onRequestCompleted(response);
          }
        }
      }
    };
  }

  @Override
  public void run() {

    HttpURLConnection conn;
    try {
      String realUrl = buildRealUrl();
      realUrl = realUrl + (realUrl.contains("?") ? "&" : "?")
          + "apikey=0339b495d888705009ad1dc1899950f0";

      AppLog.e("xxxUrl", "realUrl--->" + realUrl);
      conn = (HttpURLConnection) new URL(realUrl).openConnection();
      conn.setConnectTimeout(10000);

      conn.setRequestMethod(REQUEST_METHOD[requestMethod]);
      conn.setRequestProperty("Accept-Charset", "UTF-8");
      parser = createParser();

      int rsCode = conn.getResponseCode();
      response.setResultCode(rsCode);
      if (rsCode == 200) {
        int size = 0;
        StringBuffer data = new StringBuffer();
        char[] buffer = new char[1024];
        GZIPInputStream zipis = (GZIPInputStream) conn.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(zipis,
            "utf-8"));
        while ((size = br.read(buffer)) != -1) {
          data.append(buffer, 0, size);
        }

        AppLog.e("xxx", "data: " + data.toString());
        response = parser.parser(data.toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
      if (callback != null) {
        response.setResultCode(-100);
        callback.onRequestCompleted(response);
      }
    }
    Message msg = handler.obtainMessage();
    msg.what = MESSAGE_REQUEST_COMPLETE;
    msg.sendToTarget();
  }

  private String buildRealUrl() {
    StringBuilder builder = new StringBuilder();
    builder.append(url
        + ((url.endsWith("/") || path.startsWith("/")) ? "" : "/") + path);
    if (values.isEmpty() || requestMethod != 0) {
      return builder.toString();
    }
    if (requestMethod == 0) {
      builder.append("?");
      Set<String> keys = values.keySet();
      for (String key : keys) {
        builder.append(key + "=" + values.get(key) + "&");
      }
      return builder.substring(0, builder.length() - 1);
    }
    return builder.toString();
  }

  public void startRequest() {
    new Thread(this).start();
  }

  protected abstract BaseDataParser createParser();

  public void registNetworkCallback(NetworkCallBack nc) {
    callback = nc;
  }

  public static void unregistNetworkCallback(BaseDataRequest rq) {
    if (rq != null) {
      rq.registNetworkCallback(null);
    }
  }

  public interface NetworkCallBack {
    public void onRequestCompleted(BaseDataResponse dr);
  }
}
