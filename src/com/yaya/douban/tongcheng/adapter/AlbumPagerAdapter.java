package com.yaya.douban.tongcheng.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yaya.douban.R;
import com.yaya.douban.common.utils.AppLog;
import com.yaya.douban.tongcheng.types.json.AlbumPhotoJsonType;

public class AlbumPagerAdapter extends PagerAdapter {
  private AlbumPhotoJsonType data;
  private Context context;
  private static DisplayImageOptions options;

  public AlbumPagerAdapter(Context tcontext) {
    context = tcontext;
    if (options == null) {
      options = new DisplayImageOptions.Builder()
          .showStubImage(R.drawable.post_photo_default)
          .showImageForEmptyUri(R.drawable.post_photo_default)
          .showImageOnFail(R.drawable.post_photo_default).cacheInMemory(true)
          .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
          .build();
    }
  }

  public void setData(AlbumPhotoJsonType tdata) {
    data = tdata;
    AppLog.e("xxxxxPhoto", "photo size " + getCount());
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return data == null ? 0 : data.getPhotos().length;
  }

  @Override
  public boolean isViewFromObject(View arg0, Object arg1) {
    return arg0 == arg1;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    ImageView iv = new ImageView(context);
    iv.setBackgroundResource(R.drawable.post_photo_default);
    container.addView(iv, 80, -1);
    ImageLoader.getInstance().displayImage(
        data.getPhotos()[position].getImage(), iv, options);
    return iv;
  }
}
