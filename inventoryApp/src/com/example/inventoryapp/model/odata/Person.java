package com.example.inventoryapp.model.odata;

import java.math.BigDecimal;
import java.util.List;


public class Person  {
	private String personID;
	private String personName;
	private BigDecimal totalPriceInventories;
	private List<Device> devices;
	private List<Inventory> inventories;
	private List<Location> locations;
	
	private boolean created;
	private boolean updated;
	private boolean deleted;

	public Person() {
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public BigDecimal getTotalPriceInventories() {
		return totalPriceInventories;
	}

	public void setTotalPriceInventories(BigDecimal totalPriceInventories) {
		this.totalPriceInventories = totalPriceInventories;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public List<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
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