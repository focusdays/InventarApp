package com.example.inventoryappbase.core.ean;

import java.io.IOException;
import java.net.URLEncoder;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import android.util.Log;

public class EANSerchHelper {
	final static String EAN_SEARCH_URL = "http://inventory42-focusdays14.rhcloud.com/EANSearch?q=";

	private String barcode;

	public EANSerchHelper(String barcode) {
		this.barcode = barcode;
	}

	public String getTitle() {
		try {
			Response response = this.getHTML(this.getBarcode());
			return this.getEANTitle(response);
		} catch (IOException e) {
			Log.i("barcode", e.getMessage(), e);
			return "";
		}
	}

	private String getEANTitle(Response response) throws IOException {
		if (response.statusCode() == 200) {
			return response.body();
		} else {
			throw new IOException ("EAN not available");
		}
	}

	private Response getHTML(String imageURL) throws IOException {
		return Jsoup.connect(EAN_SEARCH_URL + URLEncoder.encode(imageURL))
				.header("Cache-Control","max-age=0")
				.execute();
	}

	public String getBarcode() {
		return barcode;
	}
}
