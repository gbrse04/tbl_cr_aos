package com.gip.tablecross.fragment.search;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.adapter.CategoryAdapter;
import com.gip.tablecross.adapter.RestaurantAdapter;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Category;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.Logger;
import com.gip.tablecross.util.NetworkUtil;

public class CategorySearchFragment extends BaseFragment {
	private ListView lsv;
	private List<Category> listCategories;

	private List<Restaurant> listRestaurants;
	private int categoryId;
	public String categoryName;
	public boolean categoryIsChild;

	public static CategorySearchFragment newInstance(Category category) {
		CategorySearchFragment instance = new CategorySearchFragment();
		Bundle b = new Bundle();
		b.putInt("CATEGORY_ID", category.getId());
		b.putString("CATEGORY_NAME", category.getName());
		b.putBoolean("CATEGORY_CHILD", category.isChild());
		instance.setArguments(b);
		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_feature, container, false);
		try {
			categoryId = getArguments().getInt("CATEGORY_ID");
		} catch (Exception e) {
			categoryId = -1;
		}
		try {
			categoryName = getArguments().getString("CATEGORY_NAME");
		} catch (Exception e) {
			categoryName = "";
		}
		try {
			categoryIsChild = getArguments().getBoolean("CATEGORY_CHILD");
		} catch (Exception e) {
			categoryIsChild = false;
		}
		initUI(view);
		initControl();
		if (categoryId > -1) {
			getData();
		}
		Logger.d("", "fragment - categoryId: " + categoryId);
		return view;
	}

	private void initUI(View view) {
		lsv = (ListView) view.findViewById(R.id.lsvCategory);
	}

	private void initControl() {
		lsv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (categoryIsChild || categoryId == -1) {
					Category category = listCategories.get(position);
					getMainActivity().addNewCategorySearchFragment(category);
					getMainActivity().goLastFragment();
					getMainActivity().lblHeader.setText(category.getName());
				} else {
					getMainActivity().currentRestaurant = listRestaurants.get(position);
					goToFragment(MainActivity.RESTAURANT_DETAIL);
					getMainActivity().setHeader(true, getString(R.string.back), false, R.string.share);
				}
			}
		});
	}

	public void getData() {
		if (categoryIsChild || categoryId == -1) {
			getMainActivity().setHeader(true, getString(R.string.back), false, R.string.close);
			getCategory();
		} else {
			getMainActivity().setHeader(true, getString(R.string.back), false, 0);
			searchRestaurant();
		}
	}

	private void getCategory() {
		if (!NetworkUtil.isNetworkAvailable(this)) {
			showDialogNoNetwork();
		} else {
			getBaseActivity().showLoading();
			GlobalValue.modelManager.getCategoryList(categoryId, -1, -1, new ModelManagerListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void onSuccess(Object object, SimpleResponse simpleResponse) {
					if (simpleResponse.isSuccess()) {
						listCategories = (List<Category>) object;
						CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), listCategories);
						lsv.setAdapter(categoryAdapter);
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

	private void searchRestaurant() {
		if (!NetworkUtil.isNetworkAvailable(this)) {
			showDialogNoNetwork();
		} else {
			getBaseActivity().showLoading();
			GlobalValue.modelManager.searchRestaurant(categoryId, new ModelManagerListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void onSuccess(Object object, SimpleResponse simpleResponse) {
					if (simpleResponse.isSuccess()) {
						listRestaurants = (List<Restaurant>) object;
						RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getActivity(), listRestaurants,
								false);
						lsv.setAdapter(restaurantAdapter);
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

	public void quickSearch() {
		Category category = new Category();
		category.setId(categoryId);
		category.setName(categoryName);
		category.setChild(false);
		getMainActivity().addNewCategorySearchFragment(category);
		getMainActivity().goLastFragment();
		getMainActivity().lblHeader.setText(category.getName());
		getMainActivity().setHeader(true, getString(R.string.back), false, 0);
	}
}