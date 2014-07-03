package com.example.inventoryappbase.core.ean;

import android.os.AsyncTask;

import com.example.inventoryappbase.core.AsyncResponse;

public class EANSearchAnalyzeAsyncTask extends AsyncTask<String, Integer, String> {
	
	private AsyncResponse<String, Integer> response;
	public EANSearchAnalyzeAsyncTask(AsyncResponse<String, Integer> response) {
		this.response = response;
	}
	@Override
	protected String doInBackground(String... string) {
		return new EANSerchHelper(string[0]).getTitle();
	}
	@Override
	protected void onPostExecute(String result) {
		response.processFinish(result);
	}
	
	
}