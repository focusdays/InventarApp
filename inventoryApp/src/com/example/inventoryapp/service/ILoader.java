package com.example.inventoryapp.service;

import android.app.Activity;

import com.example.inventoryappbase.core.AsyncResponse;

public interface ILoader<T> {
	public Activity getActivity();
	public AsyncResponse<T, Void> getCallback();
}
