package com.example.inventoryapp.model;

import java.util.ArrayList;
import java.util.List;

public class InventoryModel extends AbstractModel {
	
	private int inventoryId;
	private List<CommodityModel> inventoryCommodities = new ArrayList<CommodityModel>();
	
	
	
	public InventoryModel() {
		super();
		this.inventoryCommodities.add(CommodityModel.createCommodity1());
		this.inventoryCommodities.add(CommodityModel.createCommodity2());
		this.inventoryCommodities.add(CommodityModel.createCommodity3());
	}
	
	public int getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}
	public List<CommodityModel> getInventoryCommodities() {
		return inventoryCommodities;
	}
	public void setInventoryCommodities(List<CommodityModel> inventoryCommodities) {
		this.inventoryCommodities = inventoryCommodities;
	}
	

}
