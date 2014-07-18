package com.example.inventoryapp.model.odata;


public class Device  {
	private Integer deviceID;
	private String deviceName;
	private String deviceType;
	private Person person;
	
	private boolean created;
	private boolean updated;
	private boolean deleted;
	
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