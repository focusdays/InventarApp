package com.example.inventoryappbase.core.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Pair;

public class ListProfileAsyncTask extends AsyncTask<Void, Void, Map<String, String>> {

    private Activity activity;

	public ListProfileAsyncTask(Activity act) {
        this.activity = act;
    }
	protected Map<String, String> doInBackground(Void... params) {
		Cursor c = activity.getContentResolver().query(
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

	protected void onPostExecute(List<Pair> result) {
		
	}
}