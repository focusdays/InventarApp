package com.example.inventoryapp.model.odata;


public class Device extends AbstractModel {
	private Integer deviceID;
	private String deviceName;
	private String deviceType;
	private Person person;
	

	
	public Device() {
	}

	public Integer getDeviceID() {
		return deviceID;
	}

	
	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}