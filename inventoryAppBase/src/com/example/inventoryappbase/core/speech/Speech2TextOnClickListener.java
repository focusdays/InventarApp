package com.example.inventoryappbase.core.speech;

import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

public class Speech2TextOnClickListener implements
		View.OnClickListener {

	private int requestCode;
	private Locale locale;
	private Activity activity;

	public Speech2TextOnClickListener(Activity activity, Locale locale, int requestCode) {
		this.activity = activity;
		this.locale = locale;
		this.requestCode = requestCode;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				locale.toString());
		try {
			this.activity.startActivityForResult(intent, requestCode);
		} catch (ActivityNotFoundException a) {
			Toast t = Toast.makeText(this.activity.getApplicationContext(),
					"Ops! Your device doesn't support Speech to Text",
					Toast.LENGTH_SHORT);
			t.show();
		}
	}
}