package com.gip.tablecross.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.gip.tablecross.BaseActivity;
import com.gip.tablecross.PacketUtility;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.listener.DialogListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (checkGooglePlayServicesAvailable()) {
			LocationLibrary.initialiseLibrary(this, PacketUtility.getPacketName());
			GlobalValue.constructor(this);

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					startActivity(CheckMapActivity.class);
					finish();
				}
			}, 3000);
		} else {
			showQuestionDialog(getString(R.string.requireGooglePlayService), new DialogListener() {
				@Override
				public void onOk(Object object) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(GlobalValue.URL_GOOGLE_PLAY_SERVICE));
					startActivity(browserIntent);
				}

				@Override
				public void onCancel(Object object) {
					finish();
				}
			});
		}
	}

	private boolean checkGooglePlayServicesAvailable() {
		final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
			return false;
		}
		return true;
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