package org.dosbcn.flashcards.notifications.time;

import java.util.Calendar;
import java.util.Date;

public final class TimeAdjustor {

	private TimeAdjustor() {
		// hide utility class constructor
	}

	public static Date addDay(Date date) {
		return addDays(date, 1);
	}

	public static Date addWeek(Date date) {
		return addDays(date, 7);
	}

	public static Date addMonth(Date date) {
		return addDays(date, 30);
	}

	public static Date addDays(Date date, int days) {
		return addTime(date, new TimeAdjustment(Calendar.HOUR, 24 * days));
	}

	public static Date addTime(Date date, TimeAdjustment... adjustments) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for (TimeAdjustment adjustment : adjustments) {
			cal.add(adjustment.getField(), adjustment.getAmount());
		}
		return cal.getTime();
	}

	/**
	 * Takes a {@link Date} and removes all time information from it, stripping
	 * it down to just the day.
	 * 
	 * @param date
	 *            the date object with or without time information
	 * @return the date object, without time information
	 */
	public static Date stripTimeFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

}
