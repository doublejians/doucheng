package com.yaya.douban.common.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import com.yaya.douban.common.utils.AppLog;

public abstract class BaseDataRequest implements Runnable {
  protected String url;
  private BaseDataParser parser;
  private NetworkCallBack callback;

  @Override
  public void run() {

    HttpURLConnection conn;
    BaseDataResponse dr = new BaseDataResponse();
    try {
      conn = (HttpURLConnection) new URL(url).openConnection();
      conn.setConnectTimeout(10000);

      conn.setRequestMethod("GET");
      parser = createParser();

      int rsCode = conn.getResponseCode();
      dr.setResultCode(rsCode);
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
        dr = parser.parser(data.toString());
        if (callback != null) {
          callback.onRequestCompleted(dr);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      if (callback != null) {

        dr.setResultCode(-100);
        callback.onRequestCompleted(dr);
      }
    }
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
