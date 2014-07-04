package com.example.inventoryapp;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.TextView;

import com.example.inventoryapp.model.CommodityModel;
import com.example.inventoryapp.model.PersonModel;

public class EditInventaryCommodityActivity extends RoboActivity {

	private List<CommodityModel> commodities;
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
	@InjectView(R.id.textPrice) private TextView inventaryCommodityprice;
	@InjectView(R.id.textType) private TextView inventaryCommodityType;
	@InjectView(R.id.textName) private TextView inventaryCommodityName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_inventory_main);
		
		commodities = PersonModel.getPersonInstance().getSingleInventary().getInventoryCommodities();
						
		Bundle b = getIntent().getExtras();

		int index = b.getInt("commodityIndex", 0);
		setSelectedCommodity (commodities.get(index));
		inventaryPrices.setText(getInventaryPricesText());
		inventaryCommodityprice.setText("" + getSelectedCommodity().getCommodityPrice());
		inventaryCommodityType.setText("" + getSelectedCommodity().getCommodityType());
		inventaryCommodityName.setText(getSelectedCommodity().getCommodityTitle());
	}

	private String getInventaryPricesText() {
		StringBuilder b = new StringBuilder();
		Integer summe = 0;
		for (CommodityModel c : commodities) {
			summe = summe + c.getCommodityPrice();
		}
		b.append(summe.toString());
		b.append(" CHF");
		return b.toString();
	}

	public CommodityModel getSelectedCommodity() {
		return selectedCommodity;
	}

	public void setSelectedCommodity(CommodityModel selectedCommodity) {
		this.selectedCommodity = selectedCommodity;
	}
}
