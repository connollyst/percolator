package com.dosbcn.percolator.notifications.time;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * @author Sean Connolly
 */
public class TimeUtilities {

	// Don't notify anyone earlier than 11:00 or later than 21:00
	// TODO make configurable
	public static final int EARLIEST_NOTIFICATION_HOUR = 11;
	public static final int LATEST_NOTIFICATION_HOUR = 21;

	public DateTime getNow() {
		return new DateTime();
	}

	public LocalDate getToday() {
		return getNow().toLocalDate();
	}

	public boolean isToday(LocalDate day) {
		LocalDate today = getToday();
		return today.getYear() == day.getYear()
				&& today.getDayOfYear() == day.getDayOfYear();
	}

	/**
	 * Return a {@link LocalTime} of the <i>earliest time</i> of the day a
	 * notification is allowed to be sent.
	 *
	 * @return the earliest a notification may be sent
	 */
	public LocalTime getEarliestNotificationTime() {
		return new LocalTime().withHourOfDay(EARLIEST_NOTIFICATION_HOUR)
				.withMinuteOfHour(0).withSecondOfMinute(0)
				.withMillisOfSecond(0);
	}

	/**
	 * Return a {@link LocalTime} of the <i>latest time</i> of the day a
	 * notification is allowed to be sent.
	 *
	 * @return the latest a notification may be sent
	 */
	public LocalTime getLatestNotificationTime() {
		return new LocalTime().withHourOfDay(LATEST_NOTIFICATION_HOUR)
				.withMinuteOfHour(0).withSecondOfMinute(0)
				.withMillisOfSecond(0);
	}

}
