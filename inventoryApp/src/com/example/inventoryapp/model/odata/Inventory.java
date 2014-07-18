package com.example.inventoryapp.model.odata;


import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDateTime;


public class Inventory  {
	
	private Integer inventoryID;
	private Integer currency;
	private String inventoryTitle;
	private BigDecimal inventoryTotalPrice;
	private Integer inventoryType;
	private Integer language;
	private LocalDateTime mutationTimestamp;
	private List<Commodity> commodities;
	private Person person;	
	private Location location;
	
	private boolean created;
	private boolean updated;
	private boolean deleted;
	
		
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


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
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