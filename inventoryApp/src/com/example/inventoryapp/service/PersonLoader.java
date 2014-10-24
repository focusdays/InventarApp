package com.example.inventoryapp.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.joda.time.LocalDateTime;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.inventoryapp.model.odata.Commodity;
import com.example.inventoryapp.model.odata.Device;
import com.example.inventoryapp.model.odata.Inventory;
import com.example.inventoryapp.model.odata.LocationAddress;
import com.example.inventoryapp.model.odata.Person;
import com.example.inventoryapp.model.odata.consumer.ODataConsumerInventory;
import com.example.inventoryappbase.core.AsyncResponse;
import com.example.inventoryappbase.core.location.AddressResult;
import com.example.inventoryappbase.core.location.GetAddressAsyncTask;
import com.example.inventoryappbase.core.location.SimpleAddress;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

public class PersonLoader extends Loader<Person> implements
		ConnectionCallbacks, OnConnectionFailedListener {
	
	static final Random RAND = new Random();
	
	LocationClient locationClient;
	Person person;

	public PersonLoader(Activity activity, AsyncResponse<Person, Void> callback) {
		super(activity, callback);
	}

	public String getGoogleEmail() {
		AccountManager accountManager = AccountManager.get(this.getActivity());
		Account[] accounts = accountManager.getAccountsByType("com.google");
		for (Account account : accounts) {
			if (account.name != null && account.name.contains("@")) {
				return account.name;
			}
		}
		return "max@muster.ch";
	}
	
	public Map<String, String> getMyProfile() {
		Cursor c = this.getActivity().getContentResolver().query(
				ContactsContract.Profile.CONTENT_URI, null, null, null, null);
		int count = c.getCount();

		String[] columnNames = c.getColumnNames();
		Map<String, String> profileList = new HashMap<String, String>();
		c.moveToFirst();
		int position = c.getPosition();
		if (count == 1 && position == 0) {
			for (int i = 0; i < count; i++) {
				for (int j = 0; j < columnNames.length; j++) {
					String columnName = columnNames[j];
					String cellValue = c.getString(c.getColumnIndex(columnName));
					if (cellValue != null && !cellValue.equals("")) {
						profileList.put(columnName, cellValue);
					}
				}
				c.moveToNext();
			}
		}
		c.close();
		return profileList;
	}

	// public void loadCurrentPerson() {
	// Cursor c =
	// this.getActivity().getContentResolver().query(ContactsContract.Profile.CONTENT_URI,
	// null, null, null, null);
	// try {
	// c.moveToFirst();
	// //
	// person.setPersonId(c.getString(c.getColumnIndex(ContactsContract.Contacts._ID)));
	// //
	// person.setPersonName(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
	// new PersonLoaderAsyncTask(this.getCallback()).execute("max@muster.ch");
	// // this.getLocation(activity, person);
	// } finally {
	// c.close();
	// }
	// }

	public void loadCurrentPerson() {
		String personId = this.getGoogleEmail();
		ODataConsumerInventory.getInstance().setPersonId(personId);
		new PersonLoaderAsyncTask(this.getCallback()).execute(personId);
	}
	public Person createNewPerson() {
		String personId = this.getGoogleEmail();
		Map<String, String> myProfile = this.getMyProfile();
		Person person = new Person();
		person.setPersonID(personId);
		person.setPersonName(myProfile.get("display_name"));
		person.setTotalPriceInventories(new BigDecimal(0));
		person.setCreated(true);
		person.setDeleted(false);
		person.setUpdated(false);
		
		Device newDevice = new Device();
		newDevice.setDeviceID(RAND.nextInt(1000));
		newDevice.setPerson(person);
		person.getDevices().add(newDevice);
		newDevice.setDeviceID(new Random().nextInt(200));
		newDevice.setDeviceName(Build.DEVICE);
		newDevice.setDeviceType(Build.BRAND);
		newDevice.setCreated(true);
		newDevice.setDeleted(false);
		newDevice.setUpdated(false);
		
		LocationAddress location = new LocationAddress();
		location.setLocationID(RAND.nextInt(1000));
		SimpleAddress simpleAddress = new SimpleAddress();
		simpleAddress.setAddress(" ");
		simpleAddress.setCity(" ");
		simpleAddress.setCountry(" ");
		simpleAddress.setZip(" ");
		simpleAddress.setPosition(new LatLng(0.0d, 0.0d));
		location.updateAddress(simpleAddress);
		location.setLocationTitle(" ");
		location.setLocationType(1);
		location.setPerson(person);
		person.setLocation(location);
		location.setCreated(true);
		location.setDeleted(false);
		location.setUpdated(false);
		
		Inventory inventory = new Inventory();
		inventory.setInventoryID(RAND.nextInt(1000));
		inventory.setCurrency(1);
		inventory.setInventoryTitle(" ");
		inventory.setInventoryTotalPrice(new BigDecimal(0));
		inventory.setInventoryType(1);
		inventory.setLanguage(1);
		inventory.setLocation(location);
		location.getInventories().add(inventory);
		inventory.setMutationTimestamp(new LocalDateTime());
		inventory.setPerson(person);
		person.setInventory(inventory);
		inventory.setCreated(true);
		inventory.setDeleted(false);
		inventory.setUpdated(false);

		Commodity commodity = new Commodity();
		commodity.setCommodityId(RAND.nextInt(1000));
		commodity.setCommodityPicture(" ");
		commodity.setCommodityPrice(new BigDecimal(2131));
		commodity.setCommodityTitle("Watch");
		commodity.setCommodityType(1);
		commodity.setCreated(true);
		commodity.setDeleted(false);
		commodity.setUpdated(false);
		commodity.setMutationTimestamp(new LocalDateTime());
		commodity.setRoomType(1);
		commodity.setInventory(inventory);
		inventory.getCommodities().add(commodity);
		return person;
	}

	public void getCurrentLocation(Person p) {
		this.person = p;
		locationClient = new LocationClient(this.getActivity(), 
				this, // ConnectionCallbacks
				this); // OnConnectionFailedListener
		locationClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {

	}

	@Override
	public void onConnected(Bundle arg0) {
		try {
			Location location = locationClient.getLastLocation();
			LatLng lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
			AddressResult addressResult = new GetAddressAsyncTask(this.getActivity()).execute(lastLocation).get();
			this.person.getLocation().updateAddress(addressResult.getSimpleAddress());
			this.getCallback().processFinish(person);
			
		} catch (InterruptedException e) {
			Log.i(this.getClass().getName(), "error while retrieving location", e);
		} catch (ExecutionException e) {
			Log.i(this.getClass().getName(), "error while retrieving location", e);
		} finally {
			locationClient.disconnect();
		}
	}

	@Override
	public void onDisconnected() {

	}

}
