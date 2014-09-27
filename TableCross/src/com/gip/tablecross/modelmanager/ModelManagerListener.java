package com.gip.tablecross.modelmanager;

import com.gip.tablecross.object.SimpleResponse;

public interface ModelManagerListener {
	public void onError(String message);

	public void onSuccess(Object object, SimpleResponse simpleResponse);
}
