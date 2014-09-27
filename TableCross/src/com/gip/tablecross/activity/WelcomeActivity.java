package com.gip.tablecross.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

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

	protected void getHashKey() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.gip.tablecross",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String hashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT);
				Log.e("MY KEY HASH:", "hashKey: " + hashKey);
				// TextView t = (TextView) findViewById(R.id.lblHashKey);
				// t.setText(hashKey);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
