package com.example.inventoryapp.model;


public class CommodityModel extends AbstractModel {
	
	private static int lastId = 0;

	private int commodityId = ++lastId;
	private String commodityTitle;
	private CommodityType commodityType = CommodityType.NichtDefiniert;
	private int commodityPrice = 0;
	private String commodityPicture;
	
	
	public CommodityModel() {
		super();
		
	}
	
	public CommodityModel(String commodityTitle, CommodityType commodityType, int commodityPrice, String commodityPicture) {
		super();
		this.commodityTitle = commodityTitle;
		this.commodityType = commodityType;
		this.commodityPrice = commodityPrice;
		this.commodityPicture = commodityPicture;
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
	
	public CommodityType getCommodityType() {
		return commodityType;
	}


	public void setCommodityType(CommodityType commodityType) {
		this.commodityType = commodityType;
	}


	public int getCommodityPrice() {
		return commodityPrice;
	}


	public void setCommodityPrice(int commodityPrice) {
		this.commodityPrice = commodityPrice;
	}


	public String getCommodityPicture() {
		return commodityPicture;
	}


	public void setCommodityPicture(String commodityPicture) {
		this.commodityPicture = commodityPicture;
	}


	public static CommodityModel createCommodity1() {
		return new CommodityModel("Tisch", CommodityType.Moebel, 1000, null);
	}
	
	public static CommodityModel createCommodity2() {
		return new CommodityModel("Stuhl mit ganz vielen Beinen Beinen Beinen", CommodityType.Moebel, 100, null);
	}
	
	public static CommodityModel createCommodity3() {
		return new CommodityModel("Picasso", CommodityType.Kunst, 4000, null);
	}
}
