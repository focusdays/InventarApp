package com.example.inventoryapp.model.odata;

import java.math.BigDecimal;
import java.util.List;

import com.example.inventoryappbase.core.location.SimpleAddress;


public class LocationAddress extends AbstractModel {
	private Integer locationID;
	private String locationTitle;
	private Integer locationType;
	private BigDecimal geoLocation_accuracy;
	private BigDecimal geoLocation_latitude;
	private BigDecimal geoLocation_longitude;
	private String locationAddress_land;
	private String locationAddress_postalCodeCity;
	private String locationAddress_streetHouseNr;
	private List<Inventory> inventories;
	private Person person;
	private transient SimpleAddress address;
	
	
	
	public LocationAddress() {
	}

	public Integer getLocationID() {
		return locationID;
	}

	public void setLocationID(Integer locationID) {
		this.locationID = locationID;
	}

	public String getLocationTitle() {
		return locationTitle;
	}

	public void setLocationTitle(String locationTitle) {
		this.locationTitle = locationTitle;
	}

	public Integer getLocationType() {
		return locationType;
	}

	public void setLocationType(Integer locationType) {
		this.locationType = locationType;
	}

	public BigDecimal getGeoLocation_accuracy() {
		return geoLocation_accuracy;
	}

	public void setGeoLocation_accuracy(BigDecimal geoLocation_accuracy) {
		this.geoLocation_accuracy = geoLocation_accuracy;
	}

	public BigDecimal getGeoLocation_latitude() {
		return geoLocation_latitude;
	}

	public void setGeoLocation_latitude(BigDecimal geoLocation_latitude) {
		this.geoLocation_latitude = geoLocation_latitude;
	}

	public BigDecimal getGeoLocation_longitude() {
		return geoLocation_longitude;
	}

	public void setGeoLocation_longitude(BigDecimal geoLocation_longitude) {
		this.geoLocation_longitude = geoLocation_longitude;
	}

	public String getLocationAddress_land() {
		return locationAddress_land;
	}

	public void setLocationAddress_land(String locationAddress_land) {
		this.locationAddress_land = locationAddress_land;
	}

	public String getLocationAddress_postalCodeCity() {
		return locationAddress_postalCodeCity;
	}

	public void setLocationAddress_postalCodeCity(
			String locationAddress_postalCodeCity) {
		this.locationAddress_postalCodeCity = locationAddress_postalCodeCity;
	}

	public String getLocationAddress_streetHouseNr() {
		return locationAddress_streetHouseNr;
	}

	public void setLocationAddress_streetHouseNr(
			String locationAddress_streetHouseNr) {
		this.locationAddress_streetHouseNr = locationAddress_streetHouseNr;
	}

	public List<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public SimpleAddress getAddress() {
		return address;
	}

	public void updateAddress(SimpleAddress address) {
		this.setAddress(address);
		this.setGeoLocation_latitude(new BigDecimal(address.getPosition().latitude));
		this.setGeoLocation_longitude(new BigDecimal(address.getPosition().longitude));
		this.setLocationAddress_land(address.getCountry());
		this.setLocationAddress_postalCodeCity(address.getZip()+" "+address.getCity());
		this.setLocationAddress_streetHouseNr(address.getAddress());
		this.setUpdated(true);
	}
	
	public void setAddress(SimpleAddress address) {
		this.address = address;
	}


	

}