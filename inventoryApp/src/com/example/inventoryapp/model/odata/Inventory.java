package com.example.inventoryapp.model.odata;


import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDateTime;


public class Inventory extends AbstractModel {
	
	private Integer inventoryID;
	private Integer currency;
	private String inventoryTitle;
	private BigDecimal inventoryTotalPrice;
	private Integer inventoryType;
	private Integer language;
	private LocalDateTime mutationTimestamp;
	private List<Commodity> commodities;
	private Person person;	
	private LocationAddress location;
	
	
		
	public Inventory() {
	}


	public Integer getInventoryID() {
		return inventoryID;
	}


	public void setInventoryID(Integer inventoryID) {
		this.inventoryID = inventoryID;
	}


	public Integer getCurrency() {
		return currency;
	}


	public void setCurrency(Integer currency) {
		this.currency = currency;
	}


	public String getInventoryTitle() {
		return inventoryTitle;
	}


	public void setInventoryTitle(String inventoryTitle) {
		this.inventoryTitle = inventoryTitle;
	}


	public BigDecimal getInventoryTotalPrice() {
		return inventoryTotalPrice;
	}
	
	public void updateInventoryTotalPrice() {
		BigDecimal total = new BigDecimal(0);
		for (Commodity commodity : this.getCommodities()) {
			if (commodity.getCommodityPrice() != null) total.add(commodity.getCommodityPrice());
		}
		this.setInventoryTotalPrice(total);
		this.setUpdated(true);
	}


	public void setInventoryTotalPrice(BigDecimal inventoryTotalPrice) {
		this.inventoryTotalPrice = inventoryTotalPrice;
	}


	public Integer getInventoryType() {
		return inventoryType;
	}


	public void setInventoryType(Integer inventoryType) {
		this.inventoryType = inventoryType;
	}


	public Integer getLanguage() {
		return language;
	}


	public void setLanguage(Integer language) {
		this.language = language;
	}


	public LocalDateTime getMutationTimestamp() {
		return mutationTimestamp;
	}


	public void setMutationTimestamp(LocalDateTime mutationTimestamp) {
		this.mutationTimestamp = mutationTimestamp;
	}


	public List<Commodity> getCommodities() {
		return commodities;
	}


	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public LocationAddress getLocation() {
		return location;
	}


	public void setLocation(LocationAddress location) {
		this.location = location;
	}


	public Commodity addCommodity(Commodity commodity) {
		this.getCommodities().add(commodity);
		commodity.setInventory(this);
		return commodity;
	}


}