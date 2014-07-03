package com.example.inventoryapp.model;

import com.example.inventoryappbase.core.location.SimpleAddress;

public class LocationModel extends AbstractModel {
	
	private int locationId;
	private SimpleAddress address;
	
	private PersonModel person;
	
	public LocationModel(PersonModel person) {
		super();
		this.setPerson(person);
	}

	public SimpleAddress getAddress() {
		return address;
	}

	public void setAddress(SimpleAddress address) {
		this.address = address;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public PersonModel getPerson() {
		return person;
	}

	public void setPerson(PersonModel person) {
		this.person = person;
	}
	

}
