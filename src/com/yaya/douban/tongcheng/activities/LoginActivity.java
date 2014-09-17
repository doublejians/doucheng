package com.yaya.douban.tongcheng.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.http.BaseDataRequest.NetworkCallBack;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.common.utils.Tools;
import com.yaya.douban.tongcheng.requests.TCTokenRequest;
import com.yaya.douban.tongcheng.responses.TCTokenResponse;
import com.yaya.douban.tongcheng.types.Token;

/**
 * 完成OAuth2 认证
 * 
 * @author doublejian
 * 
 */
public class LoginActivity extends TCBaseActivity {
  private final static String URL_OAUTH2_CODE = "https://www.douban.com/service/auth2/auth";

  private String responseType = "code";// code token
  private String grantType = "authorization_code";// authorization_code 或者
                                                  // refresh_token
  private String tccode = "";
  private WebView webview;
  private MyWebViewClient client = new MyWebViewClient();
  private TCTokenRequest request;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_webview);
    webview = (WebView) findViewById(R.id.webView1);
    webview.getSettings().setSupportZoom(true);
    webview.getSettings().setSavePassword(false);
    webview.setWebViewClient(client);
    String url = appenParamForCodeUrl(URL_OAUTH2_CODE);
    AppLog.e("xxxxOauth", "onCreate url----" + url);
    webview.loadUrl(url);
  }

  private String appenParamForCodeUrl(String url) {
    StringBuilder builder = new StringBuilder();
    builder.append(url + "?");
    builder.append("client_id=" + AppContext.TC_API_KEY);
    builder.append("&redirect_uri=" + AppContext.OAUTH_REDIRECT_URI);
    builder.append("&response_type=" + responseType);
    builder.append("&scope=event_basic_r,event_basic_w,douban_basic_common");
    return builder.toString();
  }

  private void requestToken() {

    request = new TCTokenRequest();
    request.registNetworkCallback(new NetworkCallBack() {

      @Override
      public void onRequestCompleted(BaseDataResponse dr) {
        if (dr instanceof TCTokenResponse) {
          Token token = ((TCTokenResponse) dr).getToken();
          if (dr.getResultCode() == 200) {
            AppContext.getInstance().setToken(token);
            AppLog.e("xxxxOauth", "token--->" + token.getAccess_token());
            setResult(RESULT_OK);
            finish();
          } else {
            if (token != null && token.getCode() != 0) {
              if (!Tools.isEmpty(token.getMsg())) {
                Toast.makeText(LoginActivity.this, token.getMsg(),
                    Toast.LENGTH_SHORT).show();
              }
            } else {
              Toast.makeText(LoginActivity.this, "网络不给力", Toast.LENGTH_SHORT)
                  .show();
            }
          }
          hideProgeressDialog();
        }
      }
    });
    // 如果authorization_code为空，给出提示
    if (Tools.isEmpty(tccode)) {
      Toast.makeText(this, "授权出现问题，请重新输入", Toast.LENGTH_SHORT).show();
      return;
    }
    showProgressDialog();
    request.getToken(tccode, grantType);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (webview.canGoBack()) {
        webview.goBack();
      } else {
        finish();
      }
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      AppLog.e("xxxxOauth", "url--->" + url);
      if (!Tools.isEmpty(url) && url.startsWith(AppContext.OAUTH_REDIRECT_URI)) {
        if (url.contains("=")) {
          String code = url.split("=")[1];
          AppLog.e("xxxxOauth", "code--->" + code);
          tccode = code;
          requestToken();
        }
        return false;
      }
      view.loadUrl(url);
      return true;
    }
  }
}
