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

	private final RandomTime randomTime = new RandomTime();
	private final TimeUtilities timeUtilities;

	public RandomTimeGenerator(TimeUtilities timeUtilities) {
		this.timeUtilities = timeUtilities;
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
	 *            the day to generate a random time in
	 * @return the day with a random time assigned
	 */
	public DateTime getRandomTimeInDay(LocalDate day) {
		if (timeUtilities.isToday(day)) {
			return getRandomTimeToday();
		} else {
			DateTime time = day.toDateTimeAtStartOfDay();
			int randomTimeMillis = randomTime.nextTime();
			return time.plus(randomTimeMillis);
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
		// TODO reduce code duplication with getRandomTimeInDay
		LocalDate today = timeUtilities.getToday();
		DateTime time = today.toDateTimeAtStartOfDay();
		int randomTimeMillis = randomTime
				.nextTimeAfter(TimeUtilities.EARLIEST_NOTIFICATION_HOUR);
		return time.plus(randomTimeMillis);
	}

	/**
	 * The actual random time generator: generates a random time, in
	 * milliseconds, between two cutoffs, in milliseconds.
	 */
	@SuppressWarnings("unused")
	private static final class RandomTime {

		private static final int SECOND_MS = 1000;
		private static final int MINUTE_MS = 60 * SECOND_MS;
		private static final int HOUR_MS = 60 * MINUTE_MS;
		private static final int EARLIEST_NOTIFICATION_MS = TimeUtilities.EARLIEST_NOTIFICATION_HOUR
				* HOUR_MS;
		private static final int LATEST_NOTIFICATION_MS = TimeUtilities.LATEST_NOTIFICATION_HOUR
				* HOUR_MS;
		private final Random randomGenerator = new Random();

		/**
		 * Return a random time (milliseconds in day), within the hard limits.
		 *
		 * @return a random time in the day
		 */
		int nextTime() {
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
		int nextTimeAfter(final int msEarliest) {
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
		int nextTimeBefore(final int msLatest) {
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
		int nextTimeBetween(final int msEarliest, final int msLatest) {
			int lower = Math.max(msEarliest, EARLIEST_NOTIFICATION_MS);
			int upper = Math.min(msLatest, LATEST_NOTIFICATION_MS);
			return randomGenerator.nextInt(upper - lower) + lower;
		}

	}

}
