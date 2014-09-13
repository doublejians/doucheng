package com.yaya.douban.common.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import com.yaya.douban.common.utils.AppLog;

public abstract class BaseDataRequest implements Runnable {
  public static String[] REQUEST_METHOD = { "GET", "POST", "PUT", "DELETE" };

  public static final String URL_DEFAULT = "https://api.douban.com";
  public static final String PATH_LOCS_CITIES = "/v2/loc/list";
  public static final String PATH_LOCS_DISTRICTS = "/v2/loc/%1$s/districts";
  public static final String PATH_EVENT_DETAIL = "/v2/event/%1$s";
  public static final String PATH_EVENT_LIST = "/v2/event/list";
  public static final String PATH_EVENT_PHOTOS = "/v2/event/%1$s/photos";

  public static final String PARAM_START = "start";
  public static final String PARAM_COUNT = "count";
  public static final String PARAM_LOC = "loc";
  public static final String PARAM_TYPE = "type";
  public static final String PARAM_DAY_TYPE = "day_type";
  public static final String PARAM_LOC_DISTRICT = "district";

  protected String url = URL_DEFAULT;
  protected String path;
  protected Map<String, String> values = new HashMap<String, String>();

  protected int requestMethod = 0;// 请求方式
  protected int start;// 起始元素
  protected int count;// 返回结果的数量
  protected BaseDataResponse response = new BaseDataResponse();
  private BaseDataParser parser;
  private NetworkCallBack callback;

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
        if (callback != null) {
          callback.onRequestCompleted(response);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      if (callback != null) {

        response.setResultCode(-100);
        callback.onRequestCompleted(response);
      }
    }
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
    new Thread(this).run();
  }

  protected abstract BaseDataParser createParser();

  public void registNetworkCallback(NetworkCallBack nc) {
    callback = nc;
  }

  public interface NetworkCallBack {
    public void onRequestCompleted(BaseDataResponse dr);
  }
}
