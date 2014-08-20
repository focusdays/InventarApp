package com.example.inventoryapp.service;

import java.math.BigDecimal;

import org.odata4j.core.OEntity;

import android.os.AsyncTask;

import com.example.inventoryapp.model.odata.Person;
import com.example.inventoryapp.model.odata.consumer.ODataConsumerInventory;
import com.example.inventoryappbase.core.AsyncResponse;

public class PersonLoaderAsyncTask extends AsyncTask<String, Void, Person> {
	
	private AsyncResponse<Person, Void> response;
	public PersonLoaderAsyncTask(AsyncResponse<Person, Void> response) {
		this.response = response;
	}
	@Override
	protected Person doInBackground(String... personId) {
	    return ODataConsumerInventory.getInstance().getPersonHierarchy(personId[0]);
	}
	@Override
	protected void onPostExecute(Person result) {
		response.processFinish(result);
	}
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		this.response.processProgress(values[0]);
	}
	
	
	private String getString(OEntity entity, String attr) {
		return (String)entity.getProperty(attr).getValue();
	}
	private BigDecimal getBigDecimal(OEntity entity, String attr) {
		return (BigDecimal)entity.getProperty(attr).getValue();
	}
	private Integer getInteger(OEntity entity, String attr) {
		return (Integer)entity.getProperty(attr).getValue();
	}
	private int getInt(OEntity entity, String attr) {
		Integer i = this.getInteger(entity, attr);
		if (i == null) {
			return 0;
		} else {
			return i.intValue();
		}
	}
	private double getDouble(OEntity entity, String attr) {
		BigDecimal bigDecimal = this.getBigDecimal(entity, attr);
		if (bigDecimal == null) {
			return 0.0;
		} else {
			return bigDecimal.doubleValue();
		}
	}
	
	
	
	
}