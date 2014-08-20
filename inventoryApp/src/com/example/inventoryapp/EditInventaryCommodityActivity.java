package com.example.inventoryapp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inventoryapp.model.odata.Commodity;
import com.example.inventoryapp.model.odata.Inventory;
import com.example.inventoryapp.model.odata.consumer.ODataConsumerInventory;
import com.example.inventoryappbase.core.speech.Speech2TextOnClickListener;
import com.example.inventoryappbase.core.speech.Text2SpeechListener;
import com.example.inventoryappbase.core.speech.Text2SpeechOnClickListener;

public class EditInventaryCommodityActivity extends RoboActivity {

	protected static final int RESULT_SPEAK_PRICE = 10;
	protected static final int RESULT_SPEAK_NAME = 11;

	private List<Commodity> commodities;
	private Commodity selectedCommodity;
	private Inventory inventory;
	private Locale defaultLocale = new Locale("de", "DE");

	private TextToSpeech ttsPrice;
	private TextToSpeech ttsName;

	@InjectView(R.id.btnSpeakPrice)
	private ImageView btnSpeakPrice;
	@InjectView(R.id.textPrice)
	private EditText textPrice;
	@InjectView(R.id.btnListenPrice)
	private View btnListenPrice;

	@InjectView(R.id.btnSpeakName)
	private ImageView btnSpeakName;
	@InjectView(R.id.textName)
	private EditText textName;
	@InjectView(R.id.btnListenName)
	private View btnListenName;

	@InjectView(R.id.textTitel)
	private TextView inventaryPrices;
	@InjectView(R.id.textType)
	private EditText textType;

	@InjectView(R.id.imagePreview)
	private ImageView imagePreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_inventory_main);


		btnSpeakPrice.setOnClickListener(new Speech2TextOnClickListener(this,
				defaultLocale, RESULT_SPEAK_PRICE));
		Text2SpeechListener text2SpeechListenerPrice = new Text2SpeechListener(
				btnListenPrice, defaultLocale);
		ttsPrice = new TextToSpeech(this, text2SpeechListenerPrice);
		text2SpeechListenerPrice.setTTS(ttsPrice);
		btnListenPrice.setOnClickListener(new Text2SpeechOnClickListener(
				ttsPrice, textPrice));

		btnSpeakName.setOnClickListener(new Speech2TextOnClickListener(this,
				defaultLocale, RESULT_SPEAK_NAME));
		Text2SpeechListener text2SpeechListenerName = new Text2SpeechListener(
				btnListenName, defaultLocale);
		ttsName = new TextToSpeech(this, text2SpeechListenerName);
		text2SpeechListenerName.setTTS(ttsName);
		btnListenName.setOnClickListener(new Text2SpeechOnClickListener(
				ttsName, textName));

		

		inventory = ODataConsumerInventory.getInstance().getPerson().getInventory();
		commodities = inventory.getCommodities();
		
		Bundle b = getIntent().getExtras();
		int index = b.getInt("commodityIndex", 0);
		setSelectedCommodity(commodities.get(index));		
		inventaryPrices.setText(getInventaryPricesText());
		textPrice.setText("" + getSelectedCommodity().getCommodityPrice());
		textType.setText("" + getSelectedCommodity().getCommodityType());
		textName.setText(getSelectedCommodity().getCommodityTitle());
		
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (ttsPrice != null) {
			ttsPrice.stop();
			ttsPrice.shutdown();
		}
		if (ttsName != null) {
			ttsName.stop();
			ttsName.shutdown();
		}
		super.onDestroy();
	}

	private String getInventaryPricesText() {
		StringBuilder b = new StringBuilder();
		BigDecimal summe = new BigDecimal(0);
		for (Commodity c : commodities) {
			summe.add(c.getCommodityPrice());
		}
		b.append(summe.toString());
		b.append(" CHF");
		return b.toString();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		imagePreview.setImageURI(
				Uri.parse(getSelectedCommodity().getCommodityPicture()));

		switch (requestCode) {
		case RESULT_SPEAK_PRICE: {
			if (resultCode == RESULT_OK && null != data) {
				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				textPrice.setText(this.extractNumbers(text.get(0)));
			}
			break;
		}
		case RESULT_SPEAK_NAME: {
			if (resultCode == RESULT_OK && null != data) {
				ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				String string = text.get(0);
				if (string != null && string.length() > 0) {
					textName.setText(string);
				}
			}
			break;
		}
		}
	}

	private CharSequence extractNumbers(String string) {
		if (string != null) {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < string.length(); i++) {
				if (Character.isDigit(string.charAt(i))) {
					buffer.append(string.charAt(i));
				}
			}
			string = buffer.toString();
		}
		return string;
	}

	public Commodity getSelectedCommodity() {
		return selectedCommodity;
	}

	public void setSelectedCommodity(Commodity selectedCommodity) {
		this.selectedCommodity = selectedCommodity;
	}
}
