package com.gip.tablecross.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.gip.tablecross.R;

public class DialogLoading extends Dialog {
	private String message;

	public DialogLoading(Context context) {
		super(context);
		setCancelable(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}

	public DialogLoading(Context context, String message) {
		super(context);
		setCancelable(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		this.message = message;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loading);
		if (!StringUtil.isEmpty(message)) {
			((TextView) findViewById(R.id.lblMessage)).setText(message);
		}
	}
}
