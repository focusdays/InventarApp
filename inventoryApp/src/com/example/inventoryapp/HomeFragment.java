package com.example.inventoryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inventoryapp.model.PersonModel;

public class HomeFragment extends Fragment {
	
	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		
		
		PersonModel personModel = new PersonModel();
		
		TextView personNameText = (TextView) rootView.findViewById(R.id.personName);
		personNameText.setText(personModel.getPersonName());
		
		TextView personIdText = (TextView) rootView.findViewById(R.id.personId);
		personIdText.setText(personModel.getPersonId());
		
		
		
		return rootView;
		
	}

}
