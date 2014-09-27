package com.gip.tablecross.facebook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.gip.tablecross.facebook.AsyncFacebookRunner.RequestListener;
import com.gip.tablecross.util.Logger;

/**
 * Skeleton base class for RequestListeners, providing default error handling.
 * Applications should handle these error conditions.
 * 
 */
public abstract class BaseRequestListener implements RequestListener {
	public void onFacebookError(FacebookError e, final Object state) {
		Logger.e("Facebook", e.getMessage());
		e.printStackTrace();
	}

	public void onFileNotFoundException(FileNotFoundException e, final Object state) {
		Logger.e("Facebook", e.getMessage());
		e.printStackTrace();
	}

	public void onIOException(IOException e, final Object state) {
		Logger.e("Facebook", e.getMessage());
		e.printStackTrace();
	}

	public void onMalformedURLException(MalformedURLException e, final Object state) {
		Logger.e("Facebook", e.getMessage());
		e.printStackTrace();
	}
}
