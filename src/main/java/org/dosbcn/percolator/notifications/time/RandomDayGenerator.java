package org.dosbcn.percolator.notifications.time;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;

/**
 * A utility to generate random days.
 *
 * @author Sean Connolly
 */
public class RandomDayGenerator {

	/**
	 * Generates a random time to send a notification, roughly one day from the
	 * given origin date.
	 *
	 * @param originDay
	 * @return a random time to send a notification
	 */
	public LocalDate getDayAboutOneDayFromDay(LocalDate originDay) {
		return originDay.plus(Period.days(1));
	}

	public LocalDate getDayAboutOneWeekFromDay(LocalDate originDay) {
		// TODO pick a day about one week from the start day (and after now)
		return originDay.plus(Period.weeks(1));
	}

	public LocalDate getDayAboutOneMonthFromDay(LocalDate originDay) {
		// TODO pick a day about one month from the start day (and after now)
		return originDay.plus(Period.months(1));
	}

}
