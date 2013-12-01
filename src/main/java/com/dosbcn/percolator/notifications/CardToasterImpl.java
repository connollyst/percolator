package com.dosbcn.percolator.notifications;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Displays toast messages for various actions in the app.
 *
 * @author Sean Connolly
 */
public class CardToasterImpl implements CardToaster {

	private static final int DURATION = Toast.LENGTH_SHORT;

	/* Compose messages of a prefix and a suffix giving us some variation */
	private static final String[] SAVED_MESSAGE_PREFIXES = { "Great,\n",
			"Cool,\n", "Alright,\n", "Alright then,\n", "Got it!\n",
			"Good one!\n" };
	private static final String[] SAVED_MESSAGE_SUFFIXES = {
			"Ill remind you soon.", "Ill remind you tomorrow.",
			"Let's start tomorrow!", "We'll get started tomorrow." };
	private static final String[] SAVED_MESSAGES;
	static {
		List<String> messages = new ArrayList<String>();
		for (String prefix : SAVED_MESSAGE_PREFIXES) {
			for (String suffix : SAVED_MESSAGE_SUFFIXES) {
				messages.add(prefix + suffix);
			}
		}
		SAVED_MESSAGES = messages.toArray(new String[messages.size()]);
	}

	private final Context context;
	private final Random random;

	/**
	 * Construct a new card toaster in the given context.
	 *
	 * @param context
	 *            the app context
	 */
	public CardToasterImpl(Context context) {
		this.context = context;
		this.random = new Random();
	}

	/**
	 * {@inheritDoc}
	 */
	public void cardSaved() {
		toast(anyMessage(SAVED_MESSAGES));
	}

	/**
	 * Show a toast.
	 *
	 * @param text
	 *            the text to toast
	 */
	private void toast(String text) {
		Toast toast = Toast.makeText(context, text, DURATION);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * Choose a random message from the given list.
	 *
	 * @param messages
	 *            the list of messages to choose from
	 * @return a random message from the list
	 */
	public String anyMessage(String[] messages) {
		int index = random.nextInt(messages.length);
		return messages[index];
	}

}
