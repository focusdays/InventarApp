package com.example.inventoryapp.model.odata;


import java.math.BigDecimal;

import org.joda.time.LocalDateTime;


public class Commodity  {
	
	private Integer commodityId;
	private String commodityTitle;
	private Integer commodityType;
	private Integer roomType;
	private BigDecimal commodityPrice;
	private String commodityPicture;
	private LocalDateTime mutationTimestamp;
	
	private boolean created;
	private boolean updated;
	private boolean deleted;
	
	private Inventory inventory;

	public Commodity() {
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

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}