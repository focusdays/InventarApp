package com.example.inventoryapp.service;

import android.app.Activity;

import com.example.inventoryappbase.core.AsyncResponse;

public class Loader<T> implements ILoader<T> {

	private Activity activity;
	private AsyncResponse<T, Void> callback;

	public Loader(Activity activity, AsyncResponse<T, Void> callback) {
		this.activity = activity;
		this.callback = callback;
	}


	@Override
	public Activity getActivity() {
		return this.activity;
	}


	@Override
	public AsyncResponse<T, Void> getCallback() {
		return this.callback;
	}

}
