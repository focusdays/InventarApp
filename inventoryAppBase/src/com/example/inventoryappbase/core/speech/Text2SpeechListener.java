package com.example.inventoryappbase.core.speech;

import java.util.Locale;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

public class Text2SpeechListener implements
		TextToSpeech.OnInitListener {

	private Locale defaultLocale;
	private View ViewSpeak;
	private TextToSpeech tts;

	public Text2SpeechListener(View ViewSpeak, Locale defaultLocale) {
		this.ViewSpeak = ViewSpeak;
		this.defaultLocale = defaultLocale;
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = tts.setLanguage(defaultLocale);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {
				ViewSpeak.setEnabled(true);
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}

	public void setTTS(TextToSpeech tts) {
		this.tts = tts;
	}

}