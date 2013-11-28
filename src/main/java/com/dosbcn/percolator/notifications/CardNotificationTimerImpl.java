package com.dosbcn.percolator.notifications;

import com.dosbcn.percolator.data.Card;
import com.dosbcn.percolator.data.CardStage;
import com.dosbcn.percolator.notifications.time.RandomDayGenerator;
import com.dosbcn.percolator.notifications.time.RandomTimeGenerator;
import com.dosbcn.percolator.notifications.time.TimeUtilities;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import android.util.Log;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * Generates times that notifications should be scheduled for in the future.<br/>
 * There are a couple of important factors here:
 * <ol>
 * <li>
 * <b>Days are random:</b> while we keep notifications to some basic schedule,
 * we don't want to be too predictable. By sending notifications randomly, the
 * user is forced to not think about the message too much until it appears.</li>
 * <li>
 * <b>Times are random:</b> we don't want a user to always receive reminders at
 * a specific time.</li>
 * <li>
 * <b>Times are not completely random:</b> we don't want to schedule a
 * notification for the middle of the night or something.</li>
 * <li>
 * <b>Notifications per day are limited:</b> we don't want too many
 * notifications per day.</li>
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

	private static final int MAX_DAILY_NOTIFICATIONS = 2;
	private final Multiset<LocalDate> scheduledDates = HashMultiset.create();

	private final TimeUtilities timeUtilities;
	private final RandomDayGenerator dayGenerator;
	private final RandomTimeGenerator timeGenerator;

	public CardNotificationTimerImpl() {
		this(new TimeUtilities());
	}

	protected CardNotificationTimerImpl(TimeUtilities timeUtilities) {
		this.timeUtilities = timeUtilities;
		this.dayGenerator = new RandomDayGenerator();
		this.timeGenerator = new RandomTimeGenerator(timeUtilities);
	}

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
	 *
	 * @param stage
	 *            the stage the card is at
	 * @param originDate
	 *            the date the card was started
	 * @return th next date and time that a notification should be sent
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
		return getNotificationTime(timeUtilities.getToday());
	}

	private DateTime getNotificationTime(LocalDate day) {
		if (!isDayAvailable(day)) {
			day = getNextAvailableDay(day);
		}
		scheduledDates.add(day);
		return timeGenerator.getRandomTimeInDay(day);
	}

	private LocalDate getNextAvailableDay(LocalDate day) {
		while (!isDayAvailable(day)) {
			day = day.plusDays(1);
		}
		return day;
	}

	private boolean isDayAvailable(LocalDate day) {
		boolean dayFull = scheduledDates.count(day) >= MAX_DAILY_NOTIFICATIONS;
		boolean pastCutoff = false;
		if (timeUtilities.isToday(day)) {
			LocalTime limit = timeUtilities.getLatestNotificationTime();
			pastCutoff = timeUtilities.getNow().toLocalTime().isAfter(limit);
		}
		return !dayFull && !pastCutoff;
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
		return timeUtilities.getNow().isAfter(date);
	}

}
