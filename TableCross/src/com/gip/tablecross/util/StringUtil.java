package com.gip.tablecross.util;

import java.util.regex.Pattern;

import android.widget.EditText;

public class StringUtil {
	public static boolean isEmpty(String string) {
		return (string == null || "".equals(string.trim()));
	}

	public static boolean isEmpty(EditText txt) {
		try {
			String text = txt.getText().toString().trim();
			return isEmpty(text);
		} catch (Exception e) {
			return true;
		}
	}

	public static boolean checkEmail(String email) {
		Pattern EMAIL_ADDRESS_PATTERN = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	public static boolean checkEmail(EditText txtEmail) {
		try {
			String email = txtEmail.getText().toString().trim();
			return checkEmail(email);
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean checkMatch(EditText txt1, EditText txt2) {
		try {
			String text1 = txt1.getText().toString();
			String text2 = txt2.getText().toString();
			return text1.equals(text2);
		} catch (Exception e) {
			return false;
		}
	}
}