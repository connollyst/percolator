package com.dosbcn.percolator.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.dosbcn.percolator.data.Card;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * The primary implementation of the {@link CardAlarmQueue}. Adding a
 * {@link Card} to the alarm queue immediately schedules an Android alarm for
 * the next notification time. Alarms are handled by the {@link CardAlarm}
 * broadcast receiver.<br/>
 * Note that only one alarm may exist for a card at any time. Adding a card to
 * the queue twice will not schedule two alarms; instead, the first will be
 * blasted away by the second.
 *
 * @author Sean Connolly
 */
public class CardAlarmQueueImpl implements CardAlarmQueue {

	private static final String LOG_TAG = CardAlarmQueue.class.getName();
	private static final DateTimeFormatter DATE_FORMAT = ISODateTimeFormat
			.dateTime();
	private final Context context;

	public CardAlarmQueueImpl(Context context) {
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAlarms(Iterable<Card> cards) {
		for (Card card : cards) {
			setAlarm(card);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAlarm(Card card) {
		DateTime notificationTime = card.getNextNotificationDateTime();
		Log.i(LOG_TAG, "Queueing '" + card.getTitle() + "' card for: "
				+ DATE_FORMAT.print(notificationTime));
		setAlarm(card, notificationTime);
	}

	/**
	 * Set an Android alarm for the specified {@code alarmDate}. The alarm will
	 * be handled by the {@link CardAlarm} broadcast receiver who will display a
	 * notification for the specified {@code card}.
	 *
	 * @param card
	 *            the card the notification will display
	 * @param alarmDate
	 *            the date and time the alarm should go off
	 */
	protected void setAlarm(Card card, DateTime alarmDate) {
		if (card.getId() == null) {
			throw new NullPointerException("Cannot queue an alarm for a "
					+ Card.class.getSimpleName() + " without an id.");
		}
		AlarmManager alarmManager = getAlarmManager();
		Intent intent = new CardIntent(context, CardAlarm.class, card);
		alarmManager.set(AlarmManager.RTC_WAKEUP, alarmDate.getMillis(),
				PendingIntent.getBroadcast(context, card.getId(), intent,
						PendingIntent.FLAG_CANCEL_CURRENT));
	}

	/**
	 * Get the {@link AlarmManager} service from the Android {@link Context}.
	 *
	 * @return the alarm service provided by Android
	 */
	protected AlarmManager getAlarmManager() {
		return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

}
