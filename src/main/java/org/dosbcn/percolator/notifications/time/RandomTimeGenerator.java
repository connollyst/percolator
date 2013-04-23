package org.dosbcn.percolator.notifications.time;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class RandomTimeGenerator {

    private static final String LOG_TAG = RandomTimeGenerator.class.getSimpleName();
    // Don't notify anyone earlier than 11:00 or later than 21:00
    // TODO make configurable
    private static final int EARLIEST_NOTIFICATION_HOUR = 11;
    private static final int LATEST_NOTIFICATION_HOUR = 21;
    private final RandomTime randomTime = new RandomTime();

    /**
     * Generates a random time to send a notification, as soon as
     * possible.<br/>
     * If there is time to send a notification today, a random time between now
     * and the cutoff is chosen. If it is too late to send a notification
     * today,
     * a random time tomorrow is returned instead.
     *
     * @return a random time to send a notification, ASAP
     */
    public Date getRandomTimeASAP() {
        Log.i(LOG_TAG, "Getting notification ASAP.");
        Date now = Calendar.getInstance().getTime();
        Date cutoff = getLatestNotificationTimeOnDate(now);
        if (now.before(cutoff)) {
            return getRandomTimeToday();
        } else {
            Date tomorrow = TimeAdjustor.addDay(now);
            return getRandomTimeInDay(tomorrow);
        }
    }

    /**
     * Generates a random time to send a notification, roughly one day from the
     * given origin date.
     *
     * @param originDate
     * @return a random time to send a notification
     */
    public Date getRandomTimeOneDayFromDate(Date originDate) {
        Date oneDayFromOrigin = TimeAdjustor.addDay(originDate);
        return getRandomTimeInDay(oneDayFromOrigin);
    }

    public Date getRandomTimeOneWeekFromDate(Date originDate) {
        // TODO pick a day about one week from the start day (and after now)
        Date oneWeekFromOrigin = TimeAdjustor.addWeek(originDate);
        return getRandomTimeInDay(oneWeekFromOrigin);
    }

    public Date getRandomTimeOneMonthFromDate(Date originDate) {
        // TODO pick a day about one month from the start day (and after now)
        Date oneMonthFromOrigin = TimeAdjustor.addMonth(originDate);
        return getRandomTimeInDay(oneMonthFromOrigin);
    }

    /**
     * Returns an appropriate notification time on the date specified.
     * <p>
     * Note that all time information is stripped from the {@link Date}
     * provided
     * prior to calculating a the notification time. Thus, this function may
     * return a time before, exactly equal to, or after the provided time.
     * </p>
     *
     * @param date
     * @return
     */
    private Date getRandomTimeInDay(Date date) {
        Date day = TimeAdjustor.stripTimeFromDate(date);
        int randomTimeMillis = randomTime.nextTime();
        TimeAdjustment adjustment = new TimeAdjustment(Calendar.MILLISECOND,
                randomTimeMillis);
        Date notificationTime = TimeAdjustor.addTime(day, adjustment);
        return notificationTime;
    }

    private Date getRandomTimeToday() {
        Date today = Calendar.getInstance().getTime();
        Date day = TimeAdjustor.stripTimeFromDate(today);
        int msNow = (int) today.getTime();
        int randomTimeMillis = randomTime.nextTimeAfter(msNow);
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
     *         the date in question
     * @return the earliest a notification may be sent on that day
     */
    public Date getEarliestNotificationTimeOnDate(Date date) {
        Date day = TimeAdjustor.stripTimeFromDate(date);
        Date earliest = TimeAdjustor.addTime(day, new TimeAdjustment(
                Calendar.HOUR_OF_DAY, EARLIEST_NOTIFICATION_HOUR));
        return earliest;
    }

    /**
     * Return a {@link Date} of the <i>latest time</i> of the day a
     * notification
     * is allowed to be sent, on the specified date.
     *
     * @param date
     *         the date in question
     * @return the latest a notification may be sent on that day
     */
    public Date getLatestNotificationTimeOnDate(Date date) {
        Date day = TimeAdjustor.stripTimeFromDate(date);
        Date latest = TimeAdjustor.addTime(day, new TimeAdjustment(
                Calendar.HOUR_OF_DAY, LATEST_NOTIFICATION_HOUR));
        return latest;
    }

    /**
     * The actual random time generator
     */
    private class RandomTime {

        private static final int SECOND_MS = 1000;
        private static final int MINUTE_MS = 60 * SECOND_MS;
        private static final int HOUR_MS = 60 * MINUTE_MS;
        private static final int EARLIEST_NOTIFICATION_MS =
                EARLIEST_NOTIFICATION_HOUR * HOUR_MS;
        private static final int LATEST_NOTIFICATION_MS =
                LATEST_NOTIFICATION_HOUR * HOUR_MS;
        private final Random randomGenerator = new Random();

        public int nextTime() {
            return nextTimeBetween(EARLIEST_NOTIFICATION_MS,
                    LATEST_NOTIFICATION_MS);
        }

        public int nextTimeBefore(int msLatest) {
            return nextTimeBetween(EARLIEST_NOTIFICATION_MS, msLatest);
        }

        public int nextTimeAfter(int msEarliest) {
            return nextTimeBetween(msEarliest, LATEST_NOTIFICATION_MS);
        }

        public int nextTimeBetween(int msEarliest, int msLatest) {
            return randomGenerator.nextInt(msLatest - msEarliest) + msEarliest;
        }
    }

}
