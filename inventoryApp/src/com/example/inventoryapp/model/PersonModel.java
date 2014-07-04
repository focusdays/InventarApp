package com.example.inventoryapp.model;

import java.util.ArrayList;
import java.util.List;

public class PersonModel extends AbstractModel {
	
	private static PersonModel personModel;
	
	private String personName;
	private String personId;
	
	private List<InventoryModel> inventoryList =  new ArrayList<InventoryModel>();
	private LocationModel location;
	
	private PersonModel() {
		super();
		this.inventoryList.add(new InventoryModel());
	}
	
	public InventoryModel getSingleInventary() {
		for (InventoryModel inv : getInventaryList()) {
			return inv;
		}
		return null;
	}
	
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public List<InventoryModel> getInventaryList() {
		return inventoryList;
	}
	public void setInventaryList(List<InventoryModel> inventaryList) {
		this.inventoryList = inventaryList;
	}
	
	public static PersonModel getPersonInstance(){
		if(personModel == null){
			personModel = new PersonModel();
		}
		return personModel;
	}

	public LocationModel getLocation() {
		return location;
	}

	public void setLocation(LocationModel location) {
		this.location = location;
	}

	

}
