package org.dosbcn.percolator.notifications.time;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.Date;
import java.util.Random;

/**
 * A utility to generate random times on a specific day.
 *
 * @author Sean Connolly
 */
public class RandomTimeGenerator {

	// Don't notify anyone earlier than 11:00 or later than 21:00
	// TODO make configurable
	private static final int EARLIEST_NOTIFICATION_HOUR = 11;
	private static final int LATEST_NOTIFICATION_HOUR = 21;
	private final RandomTime randomTime = new RandomTime();

	/**
	 * Generates a random time to send a notification, as soon as possible.<br/>
	 * If there is time to send a notification today, a random time between now
	 * and the cutoff is chosen. If it is too late to send a notification today,
	 * a random time tomorrow is returned instead.
	 *
	 * @return a random time to send a notification, ASAP
	 */
	public DateTime getRandomTimeASAP() {
		DateTime now = getNow();
		DateTime cutoff = getLatestNotificationTimeOnDate(now);
		if (now.isBefore(cutoff)) {
			return getRandomTimeToday();
		} else {
			return getRandomTimeTomorrow();
		}
	}

	/**
	 * Generates a random time to send a notification, some time today.<br/>
	 * Note: It is possible <i>now</i> is later than the earliest notification
	 * time, usually used in generating a random time. If this is the case, we
	 * need to generate a time after now, not the notification time so as not to
	 * generate a notification time in the past.
	 *
	 * @return a random time to send a notification
	 */
	private DateTime getRandomTimeToday() {
		DateTime now = getNow();
		LocalDate today = now.toLocalDate();
		return getRandomTimeInDay(today);
	}

	/**
	 * Generates a random time to send a notification, some time tomorrow.
	 *
	 * @return a random time to send a notification
	 */
	private DateTime getRandomTimeTomorrow() {
		DateTime now = getNow();
		LocalDate today = now.toLocalDate();
		LocalDate tomorrow = today.plusDays(1);
		return getRandomTimeInDay(tomorrow);
	}

	/**
	 * Returns an appropriate notification time on the date specified.
	 * <p>
	 * Note that all time information is stripped from the {@link Date} provided
	 * prior to calculating a the notification time. Thus, this function may
	 * return a time before, exactly equal to, or after the provided time.
	 * </p>
	 *
	 * @param day
	 * @return
	 */
	public DateTime getRandomTimeInDay(LocalDate day) {
		DateTime time = day.toDateTimeAtStartOfDay();
		int randomTimeMillis = randomTime.nextTime();
		return time.plus(randomTimeMillis);
	}

	/**
	 * Return a {@link Date} of the <i>earliest time</i> of the day a
	 * notification is allowed to be sent, on the specified date.
	 *
	 * @param date
	 *            the date in question
	 * @return the earliest a notification may be sent on that day
	 */
	public DateTime getEarliestNotificationTimeOnDate(DateTime date) {
		DateTime day = TimeAdjustor.stripTimeFromDate(date);
		return day.withHourOfDay(EARLIEST_NOTIFICATION_HOUR);
	}

	/**
	 * Return a {@link Date} of the <i>latest time</i> of the day a notification
	 * is allowed to be sent, on the specified date.
	 *
	 * @param date
	 *            the date in question
	 * @return the latest a notification may be sent on that day
	 */
	public DateTime getLatestNotificationTimeOnDate(DateTime date) {
		DateTime day = TimeAdjustor.stripTimeFromDate(date);
		return day.withHourOfDay(LATEST_NOTIFICATION_HOUR);
	}

	/**
	 * Returns a {@link DateTime} for the current system time.
	 *
	 * @return the current time
	 */
	protected DateTime getNow() {
		return new DateTime();
	}

	/**
	 * The actual random time generator: generates a random time, in
	 * milliseconds, between two cutoffs, in milliseconds.
	 */
	private final class RandomTime {

		private static final int SECOND_MS = 1000;
		private static final int MINUTE_MS = 60 * SECOND_MS;
		private static final int HOUR_MS = 60 * MINUTE_MS;
		private static final int EARLIEST_NOTIFICATION_MS = EARLIEST_NOTIFICATION_HOUR
				* HOUR_MS;
		private static final int LATEST_NOTIFICATION_MS = LATEST_NOTIFICATION_HOUR
				* HOUR_MS;
		private final Random randomGenerator = new Random();

		/**
		 * Return a random time (milliseconds in day), within the hard limits.
		 *
		 * @return a random time in the day
		 */
		public int nextTime() {
			return nextTimeBetween(EARLIEST_NOTIFICATION_MS,
					LATEST_NOTIFICATION_MS);
		}

		/**
		 * Return a random time (milliseconds in day) <i>after</i> the limit
		 * specified, within the hard limits.
		 *
		 * @param msEarliest
		 *            an early cutoff
		 * @return a random time in the day
		 */
		public int nextTimeAfter(final int msEarliest) {
			return nextTimeBetween(msEarliest, LATEST_NOTIFICATION_MS);
		}

		/**
		 * Return a random time (milliseconds in day) <i>before</i> the limit
		 * specified, within the hard limits.
		 *
		 * @param msLatest
		 *            an late cutoff
		 * @return a random time in the day
		 */
		public int nextTimeBefore(final int msLatest) {
			return nextTimeBetween(EARLIEST_NOTIFICATION_MS, msLatest);
		}

		/**
		 * Return a random time (milliseconds in day) <i>between</i> the two
		 * limits specified, within the hard limits.
		 *
		 * @param msEarliest
		 *            an early cutoff
		 * @param msLatest
		 *            an late cutoff
		 * @return a random time in the day
		 */
		public int nextTimeBetween(final int msEarliest, final int msLatest) {
			int lower = Math.max(msEarliest, EARLIEST_NOTIFICATION_MS);
			int upper = Math.min(msLatest, LATEST_NOTIFICATION_MS);
			return randomGenerator.nextInt(upper - lower) + lower;
		}

	}

}
