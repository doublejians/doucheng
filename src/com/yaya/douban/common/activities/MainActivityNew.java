package com.yaya.douban.common.activities;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.yaya.douban.R;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.common.utils.IntentUtils;
import com.yaya.douban.tongcheng.activities.ITitleCallBackForMain;
import com.yaya.douban.tongcheng.activities.LocsChooseActivity;
import com.yaya.douban.tongcheng.activities.TCEventListActivity;
import com.yaya.douban.tongcheng.activities.TCEventMyActivity;
import com.yaya.douban.tongcheng.activities.WeeklyHotActivity;

@SuppressWarnings("deprecation")
public class MainActivityNew extends ActivityGroup implements
    View.OnClickListener {
  private static final int DIALOG_EXIT = 1;
  private static final int TAB_COUNT = 3;
  private static final int[] ID_ToggleButton = { R.id.btnModule1,
      R.id.btnModule2, R.id.btnModule3 };
  private static final Class<?>[] classes = { WeeklyHotActivity.class,
      TCEventListActivity.class, TCEventMyActivity.class };
  private ITitleCallBackForMain[] callbacks = new ITitleCallBackForMain[TAB_COUNT];
  private LinearLayout container = null;
  private AlertDialog exitDialog;
  private TextView titleTv, cityTv;
  private ImageButton backIb, refreshIb;
  private ToggleButton[] tabButtons = new ToggleButton[TAB_COUNT];
  private int currentTab = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.AppTheme);
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.activity_main_new);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
        R.layout.title_customer_default);
    titleTv = (TextView) findViewById(R.id.page_title);
    cityTv = (TextView) findViewById(R.id.page_title_2);
    backIb = (ImageButton) findViewById(R.id.left_arrow);
    refreshIb = (ImageButton) findViewById(R.id.refresh);
    cityTv.setOnClickListener(this);
    backIb.setOnClickListener(this);
    refreshIb.setOnClickListener(this);
    backIb.setVisibility(View.INVISIBLE);

    container = (LinearLayout) findViewById(R.id.containerBody);

    for (int i = 0; i < TAB_COUNT; i++) {
      tabButtons[i] = (ToggleButton) findViewById(ID_ToggleButton[i]);
      tabButtons[i].setOnClickListener(this);
      tabButtons[i].setTag(i);
    }
    AppContext.getInstance().setPageNeedRefresh(true);
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
      showDialog(DIALOG_EXIT);
      return true;
    }
    return super.dispatchKeyEvent(event);
  }

  @Override
  @Deprecated
  protected Dialog onCreateDialog(int id) {
    if (id == DIALOG_EXIT) {
      exitDialog = new AlertDialog.Builder(this).setTitle("退出")
          .setMessage("确定退出豆城？").setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
              IntentUtils.broadcastAppFinished();
              finish();
            }
          }).setNegativeButton("取消", null).setCancelable(true).create();
      return exitDialog;
    }
    return super.onCreateDialog(id);
  }

  @Override
  protected void onResume() {
    super.onResume();
    cityTv.setText(AppContext.getInstance().getCurrentLoc().getName());
    if (AppContext.getInstance().isPageNeedRefresh()) {
      tabButtons[currentTab].performClick();
      AppContext.getInstance().setPageNeedRefresh(false);
      AppLog.e("xxxxMain", "refresh ");
    }
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    boolean isTabs = false;
    for (int tid : ID_ToggleButton) {
      if (id == tid) {
        isTabs = true;
        break;
      }
    }
    if (isTabs) {
      int tag = -1;
      if (v.getTag() == null) {
        return;
      }
      tag = (Integer) v.getTag();
      currentTab = tag;
      for (int i = 0; i < tabButtons.length; i++) {
        if (tag != i) {
          tabButtons[i].setChecked(false);
        } else {
          tabButtons[i].setChecked(true);
        }
      }

      container.removeAllViews();
      String aid = "Module" + tag;
      container.addView(
          getLocalActivityManager().startActivity(
              aid,
              new Intent(MainActivityNew.this, classes[tag])
                  .addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT))
              .getDecorView(), -1, -1);
      updateTitleBar(getLocalActivityManager().getActivity(aid));
    } else if (id == R.id.left_arrow) {
      if (callbacks[currentTab] != null) {
        callbacks[currentTab].onLeftButtonClicked();
      }
    } else if (id == R.id.refresh) {
      if (callbacks[currentTab] != null) {
        callbacks[currentTab].onRightButtonClicked();
      }
    } else if (id == R.id.page_title_2) {
      Intent intent = new Intent(MainActivityNew.this, LocsChooseActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
    }
  }

  private void updateTitleBar(Activity activity) {
    if (activity instanceof ITitleCallBackForMain) {
      if (callbacks[currentTab] == null) {
        callbacks[currentTab] = (ITitleCallBackForMain) activity;
      }
      titleTv.setText(callbacks[currentTab].getTitleTxt());
      backIb
          .setVisibility(callbacks[currentTab].isLeftButtonShow() ? View.VISIBLE
              : View.GONE);
      Drawable drawable = callbacks[currentTab].getDrawableRightButton();
      if (drawable != null) {
        refreshIb.setVisibility(View.VISIBLE);
        refreshIb.setBackgroundDrawable(drawable);
      } else {
        refreshIb.setVisibility(View.GONE);
      }
    }
  }
}
