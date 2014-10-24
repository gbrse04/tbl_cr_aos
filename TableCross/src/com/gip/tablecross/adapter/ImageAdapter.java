package com.gip.tablecross.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.androidquery.AQuery;
import com.gip.tablecross.R;
import com.gip.tablecross.lazyloader.ImageLoader;
import com.gip.tablecross.object.Image;
import com.gip.tablecross.widget.ScaleImageView;

public class ImageAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Image> listImages;
	private ImageLoader mLoader;
	private AQuery listAq;

	public ImageAdapter(Activity context, List<Image> listImages) {
		this.listImages = listImages;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLoader = new ImageLoader(context);
		listAq = new AQuery(context);
	}

	@Override
	public int getCount() {
		return listImages.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_image, null);
		}
		Image item = listImages.get(position);
		if (item != null) {
			ScaleImageView imgImageItem = (ScaleImageView) convertView.findViewById(R.id.imgImageItem);
			mLoader.displayImage(item.getImageUrl(), imgImageItem);
			listAq.cache(item.getImageUrl(), 0);
		}
		return convertView;
	}
}
