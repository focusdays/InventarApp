package com.example.inventoryapp;

import java.util.List;

import com.example.inventoryapp.model.CommodityModel;
import com.example.inventoryapp.service.PersonLoader;

import android.os.Bundle;
import android.widget.TextView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

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
	@InjectView(R.id.textPrice) private TextView inventaryCommodityprice;
	@InjectView(R.id.textType) private TextView inventaryCommodityType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_inventory_main);
				
		setcList(new PersonLoader().getPerson(this).getSingleInventary().getInventoryCommodities());
		setSelectedCommodity (getFirstCommodity());
		inventaryPrices.setText(getInventaryPricesText());
		inventaryCommodityprice.setText(((Integer) getSelectedCommodity().getCommodityPrice()).toString());
		inventaryCommodityType.setText("Möbel");
	}

	private CharSequence getInventaryPricesText() {
		StringBuilder b = new StringBuilder();
		Integer summe = 0;
		for (CommodityModel c : cList) {
			summe = summe + c.getCommodityPrice();
		}
		b.append("47000");
		b.append(" CHF / ");
		b.append(summe.toString());
		b.append(" CHF");
		return b.toString();
	}

	private CommodityModel getFirstCommodity() {
		for ( CommodityModel c : cList) {
			return c;
		}
		return null;
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
