package com.yaya.douban.tongcheng.adapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yaya.douban.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class WeeklyHotAdapter extends BaseAdapter {
	private Context context;
	private DisplayImageOptions options;
	private ImageLoader imgLoader;

	public WeeklyHotAdapter(Context tcontext) {

		context = tcontext;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.post_photo_default)
				.showImageForEmptyUri(R.drawable.post_photo_default)
				.showImageOnFail(R.drawable.post_photo_default)
				.cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
				.build();
		imgLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
