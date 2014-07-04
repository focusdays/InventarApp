package com.example.inventoryapp;

import java.util.List;

import roboguice.activity.RoboActivity;
import android.os.Bundle;

import com.example.inventoryapp.model.CommodityModel;
import com.example.inventoryapp.model.InventoryModel;
import com.example.inventoryapp.model.PersonModel;

public class InventoryListActivity extends RoboActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory_list_main);
		
		PersonModel personModel = PersonModel.getPersonInstance();
		InventoryModel inventoryModel = personModel.getInventaryList().get(0);
		List<CommodityModel> commodities = inventoryModel.getInventoryCommodities();
		
		

	}

}
