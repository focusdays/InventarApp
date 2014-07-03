package com.example.inventoryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inventoryapp.model.PersonModel;

public class InventaryListCommodityFragment extends Fragment {
	
	public InventaryListCommodityFragment() {
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
