package com.gip.tablecross.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;

import com.gip.tablecross.BaseActivity;
import com.gip.tablecross.PacketUtility;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		LocationLibrary.initialiseLibrary(this, PacketUtility.getPacketName());
		GlobalValue.constructor(this);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(CheckMapActivity.class);
				finish();
			}
		}, 2000);
		getHashKey();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		Point size = new Point();
		WindowManager w = getWindowManager();
		w.getDefaultDisplay().getSize(size);
		GlobalValue.screenWidth = size.x;
		GlobalValue.screenHeight = size.y;
	}

	protected void getHashKey() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.gip.tablecross", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String hashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT);
				Log.e("MY KEY HASH:", "hashKey: " + hashKey);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}