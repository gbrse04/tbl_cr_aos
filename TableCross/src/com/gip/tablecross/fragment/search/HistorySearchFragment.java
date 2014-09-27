package com.gip.tablecross.fragment.search;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.gip.tablecross.R;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.adapter.RestaurantAdapter;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.object.SimpleResponse;

public class HistorySearchFragment extends BaseFragment {
	private ListView lsvRestaurant;
	private List<Restaurant> listRestaurants;
	private RestaurantAdapter adapter;
	private View btnSearch;
	private EditText txtKeyword;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_history, container, false);
		initUI(view);
		initControl();
		return view;
	}

	private void initUI(View view) {
		lsvRestaurant = (ListView) view.findViewById(R.id.lsvRestaurant);
		txtKeyword = (EditText) view.findViewById(R.id.txtKeyword);
		btnSearch = view.findViewById(R.id.btnSearch);
	}

	private void initControl() {
		listRestaurants = new ArrayList<Restaurant>();
		adapter = new RestaurantAdapter(getActivity(), listRestaurants);
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

	private void search() {
		String keyword = txtKeyword.getText().toString();
		GlobalValue.modelManager.searchRestaurant(MainActivity.HISTORY_SEARCH, keyword, 0, -1,
				new ModelManagerListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(Object object, SimpleResponse simpleResponse) {
						if (simpleResponse.getSuccess() == "true") {
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
					}

					@Override
					public void onError(String message) {
					}
				});
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txtKeyword.getWindowToken(), 0);
	}
}
