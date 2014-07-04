package com.example.inventoryapp;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.TextView;

import com.example.inventoryapp.model.CommodityModel;
import com.example.inventoryapp.service.PersonLoader;

public class EditInventaryCommodityActivity extends RoboActivity {

	private List<CommodityModel> cList;
	private CommodityModel selectedCommodity;
	
//	public EditInventaryCommodityActivity(List<CommodityModel> commodityList) {
//		super();
//		if (commodityList.isEmpty()) {
//			cList = new PersonLoader().getPerson(this).getSingleInventary().getInventoryCommodities();
//		} else {
//			cList = commodityList;
//		}
//	}

	@InjectView(R.id.textTitel) private TextView inventaryPrices;
	@InjectView(R.id.labelPrice) private TextView inventaryCommodityprice;
	@InjectView(R.id.labelType) private TextView inventaryCommodityType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_inventory_main);
		
		inventaryPrices.setTag("54'000 CHF / 140'000 CHF");
		inventaryCommodityprice.setText("140 CHF");
		
		setcList(new PersonLoader().getPerson(this).getSingleInventary().getInventoryCommodities());
		
	}

	public List<CommodityModel> getcList() {
		return cList;
	}

	public void setcList(List<CommodityModel> cList) {
		this.cList = cList;
	}

	public CommodityModel getSelectedCommodity() {
		return selectedCommodity;
	}

	public void setSelectedCommodity(CommodityModel selectedCommodity) {
		this.selectedCommodity = selectedCommodity;
	}
}
