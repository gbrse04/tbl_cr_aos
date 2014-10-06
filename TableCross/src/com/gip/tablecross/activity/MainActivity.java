package com.gip.tablecross.activity;

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

import com.gip.tablecross.BaseActivity;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.facebook.Facebook;
import com.gip.tablecross.fragment.HomeFragment;
import com.gip.tablecross.fragment.MyPagesFragment;
import com.gip.tablecross.fragment.NotificationDetailFragment;
import com.gip.tablecross.fragment.RestaurantDetailFragment;
import com.gip.tablecross.fragment.SettingFragment;
import com.gip.tablecross.fragment.ShareFragment;
import com.gip.tablecross.fragment.search.ConditionSearchFragment;
import com.gip.tablecross.listener.DialogListener;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Notification;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.object.User;
import com.gip.tablecross.util.Logger;

public class MainActivity extends BaseActivity implements OnClickListener {
	public static final int CODE_CHOOSE_REGION = 100;

	public static final int TAB_NOTIFICATION = 0;
	public static final int TAB_SEARCH = 1;
	public static final int TAB_SHARE = 2;
	public static final int TAB_MY_PAGE = 3;

	public static final int SEARCH_CONDITION = 4;
	public static final int SEARCH_LOCATION = 5;
	public static final int SEARCH_HISTORY = 6;
	public static final int SEARCH_FEATURE = 7;

	public static final int SETTING = 8;
	public static final int HOME = 9;
	public static final int RESTAURANT_DETAIL = 10;
	public static final int RESTAURANT_MAP = 11;
	public static final int NOTIFICATION_DETAIL = 12;

	public static final String HISTORY_SEARCH = "0";
	public static final String LOCATION_SEARCH = "1";
	public static final String CONDITION_SEARCH = "2";
	public static final String CATEGORY_SEARCH = "3";

	private TextView lblHeader, lblHeaderLeft, lblHeaderRight;
	private View imgSetting;
	private View layoutTabNotification, layoutTabSearch, layoutTabShare, layoutTabUser;

	private FragmentManager fm;
	public Fragment[] arrayFragments;
	public int currentFragment;
	public int previousFragment;
	public int currentSearch;

	public User user;
	public boolean isLoginedMode;
	public String accessToken;

	public Restaurant currentRestaurant;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();
		initControl();
		initFragment();

		try {
			user = getIntent().getExtras().getParcelable("user_login");
		} catch (Exception e) {
		}

		try {
			accessToken = getIntent().getExtras().getString("access_token");
		} catch (Exception e) {
			accessToken = "";
		}

		if (user == null) {
			isLoginedMode = false;
			setTabSelected(TAB_SEARCH);
		} else {
			isLoginedMode = true;
			showFragment(HOME);
			((HomeFragment) arrayFragments[HOME]).setUserPoint();
		}
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
		lblHeaderRight.setOnClickListener(this);
	}

	public void setHeader(boolean isBack, String left, boolean isSetting, int idString) {
		if (isBack) {
			lblHeaderLeft.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_forma, 0, 0, 0);
		} else {
			lblHeaderLeft.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		}

		lblHeaderLeft.setText(left);

		if (isSetting) {
			imgSetting.setVisibility(View.VISIBLE);
			lblHeaderRight.setVisibility(View.GONE);
		} else {
			imgSetting.setVisibility(View.GONE);
			if (idString == 0) {
				lblHeaderRight.setVisibility(View.GONE);
			} else {
				lblHeaderRight.setVisibility(View.VISIBLE);
				lblHeaderRight.setText(idString);
			}
		}
	}

	public void getUserInfo() {
		showLoading();
		GlobalValue.modelManager.getUserInfo(new ModelManagerListener() {
			@Override
			public void onSuccess(Object object, SimpleResponse simpleResponse) {
				user = (User) object;
				setUserInfo();
				hideLoading();
				showToast(simpleResponse.getErrorMess());
			}

			@Override
			public void onError(String message) {
				showToast(message);
				hideLoading();
			}
		});
	}

	private void setUserInfo() {
		((SettingFragment) arrayFragments[SETTING]).setDataUser();
		((MyPagesFragment) arrayFragments[TAB_MY_PAGE]).setDataUser();
	}

	public void setTabSelected(int tabSelected) {
		showFragment(tabSelected);
		switch (tabSelected) {
		case TAB_NOTIFICATION:
			layoutTabNotification.setBackgroundResource(R.drawable.tab_selected);
			layoutTabSearch.setBackgroundColor(Color.TRANSPARENT);
			layoutTabShare.setBackgroundColor(Color.TRANSPARENT);
			layoutTabUser.setBackgroundColor(Color.TRANSPARENT);
			lblHeader.setText(R.string.notification);
			break;

		case TAB_SEARCH:
			layoutTabNotification.setBackgroundColor(Color.TRANSPARENT);
			layoutTabSearch.setBackgroundResource(R.drawable.tab_selected);
			layoutTabShare.setBackgroundColor(Color.TRANSPARENT);
			layoutTabUser.setBackgroundColor(Color.TRANSPARENT);
			lblHeader.setText(R.string.search);
			break;

		case TAB_SHARE:
			layoutTabNotification.setBackgroundColor(Color.TRANSPARENT);
			layoutTabSearch.setBackgroundColor(Color.TRANSPARENT);
			layoutTabShare.setBackgroundResource(R.drawable.tab_selected);
			layoutTabUser.setBackgroundColor(Color.TRANSPARENT);
			lblHeader.setText(R.string.share);
			break;

		default:
			layoutTabNotification.setBackgroundColor(Color.TRANSPARENT);
			layoutTabSearch.setBackgroundColor(Color.TRANSPARENT);
			layoutTabShare.setBackgroundColor(Color.TRANSPARENT);
			layoutTabUser.setBackgroundResource(R.drawable.tab_selected);
			lblHeader.setText(R.string.myPage);
			break;
		}
	}

	private void initFragment() {
		fm = getFragmentManager();
		arrayFragments = new Fragment[13];
		arrayFragments[0] = fm.findFragmentById(R.id.fragmentNotification);
		arrayFragments[1] = fm.findFragmentById(R.id.fragmentSearch);
		arrayFragments[2] = fm.findFragmentById(R.id.fragmentShare);
		arrayFragments[3] = fm.findFragmentById(R.id.fragmentUser);
		arrayFragments[4] = fm.findFragmentById(R.id.fragmentSearchCondition);
		arrayFragments[5] = fm.findFragmentById(R.id.fragmentSearchLocation);
		arrayFragments[6] = fm.findFragmentById(R.id.fragmentSearchHistory);
		arrayFragments[7] = fm.findFragmentById(R.id.fragmentSearchFeature);
		arrayFragments[8] = fm.findFragmentById(R.id.fragmentSetting);
		arrayFragments[9] = fm.findFragmentById(R.id.fragmentHome);
		arrayFragments[10] = fm.findFragmentById(R.id.fragmentRestaurantDetail);
		arrayFragments[11] = fm.findFragmentById(R.id.fragmentRestaurantMap);
		arrayFragments[12] = fm.findFragmentById(R.id.fragmentNotificationDetail);

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

		switch (fragmentIndex) {
		case TAB_NOTIFICATION:
		case TAB_SEARCH:
		case TAB_SHARE:
		case TAB_MY_PAGE:
			if (isLoginedMode) {
				setHeader(false, GlobalValue.area.getAreaName(), true, 0);
			} else {
				setHeader(false, GlobalValue.area.getAreaName(), false, R.string.login);
			}
			break;

		case RESTAURANT_DETAIL:
			imgSetting.setVisibility(View.VISIBLE);
			break;

		case HOME:
			lblHeader.setText(R.string.home);
			setHeader(false, GlobalValue.area.getAreaName(), true, 0);
			break;

		default:
			break;
		}
	}

	public void gotoFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		// transaction.setCustomAnimations(
		// R.anim.fragment_out_right,R.anim.fragment_in_left);
		transaction.show(arrayFragments[fragment]);
		transaction.hide(arrayFragments[currentFragment]);
		transaction.commit();

		switch (fragment) {
		case TAB_NOTIFICATION:
			setHeader(false, GlobalValue.area.getAreaName(), true, 0);
			break;

		case NOTIFICATION_DETAIL:
			lblHeader.setText("");
			setHeader(true, getString(R.string.notification), false, 0);
			break;

		case SEARCH_CONDITION:
			currentSearch = SEARCH_CONDITION;
			setHeader(true, getString(R.string.search), true, 0);
			lblHeader.setText(R.string.conditionSearch);
			((ConditionSearchFragment) arrayFragments[SEARCH_CONDITION]).startSearch();
			break;

		case SEARCH_LOCATION:
			currentSearch = SEARCH_LOCATION;
			setHeader(true, getString(R.string.search), true, 0);
			lblHeader.setText(R.string.locationSearch);
			break;

		case SEARCH_HISTORY:
			currentSearch = SEARCH_HISTORY;
			setHeader(true, getString(R.string.search), true, 0);
			lblHeader.setText(R.string.historySearch);
			break;

		case SEARCH_FEATURE:
			currentSearch = SEARCH_FEATURE;
			setHeader(true, getString(R.string.search), true, 0);
			lblHeader.setText(R.string.featureSearch);
			break;

		case RESTAURANT_DETAIL: {
			lblHeader.setText(R.string.restaurantDetail);
			((RestaurantDetailFragment) arrayFragments[RESTAURANT_DETAIL]).setCurrentRestaurant();
			switch (currentFragment) {
			case SEARCH_CONDITION:
				setHeader(true, getString(R.string.conditionSearch), false, R.string.share);
				break;

			case SEARCH_LOCATION:
				setHeader(true, getString(R.string.locationSearch), false, R.string.share);
				break;

			case SEARCH_HISTORY:
				setHeader(true, getString(R.string.historySearch), false, R.string.share);
				break;

			case SEARCH_FEATURE:
				setHeader(true, getString(R.string.featureSearch), false, R.string.share);
				break;

			default:
				break;
			}
		}
			break;

		case RESTAURANT_MAP:
			lblHeader.setText(R.string.map);
			setHeader(true, getString(R.string.restaurantDetail), false, R.string.share);
			break;

		case SETTING:
			lblHeader.setText(R.string.setting);
			switch (currentFragment) {
			case HOME:
				setHeader(true, getString(R.string.home), false, 0);
				break;

			case TAB_NOTIFICATION:
				setHeader(true, getString(R.string.notification), false, 0);
				break;

			case TAB_SEARCH:
				setHeader(true, getString(R.string.search), false, 0);
				break;

			case TAB_SHARE:
				setHeader(true, getString(R.string.share), false, 0);
				break;

			case TAB_MY_PAGE:
				setHeader(true, getString(R.string.myPage), false, 0);
				break;

			case SEARCH_CONDITION:
				setHeader(true, getString(R.string.conditionSearch), false, 0);
				break;

			case SEARCH_LOCATION:
				setHeader(true, getString(R.string.locationSearch), false, 0);
				break;

			case SEARCH_HISTORY:
				setHeader(true, getString(R.string.historySearch), false, 0);
				break;

			case SEARCH_FEATURE:
				setHeader(true, getString(R.string.featureSearch), false, 0);
				break;

			default:
				setHeader(true, "", false, 0);
				break;
			}
			break;
		}

		previousFragment = currentFragment;
		currentFragment = fragment;

		Logger.d("", "fragment previous: " + previousFragment);
		Logger.d("", "fragment current_: " + currentFragment);
	}

	public void setNotificationDetail(Notification notification) {
		((NotificationDetailFragment) arrayFragments[NOTIFICATION_DETAIL]).setData(notification);
	}

	public void backFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		// transaction.setCustomAnimations(R.anim.fragment_in_left,
		// R.anim.fragment_out_left);
		transaction.show(arrayFragments[fragment]);
		transaction.hide(arrayFragments[currentFragment]);
		transaction.commit();

		switch (fragment) {
		case HOME:
			lblHeader.setText(R.string.home);
			setHeader(false, GlobalValue.area.getAreaName(), true, 0);
			break;

		case TAB_NOTIFICATION:
			lblHeader.setText(R.string.notification);
			setHeader(false, GlobalValue.area.getAreaName(), true, 0);
			break;

		case TAB_SEARCH:
			lblHeader.setText(R.string.search);
			setHeader(false, GlobalValue.area.getAreaName(), true, 0);
			break;

		case TAB_SHARE:
			lblHeader.setText(R.string.share);
			setHeader(false, GlobalValue.area.getAreaName(), true, 0);
			break;

		case TAB_MY_PAGE:
			lblHeader.setText(R.string.myPage);
			setHeader(false, GlobalValue.area.getAreaName(), true, 0);
			break;

		case SEARCH_CONDITION:
			lblHeader.setText(R.string.conditionSearch);
			setHeader(true, getString(R.string.search), true, 0);
			break;

		case SEARCH_LOCATION:
			lblHeader.setText(R.string.locationSearch);
			setHeader(true, getString(R.string.search), true, 0);
			break;

		case SEARCH_HISTORY:
			lblHeader.setText(R.string.historySearch);
			setHeader(true, getString(R.string.search), true, 0);
			break;

		case SEARCH_FEATURE:
			lblHeader.setText(R.string.featureSearch);
			setHeader(true, getString(R.string.search), true, 0);
			break;

		default:
			break;
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

		case R.id.lblHeaderRight:
			onClickHeaderRight();
			break;
		}
	}

	private void warningNoLogin() {
		showQuestionDialog(getString(R.string.warningNoLogin), new DialogListener() {
			@Override
			public void onOk(Object object) {
				startActivity(SigninActivity.class);
				finish();
			}

			@Override
			public void onCancel(Object object) {
			}
		});
	}

	private void onClickTabNotification() {
		if (isLoginedMode) {
			setTabSelected(TAB_NOTIFICATION);
		} else {
			warningNoLogin();
		}
	}

	private void onClickTabSearch() {
		setTabSelected(TAB_SEARCH);
	}

	private void onClickTabShare() {
		setTabSelected(TAB_SHARE);
	}

	private void onClickTabUser() {
		if (isLoginedMode) {
			setTabSelected(TAB_MY_PAGE);
		} else {
			warningNoLogin();
		}
	}

	private void onClickSetting() {
		if (isLoginedMode) {
			gotoFragment(MainActivity.SETTING);
		} else {
			startActivity(SigninActivity.class);
			finish();
		}
	}

	private void onClickHeaderLeft() {
		if (currentFragment < 4 || currentFragment == HOME) {
			chooseRegion();
		} else {
			onBackPressed();
		}
	}

	private void onClickHeaderRight() {
		if (currentFragment == RESTAURANT_DETAIL) {
			String contentShare = getString(R.string.shareRestaurant1);
			contentShare += currentRestaurant.getRestaurantName();
			contentShare += getString(R.string.shareRestaurant2);
			contentShare += currentRestaurant.getPhone();
			contentShare += getString(R.string.shareRestaurant3);
			contentShare += currentRestaurant.getAddress();
			contentShare += getString(R.string.shareRestaurant4);
			contentShare += currentRestaurant.getOrderWebUrl();
			contentShare += getString(R.string.shareRestaurant5);
			contentShare += user.getShareLink();
			contentShare += getString(R.string.shareRestaurant6);

			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, contentShare);
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
		} else {
			startActivity(SigninActivity.class);
			finish();
		}
	}

	private void chooseRegion() {
		Intent intent = new Intent(this, CheckMapActivity.class);
		intent.putExtra("is_come_back_main_activity", true);
		startActivityForResult(intent, CODE_CHOOSE_REGION);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.e("", "requestCode: " + requestCode);

		if (resultCode == RESULT_OK) {
			if (requestCode == CODE_CHOOSE_REGION) {

				try {
					lblHeaderLeft.setText(GlobalValue.area.getAreaName());
				} catch (Exception e) {
				}
			} else if (requestCode == Facebook.DEFAULT_AUTH_ACTIVITY_CODE) {
				((ShareFragment) arrayFragments[TAB_SHARE]).facebook.authorizeCallback(this, requestCode, resultCode,
						data);
			}
		}
	}

	@Override
	public void onBackPressed() {
		switch (currentFragment) {
		case TAB_NOTIFICATION:
		case TAB_SEARCH:
		case TAB_SHARE:
		case TAB_MY_PAGE:
		case HOME:
			quitApp();
			break;

		case SETTING:
			backFragment(previousFragment);
			break;

		case SEARCH_CONDITION:
		case SEARCH_LOCATION:
		case SEARCH_HISTORY:
		case SEARCH_FEATURE:
			backFragment(TAB_SEARCH);
			break;

		case RESTAURANT_DETAIL:
			backFragment(currentSearch);
			break;

		case NOTIFICATION_DETAIL:
			backFragment(TAB_NOTIFICATION);

		case RESTAURANT_MAP:
			lblHeader.setText(R.string.restaurantDetail);
			backFragment(RESTAURANT_DETAIL);
			if (currentSearch == SEARCH_CONDITION) {
				setHeader(true, getString(R.string.conditionSearch), true, 0);
			} else if (currentSearch == SEARCH_LOCATION) {
				setHeader(true, getString(R.string.locationSearch), true, 0);
			} else if (currentSearch == SEARCH_HISTORY) {
				setHeader(true, getString(R.string.historySearch), true, 0);
			} else {
				setHeader(true, getString(R.string.featureSearch), true, 0);
			}

		default:
			break;
		}
	}

	private void quitApp() {
		showQuestionDialog(getString(R.string.quitApp), new DialogListener() {
			@Override
			public void onOk(Object object) {
				finish();
			}

			@Override
			public void onCancel(Object object) {
			}
		});
	}
}
