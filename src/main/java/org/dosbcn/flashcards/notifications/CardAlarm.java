package org.dosbcn.flashcards.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.dosbcn.flashcards.data.Card;
import org.dosbcn.flashcards.data.CardService;

/**
 * The alarm, {@link BroadcastReceiver}, element of the app.<br/>
 * Receives boradcasts from {@link AlarmManager} managed alarms when it is time
 * to show a {@link Notification} for a {@link Card}.
 * 
 * @author Sean Connolly
 */
public class CardAlarm extends BroadcastReceiver {

	private static final String LOG_TAG = CardAlarm.class.getSimpleName();

	public static final String CARD_ID_EXTRA = "org.dosbcn.flashcards.card.id";

	private CardService service;
	private CardNotifier notifier;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(LOG_TAG, "Preparing notification alarm.");
		service = new CardService(context);
		notifier = new CardNotifier(context);
		Card card = getCardFromIntent(intent);
		showNotification(card);
		updateStage(card);
	}

	private Card getCardFromIntent(Intent intent) {
		int id = getCardIdFromIntent(intent);
		return service.get(id);
	}

	private int getCardIdFromIntent(Intent intent) {
		int id = intent.getIntExtra(CARD_ID_EXTRA, -1);
		if (id == -1) {
			throw new RuntimeException(CARD_ID_EXTRA
					+ " missing from notification");
		}
		return id;
	}

	private void showNotification(Card card) {
		notifier.showNotification(card);
	}

	private void updateStage(Card card) {
		service.incrementCardStage(card);
	}
}
