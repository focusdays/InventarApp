package com.example.inventoryapp.service;

import java.util.concurrent.ExecutionException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.inventoryapp.model.odata.LocationAddress;
import com.example.inventoryapp.model.odata.Person;
import com.example.inventoryapp.model.odata.consumer.ODataConsumerInventory;
import com.example.inventoryappbase.core.AsyncResponse;
import com.example.inventoryappbase.core.location.AddressResult;
import com.example.inventoryappbase.core.location.GetAddressAsyncTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

public class PersonLoader extends Loader<Person> implements
		ConnectionCallbacks, OnConnectionFailedListener {
	
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
