package com.example.inventoryapp;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.inventoryapp.model.PersonModel;
import com.example.inventoryapp.service.PersonLoader;

public class MainActivity extends RoboActivity {

	@InjectView(R.id.personName) private TextView personNameText;
	@InjectView(R.id.personId) private TextView personIdText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		PersonModel personModel = new PersonLoader().getPerson(this);
		
		personNameText.setText(personModel.getPersonName());

		personIdText.setText(personModel.getPersonId());

	}
	
	public void onEditInventoryClicked(View view){
		startActivityForResult(new Intent(this, InventoryListActivity.class), 1);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
