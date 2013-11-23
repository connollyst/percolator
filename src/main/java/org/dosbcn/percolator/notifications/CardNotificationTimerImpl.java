package org.dosbcn.percolator.notifications;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardStage;
import org.dosbcn.percolator.notifications.time.RandomDayGenerator;
import org.dosbcn.percolator.notifications.time.RandomTimeGenerator;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import android.util.Log;

/**
 * Generates times that notifications should be scheduled for in the future.<br/>
 * There are a couple of important factors here:
 * <ol>
 * <li>
 * <b>Days are random:</b> while we keep notifications to some basic schedule,
 * we don't want to be too predictable. By sending notifications randomly, the
 * user is forced to not think about the message too much until it appears.</li>
 * <li>
 * <li>
 * <b>Times are random:</b> we don't want a user to always receive reminders at
 * a specific time.</li>
 * <li>
 * <b>Times are not completely random:</b> we don't want to schedule a
 * notification for the middle of the night or something.</li>
 * <li>
 * <li>
 * <b>Notifications per day are limited:</b> we don't want too many
 * notifications per day.</li>
 * <li>
 * </ol>
 *
 * @author Sean Connolly
 * @see CardNotifier
 * @see CardStage
 */
public class CardNotificationTimerImpl implements CardNotificationTimer {

	private static final String LOG_TAG = CardNotificationTimerImpl.class
			.getName();
	private static final String BAD_STAGE_LOG = "Unexpected "
			+ CardStage.class.getSimpleName() + ": ";

	private static final int MAX_DAILY_NOTIFICATIONS = 3;
	private final Multiset<LocalDate> scheduledDates = HashMultiset.create();

	private final RandomDayGenerator dayGenerator = new RandomDayGenerator();
	private final RandomTimeGenerator timeGenerator = new RandomTimeGenerator();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DateTime getNextNotificationTime(Card card) {
		return getNotificationTime(card.getStage(), card.getStartDate());
	}

	/**
	 * Generate a random day and time to send the notification, appropriate for
	 * the given stage and origin date.
	 */
	private DateTime getNotificationTime(CardStage stage, DateTime originDate) {
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
			Log.i(LOG_TAG, "Missed one day notification, sending ASAP.");
			return getNotificationASAP();
		} else {
			LocalDate originDay = originDate.toLocalDate();
			LocalDate day = dayGenerator.getDayAboutOneDayFromDay(originDay);
			return getNotificationTime(day);
		}
	}

	private DateTime getOneWeekNotification(DateTime originDate) {
		if (haveMissedOneWeekNotification(originDate)) {
			Log.i(LOG_TAG, "Missed one week notification, sending ASAP.");
			return getNotificationASAP();
		} else {
			LocalDate originDay = originDate.toLocalDate();
			LocalDate day = dayGenerator.getDayAboutOneWeekFromDay(originDay);
			return getNotificationTime(day);
		}
	}

	private DateTime getOneMonthNotification(DateTime originDate) {
		if (haveMissedOneMonthNotification(originDate)) {
			Log.i(LOG_TAG, "Missed one month notification, sending ASAP.");
			return getNotificationASAP();
		} else {
			LocalDate originDay = originDate.toLocalDate();
			LocalDate day = dayGenerator.getDayAboutOneMonthFromDay(originDay);
			return getNotificationTime(day);
		}
	}

	private DateTime getNotificationASAP() {
		// TODO check for available date
		DateTime time = timeGenerator.getRandomTimeASAP();
		recordNotificationDay(time.toLocalDate());
		return time;
	}

	private DateTime getNotificationTime(LocalDate day) {
		day = getAvailableDay(day);
		recordNotificationDay(day);
		return timeGenerator.getRandomTimeInDay(day);
	}

	private LocalDate getAvailableDay(LocalDate day) {
		while (!isDayAvailable(day)) {
			day = day.plusDays(1);
		}
		return day;
	}

	private boolean isDayAvailable(LocalDate day) {
		boolean dayFull = scheduledDates.count(day) < MAX_DAILY_NOTIFICATIONS;
		// TODO check cutoff times!
		return dayFull;
	}

	private void recordNotificationDay(LocalDate day) {
		scheduledDates.add(day);
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
	 *            the notification date time in question
	 * @return whether the current date time is after the specified date time
	 */
	private boolean havePassedNotificationPeriodOnDate(DateTime date) {
		// TODO take cutoff times into account
		return getNow().isAfter(date);
	}

	private DateTime getNow() {
		// TODO make this easier to test
		return DateTime.now();
	}

	private DateTime getNowNormalized() {
		return normalize(getNow());
	}

	private DateTime normalize(DateTime date) {
		DateTime lowCutoff = timeGenerator
				.getEarliestNotificationTimeOnDate(date);
		DateTime highCutoff = timeGenerator
				.getLatestNotificationTimeOnDate(date);
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
