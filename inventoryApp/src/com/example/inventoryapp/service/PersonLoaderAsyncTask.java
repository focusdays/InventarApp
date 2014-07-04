package com.example.inventoryapp.service;

import org.core4j.Enumerable;
import org.odata4j.core.OEntity;

import android.os.AsyncTask;

import com.example.inventoryapp.model.InventoryModel;
import com.example.inventoryapp.model.LocationModel;
import com.example.inventoryapp.model.PersonModel;
import com.example.inventoryappbase.core.AsyncResponse;
import com.example.inventoryappbase.core.location.SimpleAddress;
import com.google.android.gms.maps.model.LatLng;

public class PersonLoaderAsyncTask extends AsyncTask<String, Void, PersonModel> {
	
	private AsyncResponse<PersonModel, Void> response;
	public PersonLoaderAsyncTask(AsyncResponse<PersonModel, Void> response) {
		this.response = response;
	}
	@Override
	protected PersonModel doInBackground(String... personId) {
	    if (JsonGrabbingConsumer.getInstance() == null) {
	    	JsonGrabbingConsumer.reset();
	    }
		OEntity personEntity = JsonGrabbingConsumer.getInstance().getPerson(personId[0]);
		PersonModel person = this.mapPerson(personEntity);
		this.mapLocation(person, JsonGrabbingConsumer.getInstance().getLocation(personEntity));
		this.mapInventories(person, JsonGrabbingConsumer.getInstance().getInventories(personEntity));
//		this.mapCommodities(person, JsonGrabbingConsumer.getInstance().getCommodities(personEntity));
		
		return person;
	}
	@Override
	protected void onPostExecute(PersonModel result) {
		response.processFinish(result);
	}
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		this.response.processProgress(values[0]);
	}
	
	
	protected PersonModel mapPerson(OEntity personEntity) {
		PersonModel person = PersonModel.getPersonInstance();
		person.setPersonName(this.getString(personEntity, "personName"));
		return person;

	}
	private String getString(OEntity entity, String attr) {
		return (String)entity.getProperty(attr).getValue();
	}
	private Double getDouble(OEntity entity, String attr) {
		return Double.parseDouble(this.getString(entity, attr));
	}
	protected PersonModel mapLocation(PersonModel person, OEntity locationEntity) {
		person.setLocation(new LocationModel(person));
		SimpleAddress address = new SimpleAddress();
		address.setAddress(this.getString(locationEntity, "locationAddress_streetHouseNr"));
		address.setCity(this.getString(locationEntity, "locationAddress_postalCodeCity"));
		address.setPosition(new LatLng(
				this.getDouble(locationEntity, "geoLocation_latitude"), 
				this.getDouble(locationEntity, "geoLocation_longitude")));
		person.getLocation().setAddress(address);
		return person;
	}
	protected PersonModel mapInventories(PersonModel person, Enumerable<OEntity> inventories ) {
		return person;
	}
	protected PersonModel mapCommodities(PersonModel person, InventoryModel inventory, Enumerable<OEntity> commodities) {
		return person;
	}
	
	
	
	
}