package com.bamboo.tablecross.modelmanager;

import com.bamboo.tablecross.object.SimpleResponse;

public interface ModelManagerListener {
	public void onError(String message);

	public void onSuccess(Object object, SimpleResponse simpleResponse);
}
