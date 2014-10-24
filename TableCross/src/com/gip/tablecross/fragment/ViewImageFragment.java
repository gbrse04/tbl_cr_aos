package com.gip.tablecross.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import com.androidquery.AQuery;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.photoview.PhotoView;
import com.gip.tablecross.widget.HackyViewPager;

public class ViewImageFragment extends BaseFragment {
	private HackyViewPager viewPager;
	private AQuery aq;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_view_image, container, false);
		initUI(view);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			initData();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	protected void initUI(View view) {
		viewPager = (HackyViewPager) view.findViewById(R.id.viewPager);
		aq = new AQuery(view);
	}

	public void initData() {
		viewPager.setAdapter(new SamplePagerAdapter());
		viewPager.setCurrentItem(getMainActivity().indexImage);
	}

	private class SamplePagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return getMainActivity().listImages.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			photoView.setImageBitmap(aq.getCachedImage(getMainActivity().listImages.get(position).getImageUrl()));

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}
}