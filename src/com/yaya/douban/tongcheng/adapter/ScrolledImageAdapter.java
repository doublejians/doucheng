package com.yaya.douban.tongcheng.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yaya.douban.R;
import com.yaya.douban.common.utils.Tools;
import com.yaya.douban.tongcheng.types.Photo;
import com.yaya.douban.tongcheng.types.json.AlbumPhotoJsonType;

/**
 * 填充图片到横向可活动控件
 * 
 * @author doublejian
 * 
 */
public class ScrolledImageAdapter {
  private ArrayList<Photo> photos = new ArrayList<Photo>();
  private Context context;
  private static DisplayImageOptions options;
  private LinearLayout layout;// 图片容器
  private LayoutParams param;

  public ScrolledImageAdapter(Context tcontext, HorizontalScrollView view) {
    context = tcontext;
    if (options == null) {
      options = new DisplayImageOptions.Builder()
          .showStubImage(R.drawable.post_photo_default)
          .showImageForEmptyUri(R.drawable.post_photo_default)
          .showImageOnFail(R.drawable.post_photo_default).cacheInMemory(true)
          .cacheOnDisc(true).bitmapConfig(Bitmap.Config.ARGB_8888) // 设置图片的解码类型
          .build();
    }
    layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    int height = Tools.getPixelByDip(context, 148);
    int width = Tools.getPixelByDip(context, 98);
    int padding = Tools.getPixelByDip(context, 6);
    param = new LayoutParams(width, height);
    param.setMargins(padding, 0, padding, 0);
    param.gravity = Gravity.CENTER;
    view.addView(layout, -1, -1);
  }

  /**
   * 设置数据
   * 
   * @param tdata
   */
  public void setData(AlbumPhotoJsonType tdata) {
    for (Photo photo : tdata.getPhotos()) {
      photos.add(photo);
    }
    updateViews(true);
  }

  /**
   * 向滑动空间控件添加数据，用于向右滑动加载更多时
   * 
   * @param tdata
   */
  public void appendData(AlbumPhotoJsonType tdata) {
    if (tdata != null) {
      for (Photo photo : tdata.getPhotos()) {
        photos.add(photo);
      }
      updateViews(false);
    }
  }

  public int getCount() {
    return photos.size();
  }

  private void updateViews(boolean clearAll) {
    if (photos.size() == 0) {
      layout.removeAllViews();
      TextView tv = new TextView(context);
      tv.setText("暂无活动图片");
      tv.setGravity(Gravity.CENTER);
      layout.addView(tv, param);
      return;
    }
    if (clearAll) {
      layout.removeAllViews();
    }
    for (Photo photo : photos) {
      ImageView iv = new ImageView(context);
      iv.setScaleType(ScaleType.CENTER_CROP);
      layout.addView(iv, param);
      // 自动加载
      ImageLoader.getInstance().displayImage(photo.getImage(), iv, options);
    }
  }
}
