package com.gip.tablecross.fragment.search;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.gip.tablecross.R;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.adapter.RestaurantAdapter;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.NetworkUtil;
import com.gip.tablecross.widget.AutoBgButton;

public class LocationSearchFragment extends BaseFragment implements OnClickListener {
	private static final int TAB_DISTANCE_1 = 0;
	private static final int TAB_DISTANCE_2 = 1;
	private static final int TAB_DISTANCE_3 = 2;
	private AutoBgButton btnDistance1, btnDistance2, btnDistance3;
	private ListView lsvRestaurant;
	private List<Restaurant> listRestaurants;
	private RestaurantAdapter adapter;
	private View btnSearch
	// ,
	// lblTextBeforeSearch,
	// layoutChooseDistance
	;
	private EditText txtKeyword;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_location, container, false);
		initUI(view);
		initControl();
		return view;
	}

	private void initUI(View view) {
		lsvRestaurant = (ListView) view.findViewById(R.id.lsvRestaurant);
		txtKeyword = (EditText) view.findViewById(R.id.txtKeyword);
		btnSearch = view.findViewById(R.id.btnSearch);
		// lblTextBeforeSearch = view.findViewById(R.id.lblTextBeforeSearch);
		// layoutChooseDistance = view.findViewById(R.id.layoutChooseDistance);

		btnDistance1 = (AutoBgButton) view.findViewById(R.id.btnDistance1);
		btnDistance2 = (AutoBgButton) view.findViewById(R.id.btnDistance2);
		btnDistance3 = (AutoBgButton) view.findViewById(R.id.btnDistance3);
	}

	private void initControl() {
		btnDistance1.setOnClickListener(this);
		btnDistance2.setOnClickListener(this);
		btnDistance3.setOnClickListener(this);
		listRestaurants = new ArrayList<Restaurant>();
		adapter = new RestaurantAdapter(getActivity(), listRestaurants, false);
		lsvRestaurant.setAdapter(adapter);

		lsvRestaurant.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				goToFragment(MainActivity.RESTAURANT_DETAIL);
			}
		});

		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				search();
			}
		});

		txtKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					search();
					return true;
				}
				return false;
			}
		});
	}

	private void setSelectTabDistance(int tab) {
		switch (tab) {
		case TAB_DISTANCE_1:
			btnDistance1.setBackgroundResource(R.drawable.tab_distance_left_selected);
			btnDistance2.setBackgroundResource(R.drawable.tab_distance_center);
			btnDistance3.setBackgroundResource(R.drawable.tab_distance_right);

			btnDistance1.setTextColor(Color.WHITE);
			btnDistance2.setTextColor(Color.BLACK);
			btnDistance3.setTextColor(Color.BLACK);
			break;

		case TAB_DISTANCE_2:
			btnDistance1.setBackgroundResource(R.drawable.tab_distance_left);
			btnDistance2.setBackgroundResource(R.drawable.tab_distance_center_selected);
			btnDistance3.setBackgroundResource(R.drawable.tab_distance_right);

			btnDistance1.setTextColor(Color.BLACK);
			btnDistance2.setTextColor(Color.WHITE);
			btnDistance3.setTextColor(Color.BLACK);
			break;

		default:
			btnDistance1.setBackgroundResource(R.drawable.tab_distance_left);
			btnDistance2.setBackgroundResource(R.drawable.tab_distance_center);
			btnDistance3.setBackgroundResource(R.drawable.tab_distance_right_selected);

			btnDistance1.setTextColor(Color.BLACK);
			btnDistance2.setTextColor(Color.BLACK);
			btnDistance3.setTextColor(Color.WHITE);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDistance1:
			onClickDistance1();
			break;

		case R.id.btnDistance2:
			onClickDistance2();
			break;

		case R.id.btnDistance3:
			onClickDistance3();
			break;

		}
	}

	private void onClickDistance1() {
		setSelectTabDistance(TAB_DISTANCE_1);
	}

	private void onClickDistance2() {
		setSelectTabDistance(TAB_DISTANCE_2);
	}

	private void onClickDistance3() {
		setSelectTabDistance(TAB_DISTANCE_3);
	}

	// public void beforeSearch() {
	// lblTextBeforeSearch.setVisibility(View.VISIBLE);
	// layoutChooseDistance.setVisibility(View.GONE);
	// }

	private void search() {
		if (!NetworkUtil.isNetworkAvailable(this)) {
			showDialogNoNetwork();
		} else {
			getBaseActivity().showLoading();
			String keyword = txtKeyword.getText().toString();
			GlobalValue.modelManager.searchRestaurant(MainActivity.LOCATION_SEARCH, keyword, 0, -1,
					new ModelManagerListener() {
						@SuppressWarnings("unchecked")
						@Override
						public void onSuccess(Object object, SimpleResponse simpleResponse) {
							if (simpleResponse.isSuccess()) {
								listRestaurants.clear();
								listRestaurants.addAll((List<Restaurant>) object);
								if (listRestaurants.size() == 0) {
									showToast("No result");
								} else {
									adapter.notifyDataSetChanged();
								}
							} else {
								showToast(simpleResponse.getErrorMess());
							}
							hideKeyboard();
							getMainActivity().hideLoading();
							// lblTextBeforeSearch.setVisibility(View.GONE);
							// layoutChooseDistance.setVisibility(View.VISIBLE);
							setSelectTabDistance(TAB_DISTANCE_1);
						}

						@Override
						public void onError(String message) {
							getMainActivity().hideLoading();
						}
					});
		}
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txtKeyword.getWindowToken(), 0);
	}
}
