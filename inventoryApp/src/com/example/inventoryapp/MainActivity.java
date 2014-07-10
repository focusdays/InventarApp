package com.example.inventoryapp;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.inventoryapp.model.PersonModel;
import com.example.inventoryapp.service.PersonLoader;
import com.example.inventoryappbase.core.AsyncResponse;
import com.example.inventoryappbase.core.location.SimpleAddress;

public class MainActivity extends RoboActivity implements AsyncResponse<PersonModel, Void> {

	@InjectView(R.id.personName)
	private TextView personNameText;
	@InjectView(R.id.personId)
	private TextView personIdText;
	@InjectView(R.id.locationAddress)
	private TextView locationAddress;
	@InjectView(R.id.locationZipCity)
	private TextView locationZipCity;
	@InjectView(R.id.locationCountry)
	private TextView locationCountry;
	@InjectView(R.id.locationGPS)
	private TextView locationGPS;

	protected static final int CAMERA_PIC_REQUEST = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new PersonLoader(this, this).loadCurrentPerson();
	}

	public void onEditInventoryClicked(View view) {
		startActivityForResult(new Intent(this, InventoryListActivity.class), 1);

	}

	public void onNewClicked(View view) {
		startActivityForResult(new Intent(this, VideoServer.class), 2);

	}

	@Override
	public void processFinish(PersonModel person) {
		personNameText.setText(person.getPersonName());
		personIdText.setText(person.getPersonId());
		if (person.getLocation() != null){
			SimpleAddress addr = person.getLocation().getAddress();
			locationAddress.setText(addr.getAddress());
			locationZipCity.setText(addr.getZip() +" "+addr.getCity());
			locationCountry.setText(addr.getCountry());
			locationGPS.setText(addr.getPosition().toString());		
		}
		
	}

	@Override
	public void processProgress(Void level) {
		
	}

	@Override
	public void processTime(long timeInMs) {
		
	}

}
