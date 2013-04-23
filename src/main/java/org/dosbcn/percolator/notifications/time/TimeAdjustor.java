package org.dosbcn.percolator.notifications.time;

import org.joda.time.DateTime;

import java.util.Date;

public final class TimeAdjustor {

    private TimeAdjustor() {
        // hide utility class constructor
    }

    /**
     * Takes a {@link Date} and removes all time information from it, stripping
     * it down to just the day.
     *
     * @param date
     *         the date object with or without time information
     * @return the date object, without time information
     */
    public static DateTime stripTimeFromDate(DateTime date) {
        return date.withHourOfDay(0)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0)
                .withMillisOfSecond(0);
    }

}
