package com.gip.tablecross.fragment.search;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.adapter.CategoryAdapter;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Category;
import com.gip.tablecross.object.SimpleResponse;

public class FeatureSearchFragment extends BaseFragment {
	private ListView lsvCategory;
	private List<Category> listCategories;
	private CategoryAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_feature, container, false);
		initUI(view);
		initControl();
		return view;
	}

	private void initUI(View view) {
		lsvCategory = (ListView) view.findViewById(R.id.lsvCategory);
	}

	private void initControl() {
		listCategories = new ArrayList<Category>();
		adapter = new CategoryAdapter(getActivity(), listCategories);
		lsvCategory.setAdapter(adapter);
	}

	public void getData() {
		if (listCategories.size() == 0) {
			getBaseActivity().showLoading();
			GlobalValue.modelManager.getCategoryList("", -1, -1, new ModelManagerListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void onSuccess(Object object, SimpleResponse simpleResponse) {
					if (simpleResponse.isSuccess()) {
						listCategories.clear();
						listCategories.addAll((List<Category>) object);
						adapter.notifyDataSetChanged();
					}
					getBaseActivity().hideLoading();
				}

				@Override
				public void onError(String message) {
					getBaseActivity().hideLoading();
				}
			});
		}
	}
}