package com.example.inventoryapp.model.odata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Person extends AbstractModel {
	private String personID;
	private String personName;
	private BigDecimal totalPriceInventories;
	private List<Device> devices;
	private List<Inventory> inventories;
	private List<LocationAddress> locations;
	
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
	public Device getDevice() {
		return this.getFirst(Device.class, this.getDevices());
	}


	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	public void setDevice(Device device) {
		if (this.getDevices() == null) this.devices = new ArrayList<Device>();
		this.setFirst(Device.class, this.getDevices(), device);
	}

	public List<Inventory> getInventories() {
		return inventories;
	}
	public Inventory getInventory() {
		return this.getFirst(Inventory.class, this.getInventories());
	}
	public void setInventory(Inventory inventory) {
		if (this.getInventories() == null) this.inventories = new ArrayList<Inventory>();
		this.setFirst(Inventory.class, this.getInventories(), inventory);
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}

	public List<LocationAddress> getLocations() {
		return locations;
	}
	public LocationAddress getLocation() {
		return this.getFirst(LocationAddress.class, this.getLocations());
	}
	public void setLocation(LocationAddress location) {
		if (this.getLocations() == null) {
			this.locations = new ArrayList<LocationAddress>();
		}
		this.setFirst(LocationAddress.class, this.getLocations(), location);
	}

	
	public void setLocations(List<LocationAddress> locations) {
		this.locations = locations;
	}

	
}