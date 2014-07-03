package com.example.inventoryapp.service;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.inventoryapp.model.PersonModel;

public class PersonLoader implements IPersonLoader {

	private PersonModel person;


	public PersonModel getPerson(Activity activity) {
		
		person = PersonModel.getPersonInstance();
		
		Cursor c = activity.getContentResolver().query(
				ContactsContract.Profile.CONTENT_URI, null, null, null, null);
		
		c.moveToFirst();
		
		person.setPersonId(c.getString(c.getColumnIndex(ContactsContract.Contacts._ID)));
		person.setPersonName(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
		
		c.close();
		
		return person;
	}
}
