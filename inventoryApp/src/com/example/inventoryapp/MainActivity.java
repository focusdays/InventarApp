package com.example.inventoryapp;

import java.math.BigDecimal;
import java.util.ArrayList;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inventoryapp.model.odata.Commodity;
import com.example.inventoryapp.model.odata.Inventory;
import com.example.inventoryapp.model.odata.Person;
import com.example.inventoryapp.model.odata.consumer.ODataConsumerInventory;
import com.example.inventoryapp.service.PersonLoader;
import com.example.inventoryappbase.core.AsyncResponse;
import com.example.inventoryappbase.core.image.Image2TextAsyncTask;
import com.example.inventoryappbase.core.image.Image2TextPresentationModel;
import com.example.inventoryappbase.core.location.SimpleAddress;

public class MainActivity extends RoboActivity implements AsyncResponse<Person, Void> {

	public final int ACTION_CAPTURE = 1;
	public final int ACTION_LIST = 2;
	public final int ACTION_IMPORT = 3;
	
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
	@InjectView(R.id.textInProgress)
	private TextView textInProgress;
	
	@InjectView(R.id.imagePreview)
	private ImageView imagePreview;
	
	@InjectView(R.id.buttonUpdateLocation)
	private ImageButton buttonUpdateLocation;
	@InjectView(R.id.buttonNew)
	private ImageButton buttonNew;
	@InjectView(R.id.buttonImport)
	private ImageButton buttonImport;
	@InjectView(R.id.buttonEdit)
	private ImageButton buttonEdit;

	Person person;
	private Image2TextPresentationModel image2TextModel;

	
	protected static final int CAMERA_PIC_REQUEST = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		enableActionButtons(false);

		person = ODataConsumerInventory.getInstance().getPerson();
		if (person == null) {
			this.tag("get Google account + load data via ODATA services ...");
			new PersonLoader(this, this).loadCurrentPerson();
		} else {
			enableActionButtons(true);
		}
	}

	public void onEditInventoryClicked(View view) {
		startActivityForResult(new Intent(this, InventoryListActivity.class), ACTION_LIST);
	}

	public void onNewClicked(View view) {
		startActivityForResult(new Intent(this, VideoServer.class), ACTION_CAPTURE);
	}
	public void onImportClicked(View view) {
    	Image2TextPresentationModel model = new Image2TextPresentationModel(this);
    	this.image2TextModel = model;
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, ACTION_IMPORT);
	}
	public void updateCurrentLocation(View view) {
		this.tag("update current location via Google Location API ...");
		new PersonLoader(this, this).getCurrentLocation(this.person);
	}

	private void tag(String string) {
		textInProgress.setText(string);
	}

	@Override
	public void processFinish(Person person) {
		this.tag("... done");
		ODataConsumerInventory.getInstance().setPerson(person);
		this.person = person;
		personNameText.setText(person.getPersonName());
		personIdText.setText(person.getPersonID());
		if (person.getLocation() != null && person.getLocation().getAddress() != null){
			SimpleAddress addr = person.getLocation().getAddress();
			locationAddress.setText(addr.getAddress());
			locationZipCity.setText(addr.getZip().isEmpty() ? addr.getCity() : addr.getZip()+" "+addr.getCity());
			locationCountry.setText(addr.getCountry());
			locationGPS.setText(addr.getPosition().toString());
		}
		enableActionButtons(true);
		
	}

	private void enableActionButtons(boolean b) {
		buttonUpdateLocation.setEnabled(b);
		buttonNew.setEnabled(b);
		buttonImport.setEnabled(b);
		buttonEdit.setEnabled(b);
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
					Inventory singleInventary = person.getInventory();
				}
				break;
			}
			case ACTION_CAPTURE: {
				if (resultCode == RESULT_OK && null != data) {
					Inventory singleInventary = person.getInventory();
					/*
					Intent result = new Intent();
	        		result.putExtra("newIndexStart", 0);
	        		result.putExtra("newIndexLenght", capturedImages.size());
	        		result.putParcelableArrayListExtra("newCommodities", capturedImages);
					 */
					ArrayList<Parcelable> parcelableArrayListExtra = data.getParcelableArrayListExtra("newCommodities");
					if (parcelableArrayListExtra != null && !parcelableArrayListExtra.isEmpty()) {
						for (Parcelable parcelable : parcelableArrayListExtra) {
							Commodity newCommodity = (Commodity)parcelable;
							newCommodity.setInventory(singleInventary);
							singleInventary.getCommodities().add(newCommodity);
						}
						singleInventary.updateInventoryTotalPrice();
					}
				}
				break;
			}
			case ACTION_IMPORT: {
				if (resultCode == RESULT_OK && null != data) {
					this.enableActionButtons(false);
					this.image2TextModel.setImageOriginalFullName(this.getPath(data.getData()));
					image2Text(this.image2TextModel, false);
				}
				break;
			}

		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		int ot = getResources().getConfiguration().orientation;
		switch (ot) {
			case Configuration.ORIENTATION_LANDSCAPE: {
				if (this.image2TextModel != null && this.image2TextModel.hasValidImage()) {
					imagePreview.setImageURI(this.image2TextModel.getImageSmallUri());
				}
			}
			case Configuration.ORIENTATION_PORTRAIT: {
				if (this.image2TextModel != null && this.image2TextModel.hasValidImage()) {
					imagePreview.setImageURI(this.image2TextModel.getImageSmallUri());
				}
			}
		}
	}
	private void image2Text(Image2TextPresentationModel model, boolean deleteIfProcessed) {
		AsyncResponse<Image2TextPresentationModel, Image2TextPresentationModel> response = new AsyncResponse<Image2TextPresentationModel, Image2TextPresentationModel>() {
			@Override
			public void processFinish(Image2TextPresentationModel model) {
				image2TextModel = model;
				tag("keywords detected: "+model.getKeywords());
				Commodity commodity = new Commodity();
				commodity.setCommodityPicture(model.getImageURL());
				commodity.setCommodityTitle(model.getKeywords());
				commodity.setCommodityPrice(new BigDecimal(100));
				commodity.setInventory(person.getInventory());
				person.getInventory().addCommodity(commodity);
				person.getInventory().updateInventoryTotalPrice();
	            enableActionButtons(true);

			}
			@Override
			public void processTime(long timeInMs) {
			}
			@Override
			public void processProgress(Image2TextPresentationModel model) {
				tag("keywords detecting ...");
	            imagePreview.setImageURI(model.getImageSmallUri());
			}
		};
		this.tag("uploading image file ...");
		new Image2TextAsyncTask(this, response, false).execute(model);
	}

	private String getPath(Uri uri) {
		if (uri != null) {
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
	
			Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
			if (cursor != null) {
				try {
					cursor.moveToFirst();
					int columnIndex = cursor.getColumnIndexOrThrow(filePathColumn[0]);
					return cursor.getString(columnIndex);
				} finally {
					cursor.close();
				}
			} else {
				return uri.getPath();
			}
		} else {
			return null;
		}
	}
	
}
