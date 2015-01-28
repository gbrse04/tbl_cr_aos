package com.gip.tablecross.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.gip.tablecross.fragment.search.CategorySearchFragment;
import com.gip.tablecross.fragment.search.ConditionSearchFragment;
import com.gip.tablecross.listener.DialogListener;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Category;
import com.gip.tablecross.object.Image;
import com.gip.tablecross.object.Notification;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.object.User;
import com.gip.tablecross.util.Logger;
import com.gip.tablecross.util.NetworkUtil;
import com.gip.tablecross.util.StringUtil;

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
	public static final int GALLERY = 12;
	public static final int VIEW_IMAGE = 13;
	public static final int NOTIFICATION_DETAIL = 14;

	public static final String HISTORY_SEARCH = "0";
	public static final String LOCATION_SEARCH = "1";
	public static final String CONDITION_SEARCH = "2";
	public static final String CATEGORY_SEARCH = "3";

	public TextView lblHeader;
	private TextView lblHeaderLeft, lblHeaderRight, lblNumberNotify;
	private View imgSetting;
	private View layoutTabNotification, layoutTabSearch, layoutTabShare, layoutTabUser;
	public RelativeLayout layoutFragment;

	private FragmentManager fm;
	public List<Fragment> arrayFragments;
	public int currentFragment;
	public int previousFragment;
	public int currentSearch;

	public User user;
	public boolean isLoginedMode;
	public String accessToken;

	public Restaurant currentRestaurant;

	public int indexImage;
	public List<Image> listImages;

	private int mInterval = 30000; // 30 seconds by default, can be changed later
	private Handler mHandler;
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			getNotifyUnpush();
			mHandler.postDelayed(runnable, mInterval);
		}
	};

	private void startRepeatingTask() {
		runnable.run();
	}

	private void stopRepeatingTask() {
		mHandler.removeCallbacks(runnable);
	}

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
			((HomeFragment) arrayFragments.get(HOME)).setUserPoint();
		}

		mHandler = new Handler();
	}

	private void initUI() {
		lblHeader = (TextView) findViewById(R.id.lblHeader);
		lblHeaderLeft = (TextView) findViewById(R.id.lblHeaderLeft);
		lblHeaderRight = (TextView) findViewById(R.id.lblHeaderRight);
		lblNumberNotify = (TextView) findViewById(R.id.lblNumberNotify);
		layoutFragment = (RelativeLayout) findViewById(R.id.layoutFragment);
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
		if (!NetworkUtil.isNetworkAvailable(this)) {
			showDialogNoNetwork();
		} else {
			showLoading();
			GlobalValue.modelManager.getUserInfo(new ModelManagerListener() {
				@Override
				public void onSuccess(Object object, SimpleResponse simpleResponse) {
					user = (User) object;
					if (GlobalValue.prefs.getUserLoginType() == SigninActivity.ACCOUNT_FACEBOOK
							&& StringUtil.isEmpty(user.getNameKanji())) {
						user.setNameKanji(GlobalValue.prefs.getUserName());
					}
					setUserInfo();
					hideLoading();
					showToast(simpleResponse.getErrorMess());

					startRepeatingTask();
				}

				@Override
				public void onError(String message) {
					showToast(message);
					hideLoading();
				}
			});
		}
	}

	private void setUserInfo() {
		((SettingFragment) arrayFragments.get(SETTING)).setDataUser();
		((MyPagesFragment) arrayFragments.get(TAB_MY_PAGE)).setDataUser();
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
		arrayFragments = new ArrayList<Fragment>();
		arrayFragments.add(fm.findFragmentById(R.id.fragmentNotification));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearch));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentShare));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentUser));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearchCondition));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearchLocation));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearchHistory));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearchFeature));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSetting));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentHome));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentRestaurantDetail));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentRestaurantMap));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentGallery));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentViewImage));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentNotificationDetail));

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
		transaction.show(arrayFragments.get(fragmentIndex));
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
			clearAllCategorySearchFragment();
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

	public void goLastFragment() {
		showFragment(arrayFragments.size() - 1);
	}

	public void addNewCategorySearchFragment(Category category) {
		CategorySearchFragment newFragment = CategorySearchFragment.newInstance(category);
		arrayFragments.add(newFragment);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.layoutFragment, newFragment).commit();
	}

	public void clearAllCategorySearchFragment() {
		int size = arrayFragments.size();
		for (int i = size - 1; i > NOTIFICATION_DETAIL; i--) {
			Fragment fragment = arrayFragments.get(i);
			arrayFragments.remove(i);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.remove(fragment).commit();
		}
	}

	private void getNotifyUnpush() {
		GlobalValue.modelManager.getUnpushNotify(new ModelManagerListener() {
			@Override
			public void onSuccess(Object object, SimpleResponse simpleResponse) {
				int numberUnpush = (Integer) object;
				if (numberUnpush > 0) {
					lblNumberNotify.setText(String.valueOf(numberUnpush));
					lblNumberNotify.setVisibility(View.VISIBLE);
				} else {
					lblNumberNotify.setVisibility(View.GONE);
				}
			}

			@Override
			public void onError(String message) {
			}
		});
	}

	public void gotoFragment(int fragment) {
		Logger.e("", "arrayFragments size: " + arrayFragments.size());

		FragmentTransaction transaction = fm.beginTransaction();
		// transaction.setCustomAnimations(
		// R.anim.fragment_out_right,R.anim.fragment_in_left);
		transaction.show(arrayFragments.get(fragment));
		transaction.hide(arrayFragments.get(currentFragment));

		// transaction.setCustomAnimations(R.anim.slide_in_left,
		// R.anim.slide_out_right);
		// transaction.replace(R.id.layoutFragment,
		// arrayFragments.get(fragment));

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
			((ConditionSearchFragment) arrayFragments.get(SEARCH_CONDITION)).startSearch();
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
			((RestaurantDetailFragment) arrayFragments.get(RESTAURANT_DETAIL)).setCurrentRestaurant();
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

			case TAB_NOTIFICATION:
				setHeader(true, getString(R.string.notification), false, R.string.share);
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

		case GALLERY:
			lblHeader.setText(R.string.gallery);
			setHeader(true, getString(R.string.restaurantDetail), false, 0);
			break;

		case VIEW_IMAGE:
			lblHeader.setText(R.string.viewImage);
			setHeader(true, getString(R.string.gallery), false, 0);
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
		((NotificationDetailFragment) arrayFragments.get(NOTIFICATION_DETAIL)).setData(notification);
	}

	public void backFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		// transaction.setCustomAnimations(R.anim.fragment_in_left,
		// R.anim.fragment_out_left);
		transaction.show(arrayFragments.get(fragment));
		transaction.hide(arrayFragments.get(currentFragment));
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

		case RESTAURANT_DETAIL:
			lblHeader.setText(R.string.restaurantDetail);
			if (currentSearch == SEARCH_CONDITION) {
				setHeader(true, getString(R.string.conditionSearch), true, R.string.share);
			} else if (currentSearch == SEARCH_LOCATION) {
				setHeader(true, getString(R.string.locationSearch), true, R.string.share);
			} else if (currentSearch == SEARCH_HISTORY) {
				setHeader(true, getString(R.string.historySearch), true, R.string.share);
			} else {
				setHeader(true, getString(R.string.featureSearch), true, R.string.share);
			}
			break;

		case GALLERY:
			lblHeader.setText(R.string.gallery);
			setHeader(true, getString(R.string.restaurantDetail), false, 0);
			break;

		default:
			break;
		}

		if (fragment > NOTIFICATION_DETAIL) {
			lblHeader.setText(((CategorySearchFragment) arrayFragments.get(fragment)).categoryName);
			setHeader(true, getString(R.string.back), false, R.string.close);
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
		} else if (currentFragment > NOTIFICATION_DETAIL) {
			((CategorySearchFragment) arrayFragments.get(currentFragment)).quickSearch();
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
				((ShareFragment) arrayFragments.get(TAB_SHARE)).facebook.authorizeCallback(this, requestCode,
						resultCode, data);
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
			return;

		case SETTING:
			backFragment(previousFragment);
			return;

		case SEARCH_CONDITION:
		case SEARCH_LOCATION:
		case SEARCH_HISTORY:
		case SEARCH_FEATURE:
			backFragment(TAB_SEARCH);
			return;

		case RESTAURANT_DETAIL:
			backFragment(currentSearch);
			return;

		case NOTIFICATION_DETAIL:
			backFragment(TAB_NOTIFICATION);
			return;

		case RESTAURANT_MAP:
			lblHeader.setText(R.string.restaurantDetail);
			backFragment(RESTAURANT_DETAIL);
			return;

		case GALLERY:
			backFragment(RESTAURANT_DETAIL);
			return;

		case VIEW_IMAGE:
			backFragment(GALLERY);
			return;

		case NOTIFICATION_DETAIL + 1:
			backFragment(SEARCH_FEATURE);
			clearAllCategorySearchFragment();
			return;
		}

		if (currentFragment > NOTIFICATION_DETAIL + 1) {
			backFragment(currentFragment - 1);
			Fragment fragment = arrayFragments.get(arrayFragments.size() - 1);
			arrayFragments.remove(arrayFragments.size() - 1);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.remove(fragment).commit();
		}
	}

	private void quitApp() {
		showQuestionDialog(getString(R.string.quitApp), new DialogListener() {
			@Override
			public void onOk(Object object) {
				stopRepeatingTask();
				finish();
			}

			@Override
			public void onCancel(Object object) {
			}
		});
	}
}
