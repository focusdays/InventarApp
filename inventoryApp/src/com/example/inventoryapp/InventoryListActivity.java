package com.example.inventoryapp;


import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inventoryapp.model.CommodityModel;
import com.example.inventoryapp.model.PersonModel;

public class InventoryListActivity extends RoboActivity {

	@InjectView(R.id.commodityList)
	private ListView commoditiesView;

	List<CommodityModel> commodities;
	
	InventoryListActivity me;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory_list_main);

		commodities = PersonModel.getPersonInstance().getInventaryList().get(0)
				.getInventoryCommodities();

		commoditiesView.setAdapter(new InventoryListAdapter());
		
		me = this;
		
		
	}

	class InventoryListAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		public InventoryListAdapter() {
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return commodities.size();
		}

		@Override
		public Object getItem(int index) {
			return commodities.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(final int index, View recycledRowView, ViewGroup arg2) {
			View rowView = recycledRowView;
			if (rowView == null) {
				rowView = inflater.inflate(R.layout.inventary_list_item, arg2,
						false);
			}

			CommodityModel commodityModel = commodities.get(index);

			TextView idView = (TextView) rowView.findViewById(R.id.commodityId);
			TextView titleView = (TextView) rowView
					.findViewById(R.id.commodityTitle);
			TextView priceView = (TextView) rowView
					.findViewById(R.id.commodityPrice);
			idView.setText("" + commodityModel.getCommodityId());
			titleView.setText(commodityModel.getCommodityTitle());
			priceView.setText("" + commodityModel.getCommodityPrice());
			
			rowView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(me, EditInventaryCommodityActivity.class);
					Bundle b = new Bundle();

					b.putInt("commodityIndex", index);

					intent.putExtras(b);

					startActivity(intent);

				}});

			return rowView;
		}
	}

}
