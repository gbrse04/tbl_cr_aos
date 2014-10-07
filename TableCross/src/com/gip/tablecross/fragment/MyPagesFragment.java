package com.gip.tablecross.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gip.tablecross.R;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.util.StringUtil;

public class MyPagesFragment extends BaseFragment {
	private TextView lblNumber1, lblNumber2, lblNumber3, lblNumber4, lblNumber5, lblNumber6, lblNumberShareApp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_page, container, false);
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
		lblNumber1 = (TextView) view.findViewById(R.id.lblNumber1);
		lblNumber2 = (TextView) view.findViewById(R.id.lblNumber2);
		lblNumber3 = (TextView) view.findViewById(R.id.lblNumber3);
		lblNumber4 = (TextView) view.findViewById(R.id.lblNumber4);
		lblNumber5 = (TextView) view.findViewById(R.id.lblNumber5);
		lblNumber6 = (TextView) view.findViewById(R.id.lblNumber6);
		lblNumberShareApp = (TextView) view.findViewById(R.id.lblNumberShareApp);
	}

	private void initControl(View view) {
		view.findViewById(R.id.layoutGotoShare).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getMainActivity().setTabSelected(MainActivity.TAB_SHARE);
			}
		});
	}

	public void setDataUser() {
		String[] s = StringUtil.splitNumber(getMainActivity().user.getPoint());
		lblNumber1.setText(s[0]);
		lblNumber2.setText(s[1]);
		lblNumber3.setText(s[2]);

		s = StringUtil.splitNumber(getMainActivity().user.getTotalPoint());
		lblNumber4.setText(s[0]);
		lblNumber5.setText(s[1]);
		lblNumber6.setText(s[2]);

		lblNumberShareApp.setText(getString(R.string.userWhosIntroducedYou1) + " "
				+ getMainActivity().user.getTotalUserShare() + " " + getString(R.string.userWhosIntroducedYou2));
	}
}
