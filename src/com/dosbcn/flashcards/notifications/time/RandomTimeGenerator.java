package com.dosbcn.flashcards.notifications.time;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.util.Log;

public class RandomTimeGenerator {

	private static final String LOG_TAG = RandomTimeGenerator.class
			.getSimpleName();

	private static final int EARLIEST_NOFITICATION_HOUR = 11;
	private static final int LATEST_NOFITICATION_HOUR = 21;

	private final RandomTime randomTimeGenerator = new RandomTime();

	public Date getRandomTimeASAP() {
		Log.i(LOG_TAG, "Getting notification ASAP.");
		Date now = Calendar.getInstance().getTime();
		Date latest = getLatestNotificationTimeOnDate(now);
		if (now.before(latest)) {

		}
		Date notificationTime = getRandomTimeInDay(now);
		return notificationTime;
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
	public Date getRandomTimeOneDayFromDate(Date originDate) {
		Date oneDayFromOrigin = TimeAdjustor.addDay(originDate);
		return getRandomTimeInDay(oneDayFromOrigin);
	}

	public Date getRandomTimeOneWeekFromDate(Date originDate) {
		// TODO
		// Pick a day about one week from the start day
		// Keep adding days until not already passed
		Date oneWeekFromOrigin = TimeAdjustor.addWeek(originDate);
		return getRandomTimeInDay(oneWeekFromOrigin);
	}

	public Date getRandomTimeOneMonthFromDate(Date originDate) {
		// TODO
		// Pick a day about one month from the start day
		// Keep adding days until not already passed
		Date oneMonthFromOrigin = TimeAdjustor.addMonth(originDate);
		return getRandomTimeInDay(oneMonthFromOrigin);
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
	private Date getRandomTimeInDay(Date date) {
		// Generate a random time during the day
		Date day = TimeAdjustor.stripTimeFromDate(date);
		int randomTimeMillis = randomTimeGenerator.nextTime();
		TimeAdjustment adjustment = new TimeAdjustment(Calendar.MILLISECOND,
				randomTimeMillis);
		Date notificationTime = TimeAdjustor.addTime(day, adjustment);
		return notificationTime;
	}

	/**
	 * Return a {@link Date} of the <i>earliest time</i> of the day a
	 * notification is allowed to be sent, on the specified date.
	 * 
	 * @param date
	 *            the date in question
	 * @return the earliest a notification may be sent on that day
	 */
	public Date getEarliestNotificationTimeOnDate(Date date) {
		Date day = TimeAdjustor.stripTimeFromDate(date);
		Date earliest = TimeAdjustor.addTime(day, new TimeAdjustment(
				Calendar.HOUR_OF_DAY, EARLIEST_NOFITICATION_HOUR));
		return earliest;
	}

	/**
	 * Return a {@link Date} of the <i>latest time</i> of the day a notification
	 * is allowed to be sent, on the specified date.
	 * 
	 * @param date
	 *            the date in question
	 * @return the latest a notification may be sent on that day
	 */
	public Date getLatestNotificationTimeOnDate(Date date) {
		Date day = TimeAdjustor.stripTimeFromDate(date);
		Date latest = TimeAdjustor.addTime(day, new TimeAdjustment(
				Calendar.HOUR_OF_DAY, LATEST_NOFITICATION_HOUR));
		return latest;
	}

	private class RandomTime {

		private static final int SECOND_MS = 1000;
		private static final int MINUTE_MS = 60 * SECOND_MS;
		private static final int HOUR_MS = 60 * MINUTE_MS;
		// Don't notify anyone earlier than 11:00 or later than 21:00
		// TODO make configurable
		private static final int EARLIEST_NOTIFICATION_MS = EARLIEST_NOFITICATION_HOUR
				* HOUR_MS;
		private static final int LATEST_NOTIFICATION_MS = LATEST_NOFITICATION_HOUR
				* HOUR_MS;

		private final Random randomGenerator = new Random();

		public int nextTime() {
			return randomGenerator.nextInt(LATEST_NOTIFICATION_MS
					- EARLIEST_NOTIFICATION_MS)
					+ EARLIEST_NOTIFICATION_MS;
		}

	}

}
