package org.dosbcn.flashcards.notifications;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dosbcn.flashcards.data.Card;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;

public class CardAlarmQueueImpl implements CardAlarmQueue {

	private static final String LOG_TAG = CardAlarmQueue.class.getSimpleName();

	private static final DateFormat DATE_FORMAT = SimpleDateFormat
			.getDateTimeInstance();

	private final Context context;
	private final CardNotificationTimer timer;
	private final SparseArray<Date> alarms;

	public CardAlarmQueueImpl(Context context) {
		this.context = context;
		this.timer = new CardNotificationTimerImpl();
		this.alarms = new SparseArray<Date>();
	}

	public Date getAlarm(Card card) {
		return alarms.get(card.getId());
	}

	public void setAlarms(Iterable<Card> cards) {
		for (Card card : cards) {
			setAlarm(card);
		}
	}

	public void setAlarm(Card card) {
		Date notificationTime = timer.getNextNotificationTime(card);
		Log.i(LOG_TAG, "Queueing '" + card.getTitle() + "' card for: "
				+ DATE_FORMAT.format(notificationTime));
		setAlarm(card, notificationTime);
	}

	private void setAlarm(Card card, Date alarmDate) {
		if (card.getId() == null) {
			throw new NullPointerException("Cannot queue an alarm for "
					+ Card.class.getSimpleName() + " without an id.");
		}
		int cardId = card.getId();
		setAlarm(cardId, alarmDate);
	}

	private void setAlarm(int cardId, Date alarmDate) {
		AlarmManager alarmManager = getAlarmManager();
		Intent intent = new Intent(context, CardAlarm.class);
		intent.putExtra(CardAlarm.CARD_ID_EXTRA, cardId);
		alarmManager.set(AlarmManager.RTC_WAKEUP, alarmDate.getTime(),
				PendingIntent.getBroadcast(context, cardId, intent,
						PendingIntent.FLAG_CANCEL_CURRENT));

	}

	private AlarmManager getAlarmManager() {
		return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

}
