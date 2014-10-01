package com.gip.tablecross;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.gip.tablecross.listener.AlertListener;
import com.gip.tablecross.util.DialogLoading;

public class BaseActivity extends Activity {
	private DialogLoading dialogLoading;

	public void showLoading() {
		if (dialogLoading == null) {
			dialogLoading = new DialogLoading(this);
		}
		dialogLoading.show();
	}

	public void hideLoading() {
		if (dialogLoading != null) {
			dialogLoading.dismiss();
		}
	}

	protected void showAlertDialog(String message) {
		LayoutInflater inflater = getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_alert, null);
		TextView lblMessage = (TextView) dialogView.findViewById(R.id.lblMessage);
		lblMessage.setText(message);
		new AlertDialog.Builder(this).setView(dialogView).setCancelable(false)
				.setPositiveButton(android.R.string.ok, null).show();
	}

	protected void showAlertDialog(int idMessage) {
		new AlertDialog.Builder(this).setTitle(R.string.app_name).setMessage(idMessage).setCancelable(false)
				.setPositiveButton(android.R.string.ok, null).show();
	}

	protected void startActivity(Class<?> cls) {
		startActivity(new Intent(this, cls));
	}

	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(this, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	protected void showToast(int stringId) {
		Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	protected void hideKeyBoard() {
		try {
			InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}

	protected void showAlertDialog(int idString, final AlertListener listener) {
		new AlertDialog.Builder(this).setTitle(R.string.app_name).setMessage(idString).setCancelable(false)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (listener != null) {
							listener.onOk();
						}
					}
				}).create().show();
	}

	protected void showAlertDialogYesNo(int idString, final AlertListener listener) {
		new AlertDialog.Builder(this).setMessage(idString).setCancelable(false)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onOk();
					}
				}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onNo();
					}
				}).create().show();
	}

	protected void showAlertDialog(String message, int idYes, int idNo, final AlertListener listener) {
		new AlertDialog.Builder(this).setMessage(message).setCancelable(false)
				.setPositiveButton(idYes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onOk();
					}
				}).setNegativeButton(idNo, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onNo();
					}
				}).create().show();
	}
}
