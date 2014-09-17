package com.yaya.douban.common.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import android.os.Handler;
import android.os.Message;

import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.common.utils.Tools;

public abstract class BaseDataRequest implements Runnable {
  // 请求方式相关
  public static final String[] REQUEST_METHODS = { "GET", "POST", "PUT",
      "DELETE" };
  public static final int REQUEST_GET = 0;
  public static final int REQUEST_POST = 1;
  public static final int REQUEST_PUT = 2;
  public static final int REQUEST_DELETE = 3;

  public static final String URL_DEFAULT = "https://api.douban.com";
  // 各种路径
  public static final String PATH_LOCS_CITIES = "/v2/loc/list"; // 城市列表
  public static final String PATH_LOCS_DISTRICTS = "/v2/loc/%1$s/districts";// 城市的区
  public static final String PATH_EVENT_DETAIL = "/v2/event/%1$s";// 活动详细信息
  public static final String PATH_EVENT_LIST = "/v2/event/list";// 活动列表
  public static final String PATH_EVENT_PHOTOS = "/v2/event/%1$s/photos";// 活动图片
  public static final String PATH_EVENT_SEARCH = "/v2/event/search";// 搜索活动
  public static final String PATH_EVENT_USER_PARTICIPATED = "/v2/event/user_participated/%1$s";// 用户参加的
  public static final String PATH_EVENT_USER_WISHED = "/v2/event/user_wished/%1$s";// 用户感兴趣的
  public static final String PATH_EVENT_USER_CREATED = "/v2/event/user_created/%1$s";// 用户创建的

  // 请求的参数们。。。
  public static final String PARAM_START = "start"; // 开始元素
  public static final String PARAM_COUNT = "count"; // 想要请求元素个数
  public static final String PARAM_LOC = "loc"; // 城市
  public static final String PARAM_TYPE = "type"; // 活动类型
  public static final String PARAM_DAY_TYPE = "day_type"; // 时间类型
  public static final String PARAM_LOC_DISTRICT = "district"; // 区
  public static final String PARAM_EVENT_KEY = "q"; // 查询时间的关键词

  // token请求相关
  public static final String PARAM_CLIENT_ID = "client_id"; //
  public static final String PARAM_CLIENT_SECRET = "client_secret"; //
  public static final String PARAM_REDIRECT_URI = "redirect_uri";
  public static final String PARAM_GRANT_TYPE = "grant_type";
  public static final String PARAM_CODE = "code";
  public static final String PARAM_REFRESH_TOKEN = "refresh_token";
  public static final String PARAM_SCOPE = "scope";
  public static final String PARAM_RESPONSE_TYPE = "response_type";
  public static final String PARAM_STATE = "state";

  private static final int MESSAGE_REQUEST_COMPLETE = 1;

  protected String url = URL_DEFAULT;
  protected String path;
  protected Map<String, String> values = new HashMap<String, String>();// 各种参数集合

  protected int requestMethod = REQUEST_GET;// 请求方式，默认get
  protected boolean valuesAppendInUrl = true;
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
      realUrl = realUrl + (realUrl.contains("?") ? "&" : "?") + "apikey="
          + AppContext.TC_API_KEY;// 0339b495d888705009ad1dc1899950f0

      AppLog.e("xxxUrl", "realUrl--->" + realUrl);
      conn = (HttpURLConnection) new URL(realUrl).openConnection();
      conn.setConnectTimeout(10000);

      conn.setRequestMethod(REQUEST_METHODS[requestMethod]);
      conn.setRequestProperty("Accept-Charset", "UTF-8");

      if (requestMethod == REQUEST_POST && valuesAppendInUrl) {
        // Post 请求不能使用缓存
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        String paramStr = getValuesStr();
        if (!Tools.isEmpty(paramStr)) {
          DataOutputStream out = new DataOutputStream(conn.getOutputStream());
          out.write(paramStr.getBytes("UTF-8"));
          out.flush();
          out.close();
        }
      }
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
    if (requestMethod == REQUEST_GET || valuesAppendInUrl) {
      String params = getValuesStr();
      if (!Tools.isEmpty(params)) {
        builder.append("?" + params);
      }
    }
    return builder.toString();
  }

  private String getValuesStr() {
    StringBuilder builder = new StringBuilder();
    Set<String> keys = values.keySet();
    for (String key : keys) {
      builder.append(key + "=" + values.get(key) + "&");
    }
    return builder.substring(0, builder.length() - 1);
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
