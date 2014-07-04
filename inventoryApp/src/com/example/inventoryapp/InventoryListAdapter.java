package com.example.inventoryapp;

import android.content.Context;
import android.widget.ArrayAdapter;

public class InventoryListAdapter extends ArrayAdapter<String> {

	private final Context context;
    private final int textViewResourceId;
    
    public InventoryListAdapter(Context context, 
			int textViewResourceId) {
		super(context, R.layout.inventory_list_main, textViewResourceId);
		this.context = context;
        this.textViewResourceId = textViewResourceId;
        }
	
}
