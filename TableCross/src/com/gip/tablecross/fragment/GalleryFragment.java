package com.gip.tablecross.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.adapter.ImageAdapter;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Image;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.NetworkUtil;

public class GalleryFragment extends BaseFragment {
	private GridView grvGallery;
	private ImageAdapter imageAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gallery, container, false);
		initUI(view);
		initControl(view);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
		}
	}

	private void initUI(View view) {
		grvGallery = (GridView) view.findViewById(R.id.grvGallery);
		getMainActivity().listImages = new ArrayList<Image>();
		imageAdapter = new ImageAdapter(getActivity(), getMainActivity().listImages);
		grvGallery.setAdapter(imageAdapter);
	}

	private void initControl(View view) {
		grvGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				getMainActivity().indexImage = position;
				goToFragment(MainActivity.VIEW_IMAGE);
			}
		});
	}

	public void getImages() {
		if (!NetworkUtil.isNetworkAvailable(this)) {
			showDialogNoNetwork();
		} else {
			getBaseActivity().showLoading();
			getMainActivity().listImages.clear();
			imageAdapter.notifyDataSetChanged();
			GlobalValue.modelManager.getImages(getMainActivity().currentRestaurant.getRestaurantId(),
					new ModelManagerListener() {
						@SuppressWarnings("unchecked")
						@Override
						public void onSuccess(Object object, SimpleResponse simpleResponse) {
							getMainActivity().listImages.addAll((List<Image>) object);
							if (getMainActivity().listImages.size() > 0) {
								imageAdapter.notifyDataSetChanged();
							} else {
								showToast(simpleResponse.getErrorMess());
							}
							getBaseActivity().hideLoading();
						}

						@Override
						public void onError(String message) {
							showToast(message);
							getBaseActivity().hideLoading();
						}
					});
		}
	}
}