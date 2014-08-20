package com.example.inventoryapp.model.odata;


import java.math.BigDecimal;

import org.joda.time.LocalDateTime;

import android.os.Parcel;


public class Commodity  extends AbstractModel {
	
	private static int MAX = 14;
	
	private Integer commodityId = ++MAX;
	private String commodityTitle;
	private Integer commodityType = 3;
	private Integer roomType = 1;
	private BigDecimal commodityPrice = new BigDecimal(10);
	private String commodityPicture;
	private LocalDateTime mutationTimestamp = LocalDateTime.now();
	
	private Inventory inventory;

	public Commodity() {
		super();
	}

	public Commodity(Parcel source) {
		super(source);
		this.commodityId = (Integer)source.readValue(null);
		this.commodityTitle = (String)source.readValue(null);
		this.commodityType = (Integer)source.readValue(null);
		this.roomType = (Integer)source.readValue(null);
		this.commodityPrice = new BigDecimal(source.readString());
		this.commodityPicture = (String)source.readValue(null);;
		this.mutationTimestamp = LocalDateTime.parse(source.readString());
		/*
		private Integer commodityId;
		private String commodityTitle;
		private Integer commodityType;
		private Integer roomType;
		private BigDecimal commodityPrice;
		private String commodityPicture;
		private LocalDateTime mutationTimestamp;
		 */
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		/*
		private Integer commodityId;
		private String commodityTitle;
		private Integer commodityType;
		private Integer roomType;
		private BigDecimal commodityPrice;
		private String commodityPicture;
		private LocalDateTime mutationTimestamp;
		 */
		dest.writeValue(this.commodityId);
		dest.writeValue(this.commodityTitle);
		dest.writeValue(this.commodityType);
		dest.writeValue(this.roomType);
		dest.writeString(this.commodityPrice.toString());
		dest.writeValue(this.commodityPicture);
		dest.writeString(this.mutationTimestamp.toString());
	}
		
	
	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public String getCommodityTitle() {
		return commodityTitle;
	}

	public void setCommodityTitle(String commodityTitle) {
		this.commodityTitle = commodityTitle;
	}

	public Integer getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(Integer commodityType) {
		this.commodityType = commodityType;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public BigDecimal getCommodityPrice() {
		return commodityPrice;
	}

	public void setCommodityPrice(BigDecimal commodityPrice) {
		this.commodityPrice = commodityPrice;
	}

	public String getCommodityPicture() {
		return commodityPicture;
	}

	public void setCommodityPicture(String commodityPicture) {
		this.commodityPicture = commodityPicture;
	}

	public LocalDateTime getMutationTimestamp() {
		return mutationTimestamp;
	}

	public void setMutationTimestamp(LocalDateTime mutationTimestamp) {
		this.mutationTimestamp = mutationTimestamp;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public int describeContents() {
		return 0;
	}


    public static final Creator<Commodity> CREATOR = new Creator<Commodity>() {
        public Commodity createFromParcel(Parcel source) {
            return new Commodity(source);
        }

        public Commodity[] newArray(int size) {
            return new Commodity[size];
        }
    };
	
}