package com.example.inventoryappbase.core.speech;

import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

public class Text2SpeechOnClickListener implements
		View.OnClickListener {
	private TextView view;
	private TextToSpeech tts;

	public Text2SpeechOnClickListener(TextToSpeech tts, TextView view) {
		this.tts = tts;
		this.view = view;

	}

	@Override
	public void onClick(View v) {
		tts.speak(view.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
	}
}