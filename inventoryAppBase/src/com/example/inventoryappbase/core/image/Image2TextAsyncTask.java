package com.example.inventoryappbase.core.image;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.example.inventoryappbase.core.AsyncResponse;
import com.example.inventoryappbase.core.JsonHelper;

public class Image2TextAsyncTask extends AsyncTask<Image2TextPresentationModel, Image2TextPresentationModel, Image2TextPresentationModel[]> {
	
	private boolean deleteAfterDetection;
	private AsyncResponse<Image2TextPresentationModel, Image2TextPresentationModel> response;

	public Image2TextAsyncTask(Context context, AsyncResponse<Image2TextPresentationModel, Image2TextPresentationModel> response, boolean deleteAfterDetection) {
		super();
		this.response = response;
		this.deleteAfterDetection = deleteAfterDetection;
	}


	@Override
	protected Image2TextPresentationModel[] doInBackground(Image2TextPresentationModel... models) {
		for (Image2TextPresentationModel model : models) {
			try {
				ImageHelper image = model.createImage();
				ImageHelper smallImage = model.createSmallImage(image);
				this.publishProgress(model);
				JsonHelper json = new JsonHelper();
				JSONObject image2TextResponse = json.getJSON(smallImage.detectKeywords());
				model.setKeywords((String)json.evalExpression(image2TextResponse, "keywords"));
				
				JSONArray similarImages = json.evalArrayExpression(image2TextResponse, "similarImages");
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < similarImages.length(); i++) {
					buffer.append(json.evalExpression(similarImages.getJSONObject(i), "pt"));
					buffer.append("\n");
				}
				model.setSimilarKeywords(buffer.toString());
//				Toast.makeText(this.context, "tagged picture <" + model.getKeywords() +"> ("+model.getImageSmallUri()+")", Toast.LENGTH_SHORT).show();
				if (this.deleteAfterDetection) {
					new File(model.getImageSmallFullName()).delete();
				}
			} catch (Exception e) {
//				Toast.makeText(this.context, "error tagging picture: "+e.getMessage(), Toast.LENGTH_LONG).show();
				model.setKeywords("none");
				if (this.deleteAfterDetection) {
					new File(model.getImageSmallFullName()).delete();
				}
			}
		}
		return models;
	}

	@Override
	protected void onPostExecute(Image2TextPresentationModel[] models) {
		for (Image2TextPresentationModel model : models) {
//			Toast.makeText(this.context, "tagged picture <" + model.getKeywords() +"> ("+model.getImageSmallUri()+")", Toast.LENGTH_SHORT).show();
			response.processFinish(model);
		}
	}

	@Override
	protected void onProgressUpdate(Image2TextPresentationModel... values) {
		this.response.processProgress(values[0]);
	}
}