package com.example.inventoryapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inventoryapp.model.PersonModel;
import com.example.inventoryapp.service.IPersonLoader;
import com.example.inventoryapp.service.PersonLoader;

public class HomeFragment extends Fragment {

	private Activity mActivity;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, container, false);
		
		init();

		return rootView;

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
		System.out.println(mActivity);
	}


	private void init() {
		IPersonLoader personLoader = new PersonLoader();
		PersonModel personModel = personLoader.getPerson(mActivity);

		TextView personNameText = (TextView) rootView
				.findViewById(R.id.personName);
		personNameText.setText(personModel.getPersonName());

		TextView personIdText = (TextView) rootView.findViewById(R.id.personId);
		personIdText.setText(personModel.getPersonId());
	} 

}
