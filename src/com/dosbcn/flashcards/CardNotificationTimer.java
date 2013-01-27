package com.dosbcn.flashcards;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.util.Log;

import com.dosbcn.flashcards.data.Card;

/**
 * Generates times that notifications should be scheduled for in the future.<br/>
 * There are a couple of important factors here:
 * <ol>
 * <li>
 * <b>Times are random:</b> we don't want a user to always receive reminders at
 * 1:37pm. By making the notifications occur randomly, the user is forced to not
 * think about the message until it appears.</li>
 * <li>
 * <b>Times are not random:</b> we don't want to schedule a notification for
 * 00:42am or for one year from now.</li>
 * <li>
 * <b>Times are staged:</b> we want notifications to be sent in stages. For
 * example: a simple reminder tomorrow, a simple quiz next week, a hard quiz
 * next month.</li>
 * </ol>
 * 
 * @see {@link CardNofifier}
 * @see {@link CardNotificationStage}
 * 
 * @author Sean Connolly
 */
public class CardNotificationTimer {

	private static final String LOG_TAG = CardNotificationTimer.class
			.getSimpleName();

	private static final String BAD_STAGE_LOG = "Unexpected "
			+ CardNotificationStage.class.getSimpleName() + ": ";

	private final RandomTime randomTimeGenerator = new RandomTime();

	/**
	 * Returns the next time that a notification should be sent for this
	 * {@link Card}.<br/>
	 * The time is guaranteed to be within the range of 'Ok times'.
	 * 
	 * @param card
	 * @return
	 */
	public Date getNextNotificationTime(Card card) {
		return getNotificationTime(card.getStage(), card.getStartDate());
	}

	/**
	 * Generate a random time to send the notification, appropriate for the
	 * given stage and origin date.
	 */
	private Date getNotificationTime(CardNotificationStage stage,
			Date originDate) {
		switch (stage) {
		case ONE_DAY:
			return getNotificationTimeOneDayAfterDate(originDate);
		default:
			Log.e(LOG_TAG, BAD_STAGE_LOG + stage);
			return null;
		}
	}

	/**
	 * Generates a random time to send the notification, roughly one day from
	 * the given origin date.<br/>
	 * If that is not possible because the origin date is too far in the past, a
	 * random time within the next day or two is returned.<br/>
	 * Notification times are guaranteed to not clash with others already
	 * scheduled.
	 * 
	 * @param originDate
	 * @return
	 */
	private Date getNotificationTimeOneDayAfterDate(Date originDate) {
		// First, check if the origin date is too far in the past
		// The origin date is within limits, let's generate a random date
		TimeAdjustment adjustment = new TimeAdjustment(Calendar.HOUR, 24);
		Date tomorrow = addTime(originDate, adjustment);
		Date notificationTime = getNotificationTimeOnDate(tomorrow);
		return notificationTime;
	}

	/**
	 * Returns an appropriate notification time on the date specified.
	 * <p>
	 * Note that all time information is stripped from the {@link Date} provided
	 * prior to calculating a the notification time. Thus, this function may
	 * return a time before, exactly equal to, or after the provided date.
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	private Date getNotificationTimeOnDate(Date date) {
		// Generate a random time during the day
		Date day = stripTimeFromDate(date);
		int randomTimeMillis = randomTimeGenerator.nextTime();
		TimeAdjustment adjustment = new TimeAdjustment(Calendar.MILLISECOND,
				randomTimeMillis);
		Date notificationTime = addTime(day, adjustment);
		return notificationTime;
	}

	/**
	 * Takes a {@link Date} and removes all time information from it, stripping
	 * it down to just the day.
	 * 
	 * @param date
	 *            the date object with or without time information
	 * @return the date object, without time information
	 */
	private Date stripTimeFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	private Date addTime(Date date, TimeAdjustment... adjustments) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for (TimeAdjustment adjustment : adjustments) {
			cal.add(adjustment.field, adjustment.amount);
		}
		return cal.getTime();
	}

	private class TimeAdjustment {
		public final int field;
		public final int amount;

		public TimeAdjustment(int field, int amount) {
			super();
			this.field = field;
			this.amount = amount;
		}

	}

	private class RandomTime {

		private static final int SECOND_MS = 1000;
		private static final int MINUTE_MS = 60 * SECOND_MS;
		private static final int HOUR_MS = 60 * MINUTE_MS;
		// Don't notify anyone earlier than 11:00 or later than 21:00
		// TODO make configurable
		private static final int EARLIEST_NOTIFICATION_TIME_MS = 11 * HOUR_MS;
		private static final int LATEST_NOTIFICATION_TIME_MS = 21 * HOUR_MS;

		private final Random randomGenerator = new Random();

		public int nextTime() {
			return randomGenerator.nextInt(LATEST_NOTIFICATION_TIME_MS
					- EARLIEST_NOTIFICATION_TIME_MS)
					+ EARLIEST_NOTIFICATION_TIME_MS;
		}

	}
}
