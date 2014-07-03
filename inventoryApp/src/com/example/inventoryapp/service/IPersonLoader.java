package com.example.inventoryapp.service;

import android.app.Activity;

import com.example.inventoryapp.model.PersonModel;

public interface IPersonLoader {
	PersonModel getPerson(Activity activity);
}
