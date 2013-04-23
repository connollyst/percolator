package org.dosbcn.percolator.notifications;

import android.util.Log;
import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardStage;
import org.dosbcn.percolator.notifications.time.RandomTimeGenerator;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Generates times that notifications should be scheduled for in the
 * future.<br/>
 * There are a couple of important factors here:
 * <ol>
 * <li>
 * <b>Times are random:</b> we don't want a user to always receive reminders at
 * 1:37pm. By making the notifications occur randomly, the user is forced to
 * not
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
 * @author Sean Connolly
 * @see CardNofifier
 * @see CardStage
 */
public class CardNotificationTimerImpl
        implements CardNotificationTimer {

    private static final String LOG_TAG = CardNotificationTimerImpl.class
            .getSimpleName();
    private static final String BAD_STAGE_LOG = "Unexpected "
            + CardStage.class.getSimpleName() + ": ";
    private final RandomTimeGenerator timeGenerator = new RandomTimeGenerator();

    /**
     * {@inheritDoc}
     */
    @Override
    public DateTime getNextNotificationTime(Card card) {
        return getNotificationTime(card.getStage(), card.getStartDate());
    }

    /**
     * Generate a random time to send the notification, appropriate for the
     * given stage and origin date.
     */
    private DateTime getNotificationTime(CardStage stage,
                                         DateTime originDate) {
        switch (stage) {
            case ONE_DAY:
                return getOneDayNotification(originDate);
            case ONE_WEEK:
                return getOneWeekNotification(originDate);
            case ONE_MONTH:
                return getOneMonthNotification(originDate);
            default:
                Log.e(LOG_TAG, BAD_STAGE_LOG + stage);
                return null;
        }
    }

    private DateTime getOneDayNotification(DateTime originDate) {
        if (haveMissedOneDayNotification(originDate)) {
            Log.i(LOG_TAG, "Missed one day notification, sending asap.");
            return timeGenerator.getRandomTimeASAP();
        } else {
            return timeGenerator.getRandomTimeOneDayFromDate(originDate);
        }
    }

    private DateTime getOneWeekNotification(DateTime originDate) {
        if (haveMissedOneWeekNotification(originDate)) {
            Log.i(LOG_TAG, "Missed one week notification, sending asap.");
            return timeGenerator.getRandomTimeASAP();
        } else {
            return timeGenerator.getRandomTimeOneWeekFromDate(originDate);
        }
    }

    private DateTime getOneMonthNotification(DateTime originDate) {
        if (haveMissedOneMonthNotification(originDate)) {
            Log.i(LOG_TAG, "Missed one month notification, sending asap.");
            return timeGenerator.getRandomTimeASAP();
        } else {
            return timeGenerator.getRandomTimeOneMonthFromDate(originDate);
        }
    }

    private boolean haveMissedOneDayNotification(DateTime date) {
        return havePassedNotificationPeriodOnDate(date.plus(Period.days(1)));
    }

    private boolean haveMissedOneWeekNotification(DateTime date) {
        return havePassedNotificationPeriodOnDate(date.plus(Period.weeks(1)));
    }

    private boolean haveMissedOneMonthNotification(DateTime date) {
        return havePassedNotificationPeriodOnDate(date.plus(Period.months(1)));
    }

    /**
     * Check if we have missed the specified date, taking into account the
     * cutoff periods in morning and night.<br/>
     * For example, if the latest a notification can be sent is 8pm and it is
     * currently 10pm on given date, we have passed the notification period on
     * the date.
     *
     * @param date
     * @return
     */
    private boolean havePassedNotificationPeriodOnDate(DateTime date) {
        return getNow().isAfter(date);
    }

    private DateTime getNow() {
        // TODO make this more efficient and easier to test
        return new DateTime();
    }

    private DateTime getNowNormalized() {
        return normalize(getNow());
    }

    private DateTime normalize(DateTime date) {
        DateTime lowCutoff = timeGenerator.getEarliestNotificationTimeOnDate(date);
        DateTime highCutoff = timeGenerator.getLatestNotificationTimeOnDate(date);
        DateTime normalizedDate;
        if (date.isBefore(lowCutoff)) {
            normalizedDate = lowCutoff;
        } else if (date.isAfter(highCutoff)) {
            normalizedDate = highCutoff;
        } else {
            normalizedDate = date;
        }
        return normalizedDate;
    }

}
