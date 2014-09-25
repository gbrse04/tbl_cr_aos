package com.bamboo.tablecross.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bamboo.tablecross.BaseActivity;
import com.bamboo.tablecross.R;
import com.bamboo.tablecross.common.GlobalValue;

public class MainActivity extends BaseActivity implements OnClickListener {
	public static final int TAB_NOTIFICATION = 0;
	public static final int TAB_SEARCH = 1;
	public static final int TAB_SHARE = 2;
	public static final int TAB_USER = 3;

	public static final int SEARCH_CONDITION = 4;
	public static final int SEARCH_LOCATION = 5;
	public static final int SEARCH_MAP = 6;
	public static final int SEARCH_HISTORY = 7;

	public static final int SETTING = 8;
	public static final int HOME = 9;
	public static final int FOOD_DETAIL = 10;

	public static final String HISTORY_SEARCH = "0";
	public static final String LOCATION_SEARCH = "1";
	public static final String CONDITION_SEARCH = "2";

	private TextView lblHeader, lblHeaderLeft, lblHeaderRight;
	private View layoutTabNotification, layoutTabSearch, layoutTabShare, layoutTabUser;
	private ImageView imgSetting;

	private FragmentManager fm;
	private Fragment[] arrayFragments;
	public int currentFragment;
	public int previousFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();
		initControl();
		initFragment();
		setTabSelected(TAB_NOTIFICATION);
		showFragment(HOME);
		setData();
	}

	private void initUI() {
		lblHeader = (TextView) findViewById(R.id.lblHeader);
		lblHeaderLeft = (TextView) findViewById(R.id.lblHeaderLeft);
		lblHeaderRight = (TextView) findViewById(R.id.lblHeaderRight);
		layoutTabNotification = findViewById(R.id.layoutTabNotification);
		layoutTabSearch = findViewById(R.id.layoutTabSearch);
		layoutTabShare = findViewById(R.id.layoutTabShare);
		layoutTabUser = findViewById(R.id.layoutTabUser);
		imgSetting = (ImageView) findViewById(R.id.imgSetting);
	}

	private void initControl() {
		layoutTabNotification.setOnClickListener(this);
		layoutTabSearch.setOnClickListener(this);
		layoutTabShare.setOnClickListener(this);
		layoutTabUser.setOnClickListener(this);
		imgSetting.setOnClickListener(this);
		lblHeaderLeft.setOnClickListener(this);
	}

	private void setData() {
		lblHeaderRight.setVisibility(View.GONE);
		lblHeaderLeft.setVisibility(View.VISIBLE);
		try {
			lblHeaderLeft.setText(GlobalValue.area.getAreaName());
		} catch (Exception e) {
		}
	}

	public void setTabSelected(int tabSelected) {
		showFragment(tabSelected);
		lblHeaderLeft.setVisibility(View.GONE);
		switch (tabSelected) {
		case TAB_NOTIFICATION:
			layoutTabNotification.setBackgroundResource(R.drawable.tab_selected);
			layoutTabSearch.setBackgroundColor(Color.TRANSPARENT);
			layoutTabShare.setBackgroundColor(Color.TRANSPARENT);
			layoutTabUser.setBackgroundColor(Color.TRANSPARENT);
			break;

		case TAB_SEARCH:
			layoutTabNotification.setBackgroundColor(Color.TRANSPARENT);
			layoutTabSearch.setBackgroundResource(R.drawable.tab_selected);
			layoutTabShare.setBackgroundColor(Color.TRANSPARENT);
			layoutTabUser.setBackgroundColor(Color.TRANSPARENT);
			break;

		case TAB_SHARE:
			layoutTabNotification.setBackgroundColor(Color.TRANSPARENT);
			layoutTabSearch.setBackgroundColor(Color.TRANSPARENT);
			layoutTabShare.setBackgroundResource(R.drawable.tab_selected);
			layoutTabUser.setBackgroundColor(Color.TRANSPARENT);
			break;

		default:
			layoutTabNotification.setBackgroundColor(Color.TRANSPARENT);
			layoutTabSearch.setBackgroundColor(Color.TRANSPARENT);
			layoutTabShare.setBackgroundColor(Color.TRANSPARENT);
			layoutTabUser.setBackgroundResource(R.drawable.tab_selected);
			break;
		}
	}

	private void initFragment() {
		fm = getFragmentManager();
		arrayFragments = new Fragment[11];
		arrayFragments[0] = fm.findFragmentById(R.id.fragmentNotification);
		arrayFragments[1] = fm.findFragmentById(R.id.fragmentSearch);
		arrayFragments[2] = fm.findFragmentById(R.id.fragmentShare);
		arrayFragments[3] = fm.findFragmentById(R.id.fragmentUser);
		arrayFragments[4] = fm.findFragmentById(R.id.fragmentSearchCondition);
		arrayFragments[5] = fm.findFragmentById(R.id.fragmentSearchLocation);
		arrayFragments[6] = fm.findFragmentById(R.id.fragmentSearchMap);
		arrayFragments[7] = fm.findFragmentById(R.id.fragmentSearchHistory);
		arrayFragments[8] = fm.findFragmentById(R.id.fragmentSetting);
		arrayFragments[9] = fm.findFragmentById(R.id.fragmentHome);
		arrayFragments[10] = fm.findFragmentById(R.id.fragmentFoodDetail);

		FragmentTransaction transaction = fm.beginTransaction();
		for (Fragment fragment : arrayFragments) {
			transaction.hide(fragment);
		}
		transaction.commit();
	}

	private void showFragment(int fragmentIndex) {
		currentFragment = fragmentIndex;
		FragmentTransaction transaction = fm.beginTransaction();
		for (Fragment fragment : arrayFragments) {
			transaction.hide(fragment);
		}
		transaction.show(arrayFragments[fragmentIndex]);
		transaction.commit();

		if (fragmentIndex != FOOD_DETAIL) {
			imgSetting.setVisibility(View.VISIBLE);
			imgSetting.setImageResource(R.drawable.ic_setting);
		}
	}

	public void gotoFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		// transaction.setCustomAnimations(
		// R.anim.fragment_out_right,R.anim.fragment_in_left);
		transaction.show(arrayFragments[fragment]);
		transaction.hide(arrayFragments[currentFragment]);
		transaction.commit();

		switch (currentFragment) {
		case SEARCH_CONDITION:
			lblHeaderLeft.setVisibility(View.VISIBLE);
			lblHeaderLeft.setText("< " + getString(R.string.conditionSearch));
			break;

		case SEARCH_LOCATION:
			lblHeaderLeft.setVisibility(View.VISIBLE);
			lblHeaderLeft.setText("< " + getString(R.string.locationSearch));
			break;

		case SEARCH_MAP:
			lblHeaderLeft.setVisibility(View.VISIBLE);
			lblHeaderLeft.setText("< " + getString(R.string.mapSearch));
			break;

		case SEARCH_HISTORY:
			lblHeaderLeft.setVisibility(View.VISIBLE);
			lblHeaderLeft.setText("< " + getString(R.string.historySearch));
			break;

		default:
			break;
		}

		switch (fragment) {
		case SEARCH_CONDITION:
			lblHeaderLeft.setVisibility(View.VISIBLE);
			lblHeaderLeft.setText("< " + getString(R.string.search));
			lblHeader.setText(R.string.conditionSearch);
			break;

		case SEARCH_LOCATION:
			lblHeaderLeft.setVisibility(View.VISIBLE);
			lblHeaderLeft.setText("< " + getString(R.string.search));
			lblHeader.setText(R.string.locationSearch);
			break;

		case SEARCH_MAP:
			lblHeaderLeft.setVisibility(View.VISIBLE);
			lblHeaderLeft.setText("< " + getString(R.string.search));
			lblHeader.setText(R.string.mapSearch);
			break;

		case SEARCH_HISTORY:
			lblHeaderLeft.setVisibility(View.VISIBLE);
			lblHeaderLeft.setText("< " + getString(R.string.search));
			lblHeader.setText(R.string.historySearch);
			break;

		case FOOD_DETAIL:
			lblHeader.setText(R.string.restaurantDetail);
			imgSetting.setImageResource(R.drawable.ic_share);
			break;

		case SETTING:
			lblHeaderLeft.setVisibility(View.GONE);
			break;

		default:
			break;
		}

		if (fragment == SETTING) {
			imgSetting.setVisibility(View.GONE);
		} else if (fragment == FOOD_DETAIL) {
			imgSetting.setVisibility(View.VISIBLE);
			imgSetting.setImageResource(R.drawable.ic_share);
		} else {
			imgSetting.setVisibility(View.VISIBLE);
			imgSetting.setImageResource(R.drawable.ic_setting);
		}
		previousFragment = currentFragment;
		currentFragment = fragment;
	}

	public void backFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		// transaction.setCustomAnimations(R.anim.fragment_in_left,
		// R.anim.fragment_out_left);
		transaction.show(arrayFragments[fragment]);
		transaction.hide(arrayFragments[currentFragment]);
		transaction.commit();

		switch (fragment) {
		case TAB_SEARCH:
			lblHeaderLeft.setVisibility(View.GONE);
			lblHeader.setText(R.string.search);
			break;

		default:
			break;
		}

		if (fragment != FOOD_DETAIL) {
			imgSetting.setVisibility(View.VISIBLE);
			imgSetting.setImageResource(R.drawable.ic_setting);
		}
		currentFragment = fragment;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layoutTabNotification:
			onClickTabNotification();
			break;

		case R.id.layoutTabSearch:
			onClickTabSearch();
			break;

		case R.id.layoutTabShare:
			onClickTabShare();
			break;

		case R.id.layoutTabUser:
			onClickTabUser();
			break;

		case R.id.imgSetting:
			onClickSetting();
			break;

		case R.id.lblHeaderLeft:
			onClickHeaderLeft();
			break;
		}
	}

	private void onClickTabNotification() {
		setTabSelected(TAB_NOTIFICATION);
		lblHeader.setText(R.string.notification);
	}

	private void onClickTabSearch() {
		setTabSelected(TAB_SEARCH);
		lblHeader.setText(R.string.search);
	}

	private void onClickTabShare() {
		setTabSelected(TAB_SHARE);
		lblHeader.setText(R.string.share);
	}

	private void onClickTabUser() {
		setTabSelected(TAB_USER);
		lblHeader.setText(R.string.myPage);
	}

	private void onClickSetting() {
		if (currentFragment == FOOD_DETAIL) {
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
		} else if (currentFragment != SETTING) {
			gotoFragment(SETTING);
			lblHeader.setText(R.string.setting);
		}
	}

	private void onClickHeaderLeft() {
		switch (currentFragment) {
		case SEARCH_CONDITION:
		case SEARCH_LOCATION:
		case SEARCH_MAP:
		case SEARCH_HISTORY:
			backFragment(TAB_SEARCH);
			break;

		case FOOD_DETAIL:
			backFragment(previousFragment);
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		if (currentFragment < 4) {
			super.onBackPressed();
		} else if (currentFragment < 8) {
			backFragment(TAB_SEARCH);
		} else if (currentFragment == FOOD_DETAIL) {
			backFragment(previousFragment);
		}
	}
}
