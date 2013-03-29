package org.dosbcn.flashcards.notifications;

import org.dosbcn.flashcards.data.Card;
import org.dosbcn.flashcards.data.CardService;
import org.dosbcn.flashcards.data.CardServiceImpl;

import android.app.AlarmManager;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * The alarm, {@link BroadcastReceiver}, element of the app.<br/>
 * Receives boradcasts from {@link AlarmManager} managed alarms when it is time
 * to show a {@link Notification} for a {@link Card}.
 * 
 * @author Sean Connolly
 */
public class CardAlarm extends BroadcastReceiver {

	private static final String LOG_TAG = CardAlarm.class.getSimpleName();

	protected static final String ERROR_NULL_INTENT = CardAlarm.class
			.getSimpleName() + " received an alarm with a null intent.";
	protected static final String ERROR_BAD_INTENT = CardAlarm.class
			.getSimpleName() + " received an alarm with an invalid intent: ";

	private CardService service;
	private CardNotifier notifier;

	@Override
	public void onReceive(Context context, Intent intent) {
		CardAlarmIntent cardIntent = castIntent(intent);
		handleAlarm(cardIntent);
	}

	protected void initialize(Context context) {
		Log.i(LOG_TAG, "Preparing notification alarm.");
		service = new CardServiceImpl(context);
		notifier = new CardNotifierImpl(context);
	}

	protected void handleAlarm(CardAlarmIntent intent) {
		Card card = getCardFromIntent(intent);
		showNotification(card);
		updateStage(card);
	}

	private Card getCardFromIntent(CardAlarmIntent intent) {
		int cardId = intent.getCardId();
		return service.get(cardId);
	}

	private void showNotification(Card card) {
		notifier.showNotification(card);
	}

	private void updateStage(Card card) {
		service.incrementCardStage(card);
	}

	private CardAlarmIntent castIntent(Intent intent) {
		if (intent == null) {
			throw new NullPointerException(ERROR_NULL_INTENT);
		} else if (intent instanceof CardAlarmIntent) {
			return (CardAlarmIntent) intent;
		} else {
			throw new ClassCastException(ERROR_BAD_INTENT + intent.getClass());
		}
	}

	public CardService getService() {
		return service;
	}

	protected void setService(CardService service) {
		this.service = service;
	}

	public CardNotifier getNotifier() {
		return notifier;
	}

	protected void setNotifier(CardNotifier notifier) {
		this.notifier = notifier;
	}
}
