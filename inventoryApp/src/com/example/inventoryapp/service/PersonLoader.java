package com.example.inventoryapp.service;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.inventoryapp.model.LocationModel;
import com.example.inventoryapp.model.PersonModel;
import com.example.inventoryappbase.core.location.AddressResult;
import com.example.inventoryappbase.core.location.GetAddressAsyncTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

public class PersonLoader implements IPersonLoader, ConnectionCallbacks, OnConnectionFailedListener  {

	@Override
	public PersonModel getPerson(Activity activity) {
		
		PersonModel person = PersonModel.getPersonInstance();
		
		Cursor c = activity.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
		try {
			c.moveToFirst();
			person.setPersonId(c.getString(c.getColumnIndex(BaseColumns._ID)));
			person.setPersonName(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
		} finally {
			c.close();
		}
//		this.getLocation(activity, person);
		
		return person;
	}
	
	
	public LocationModel getLocation(Activity activity, PersonModel person) {
		
        LocationClient locationClient = new LocationClient(
                activity,
                this,  // ConnectionCallbacks
                this); // OnConnectionFailedListener
        locationClient.connect();
        try {
	        Location location = locationClient.getLastLocation();
	        LatLng lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
	        AddressResult addressResult = new GetAddressAsyncTask(activity).execute(lastLocation).get();
	        person.getLocation().setAddress(
	        		addressResult.getSimpleAddress());
        } catch (InterruptedException e) {
        	Log.i(this.getClass().getName(), "error while retrieving location", e);
		} catch (ExecutionException e) {
        	Log.i(this.getClass().getName(), "error while retrieving location", e);
		} finally {
        	locationClient.disconnect();
        }
		return person.getLocation();
	}


	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
	}


	@Override
	public void onConnected(Bundle arg0) {
		
	}


	@Override
	public void onDisconnected() {
		
	}
}
