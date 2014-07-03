package com.example.inventoryappbase.core.location;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;

public final class SimpleAddress extends Intent {
	
	public SimpleAddress(Intent o) {
		super(o);
		this.setAddress(o.getStringExtra("currentLocationAddress"));
		this.setZip(o.getStringExtra("currentLocationZip"));
		this.setCity(o.getStringExtra("currentLocationCity"));
		this.setCountry(o.getStringExtra("currentLocationCountry"));
		
		this.position = new LatLng(
				o.getDoubleExtra("marker.latitude", 0.0),
				o.getDoubleExtra("marker.longitude", 0.0));
		
	}

	private String address;
	private String zip;
	private String city;
	private String country;
	private LatLng position;

	public SimpleAddress(LatLng position, String address, String zip, String city, String country) {
		this.position = position;
		this.address = address;
		this.zip = zip;
		this.city = city;
		this.country = country;
		
	}

	public SimpleAddress(Context context, Class<?> class1) {
		super(context, class1);
	}

	public SimpleAddress(Context context,
			Class<?> class1, SimpleAddress address) {
		this(context, class1);
		if (address != null) {
			this.putExtra("currentLocationAddress", address.getAddress());
			this.putExtra("currentLocationZip", address.getZip());
			this.putExtra("currentLocationCity", address.getCity());
			this.putExtra("currentLocationCountry", address.getCountry());
			this.putExtra("marker.longitude", address.getPosition().longitude);
			this.putExtra("marker.latitude", address.getPosition().latitude);
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public LatLng getPosition() {
		return position;
	}

	public void setPosition(LatLng position) {
		this.position = position;
	}
	
	public boolean isValid() {
		return this.getPosition().latitude != 0.0d && this.getPosition().longitude != 0.0d;
	}
}