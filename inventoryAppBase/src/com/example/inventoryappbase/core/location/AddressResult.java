package com.example.inventoryappbase.core.location;

import java.util.ArrayList;
import java.util.List;

import android.location.Address;

public class AddressResult {
	private List<Address> addresses;
	private String message;
	private List<SimpleAddress> simpleAddresses;

	public AddressResult(List<Address> addresses, List<SimpleAddress> simpleAddresses) {
		this.addresses = addresses;
		this.simpleAddresses = simpleAddresses;
	}
	public AddressResult(Address address, SimpleAddress simpleAddress) {
		this.addresses = new ArrayList<Address>();
		this.addresses.add(address);
		this.simpleAddresses = new ArrayList<SimpleAddress>();
		this.simpleAddresses.add(simpleAddress);
	}
	public AddressResult(String message) {
		this.message = message;
		this.addresses = new ArrayList<Address>();
		this.simpleAddresses = new ArrayList<SimpleAddress>();
	}
	public AddressResult() {
		this(null);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean hasResult() {
		return (this.addresses != null && this.addresses.size() > 0);
	}
	public boolean hasError() {
		return !this.hasResult();
	}
	public SimpleAddress getSimpleAddress() {
		return this.simpleAddresses.size() > 0 ? this.simpleAddresses.get(0) : null;
	}
}