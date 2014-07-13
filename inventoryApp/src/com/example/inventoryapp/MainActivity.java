package com.example.inventoryapp;

import java.util.ArrayList;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.inventoryapp.model.CommodityModel;
import com.example.inventoryapp.model.InventoryModel;
import com.example.inventoryapp.model.PersonModel;
import com.example.inventoryapp.service.PersonLoader;
import com.example.inventoryappbase.core.AsyncResponse;
import com.example.inventoryappbase.core.image.Image2TextAsyncTask;
import com.example.inventoryappbase.core.image.Image2TextPresentationModel;
import com.example.inventoryappbase.core.location.SimpleAddress;

public class MainActivity extends RoboActivity implements AsyncResponse<PersonModel, Void> {

	public final int ACTION_CAPTURE = 1;
	public final int ACTION_LIST = 2;
	
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
		startActivityForResult(new Intent(this, InventoryListActivity.class), ACTION_LIST);

	}

	public void onNewClicked(View view) {
		startActivityForResult(new Intent(this, VideoServer.class), ACTION_CAPTURE);

	}

	@Override
	public void processFinish(PersonModel person) {
		personNameText.setText(person.getPersonName());
		personIdText.setText(person.getPersonId());
		if (person.getLocation() != null){
			SimpleAddress addr = person.getLocation().getAddress();
			locationAddress.setText(addr.getAddress());
			locationZipCity.setText(addr.getCity());
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
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case ACTION_LIST: {
				if (resultCode == RESULT_OK && null != data) {
					InventoryModel singleInventary = PersonModel.getPersonInstance().getSingleInventary();
					ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra("captured_images");
					if (stringArrayListExtra != null) {
						for (String pict : stringArrayListExtra) {
							CommodityModel newModel = singleInventary.addCommodity(new CommodityModel());
							newModel.setCommodityPicture(pict);
							
						}
					}
				}
				break;
			}
			case ACTION_CAPTURE: {
				if (resultCode == RESULT_OK && null != data) {
					
				}
			}
		}
	}
	
}
