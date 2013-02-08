package com.dosbcn.flashcards.notifications;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class CardToaster {

	private static final int DURATION = Toast.LENGTH_SHORT;

	private static final String SAVED_MESSAGE = "Got it!";

	private final Context context;

	public CardToaster(Context context) {
		this.context = context;
	}

	public void cardSaved() {
		toast(SAVED_MESSAGE);
	}

	private void toast(String text) {
		Toast toast = Toast.makeText(context, text, DURATION);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
