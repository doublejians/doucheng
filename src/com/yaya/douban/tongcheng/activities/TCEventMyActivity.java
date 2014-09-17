package com.yaya.douban.tongcheng.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

import com.yaya.douban.R;
import com.yaya.douban.common.activities.AppContext;
import com.yaya.douban.common.utils.Tools;
import com.yaya.douban.tongcheng.requests.TCEventListRequest;

/**
 * 处理
 * 
 * @author doublejian
 * 
 */
public class TCEventMyActivity extends TCBaseEventListActivity implements
    OnClickListener {
  private static final int DIALOG_LOGIN_PROMPT = 1;
  private static final int[] IDS_TAB = { R.id.tg_my_created, R.id.tg_my_wished,
      R.id.tg_my_participant };// togglebutton的ID，对应TCEventListRequest.TYPE_My_xxx的值
  private int reqType = TCEventListRequest.TYPE_MY_CREATED; // 类型，默认为我创建的
  private ToggleButton[] tabs = new ToggleButton[IDS_TAB.length];
  private AlertDialog loginPromptDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    int index = -1;
    for (int id : IDS_TAB) {
      tabs[++index] = (ToggleButton) findViewById(id);
      tabs[index].setTag(index);
      tabs[index].setOnClickListener(this);
      tabs[index].setChecked(false);
    }
    tabs[reqType].performClick();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      tabs[reqType].performClick();
    }
  }

  private void updateToggleButtons() {
    for (int i = 0; i < tabs.length; i++) {
      tabs[i].setChecked(i == reqType ? true : false);
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_event_my;
  }

  @Override
  public void onClick(View v) {
    if (v.getTag() instanceof Integer) {
      reqType = (Integer) v.getTag();
      updateToggleButtons();
      requestEvents();
    }
  }

  @Override
  protected void requestMethod() {
    request.getMyEvents(reqType, currentStart, 10);
  }

  @Override
  protected boolean checkAndInitRequestParam() {
    if (AppContext.getInstance().getToken() == null
        || Tools.isEmpty(AppContext.getInstance().getToken().getAccess_token())) {
      showDialog(DIALOG_LOGIN_PROMPT);
      return false;
    }
    if (reqType < 0 || reqType >= tabs.length) {
      return false;
    }
    return true;
  }

  @Override
  public String getTitleTxt() {
    return "与我相关";
  }

  @Override
  @Deprecated
  protected Dialog onCreateDialog(int id) {
    if (id == DIALOG_LOGIN_PROMPT) {
      loginPromptDialog = new AlertDialog.Builder(this).setTitle("授权提示")
          .setMessage("您还没有进行登录授权，无法查看您的页面，是否进入授权页面？")
          .setPositiveButton("现在进入", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
              Intent intent = new Intent(TCEventMyActivity.this,
                  LoginActivity.class);
              startActivityForResult(intent, 111);
            }
          }).setNegativeButton("不了，谢谢", null).setCancelable(true).create();
      return loginPromptDialog;
    }

    return super.onCreateDialog(id);
  }
}
