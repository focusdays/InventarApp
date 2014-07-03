package com.example.inventoryapp.model;


public class CommodityModel extends AbstractModel {
	
	private static int lastId = 0;

	private int commodityId;
	private String commodityTitle;
	private Object commodityType;
	
	public CommodityModel(String commodityTitle, Object commodityType) {
		super();
		this.commodityTitle = commodityTitle;
		this.commodityType = commodityType;
		this.commodityId = ++lastId;
	}
	
	
	public int getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}
	public String getCommodityTitle() {
		return commodityTitle;
	}
	public void setCommodityTitle(String commodityTitle) {
		this.commodityTitle = commodityTitle;
	}
	
	public static CommodityModel createCommodity1() {
		return new CommodityModel("Tisch", null);
	}
	
	public static CommodityModel createCommodity2() {
		return new CommodityModel("Stuhl", null);
	}
	
	public static CommodityModel createCommodity3() {
		return new CommodityModel("Sofa", null);
	}
}
