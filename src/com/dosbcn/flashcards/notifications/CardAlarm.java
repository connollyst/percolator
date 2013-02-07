package com.dosbcn.flashcards.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dosbcn.flashcards.data.Card;
import com.dosbcn.flashcards.data.CardService;

/**
 * The alarm, {@link BroadcastReceiver}, element of the app.<br/>
 * Receives boradcasts from {@link AlarmManager} managed alarms when it is time
 * to show a {@link Notification} for a {@link Card}.
 * 
 * @author Sean Connolly
 */
public class CardAlarm extends BroadcastReceiver {

	private static final String LOG_TAG = CardAlarm.class.getSimpleName();

	public static final String CARD_ID_EXTRA = "com.dosbcn.flashcards.card.id";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(LOG_TAG, "Preparing notification alarm.");
		Card card = getCardExtra(context, intent);
		showNotification(context, card);
	}

	private Card getCardExtra(Context context, Intent intent) {
		int id = getCardIdExtra(intent);
		CardService service = new CardService(context);
		Card card = service.get(id);
		return card;
	}

	private int getCardIdExtra(Intent intent) {
		int id = intent.getIntExtra(CARD_ID_EXTRA, -1);
		if (id == -1) {
			throw new RuntimeException("com.dosbcn.flashcards.card.id missing");
		}
		return id;
	}

	private void showNotification(Context context, Card card) {
		CardNotifier notifier = new CardNotifier(context);
		notifier.showNotification(card);
	}

}
