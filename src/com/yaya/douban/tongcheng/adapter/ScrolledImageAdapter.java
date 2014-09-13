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

public class ScrolledImageAdapter {
  private AlbumPhotoJsonType data;
  private ArrayList<Photo> photos = new ArrayList<Photo>();
  private Context context;
  private static DisplayImageOptions options;
  private LinearLayout layout;
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
    view.addView(layout, -2, -1);
  }

  public void setData(AlbumPhotoJsonType tdata) {
    data = tdata;
    for (Photo photo : tdata.getPhotos()) {
      photos.add(photo);
    }
    updateViews(true);
  }

  public void appendData(AlbumPhotoJsonType tdata) {
    if (tdata != null) {
      for (Photo photo : tdata.getPhotos()) {
        photos.add(photo);
      }
      updateViews(false);
    }
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
      ImageLoader.getInstance().displayImage(photo.getImage(), iv, options);
    }
  }
}
