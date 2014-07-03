package com.example.inventoryapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.inventoryapp.model.PersonModel;

public class InventaryListFragment extends ListFragment {
	
	public InventaryListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_inventary_list, container,
				false);
		
		
		PersonModel personModel = PersonModel.getPersonInstance();
		
		ListView inventoryListView = (ListView) rootView.findViewById(R.id.inventaryList);
		
		
		
		return rootView;
		
	}

}
