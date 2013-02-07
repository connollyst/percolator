package com.dosbcn.flashcards.notifications;

import java.util.Calendar;
import java.util.Date;

import android.util.Log;

import com.dosbcn.flashcards.data.Card;
import com.dosbcn.flashcards.notifications.time.RandomTimeGenerator;
import com.dosbcn.flashcards.notifications.time.TimeAdjustor;

/**
 * Generates times that notifications should be scheduled for in the future.<br/>
 * There are a couple of important factors here:
 * <ol>
 * <li>
 * <b>Times are random:</b> we don't want a user to always receive reminders at
 * 1:37pm. By making the notifications occur randomly, the user is forced to not
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
 * @see {@link CardNofifier}
 * @see {@link CardNotificationStage}
 * 
 * @author Sean Connolly
 */
public class CardNotificationTimer {

	private static final String LOG_TAG = CardNotificationTimer.class
			.getSimpleName();

	private static final String BAD_STAGE_LOG = "Unexpected "
			+ CardNotificationStage.class.getSimpleName() + ": ";

	private final RandomTimeGenerator timeGenerator = new RandomTimeGenerator();

	/**
	 * Returns the next time that a notification should be sent for this
	 * {@link Card}.<br/>
	 * The time is guaranteed to be within the range of 'Ok times'.
	 * 
	 * @param card
	 * @return
	 */
	public Date getNextNotificationTime(Card card) {
		return getNotificationTime(card.getStage(), card.getStartDate());
	}

	/**
	 * Generate a random time to send the notification, appropriate for the
	 * given stage and origin date.
	 */
	private Date getNotificationTime(CardNotificationStage stage,
			Date originDate) {
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

	private Date getOneDayNotification(Date originDate) {
		if (haveMissedOneDayNotification(originDate)) {
			Log.i(LOG_TAG, "Missed one day notification, sending asap.");
			return timeGenerator.getRandomTimeASAP();
		} else {
			return timeGenerator.getRandomTimeOneDayFromDate(originDate);
		}
	}

	private Date getOneWeekNotification(Date originDate) {
		if (haveMissedOneWeekNotification(originDate)) {
			Log.i(LOG_TAG, "Missed one week notification, sending asap.");
			return timeGenerator.getRandomTimeASAP();
		} else {
			return timeGenerator.getRandomTimeOneWeekFromDate(originDate);
		}
	}

	private Date getOneMonthNotification(Date originDate) {
		if (haveMissedOneMonthNotification(originDate)) {
			Log.i(LOG_TAG, "Missed one month notification, sending asap.");
			return timeGenerator.getRandomTimeASAP();
		} else {
			return timeGenerator.getRandomTimeOneMonthFromDate(originDate);
		}
	}

	private boolean haveMissedOneDayNotification(Date date) {
		return haveMissedNotificationDate(TimeAdjustor.addDay(date));
	}

	private boolean haveMissedOneWeekNotification(Date date) {
		return haveMissedNotificationDate(TimeAdjustor.addWeek(date));
	}

	private boolean haveMissedOneMonthNotification(Date date) {
		return haveMissedNotificationDate(TimeAdjustor.addMonth(date));
	}

	private boolean haveMissedNotificationDate(Date date) {
		Date notificationDate = date;
		Date now = getNowNormalized();
		return now.after(notificationDate);
	}

	private Date getNow() {
		// TODO make this more efficient and easier to test
		return Calendar.getInstance().getTime();
	}

	private Date getNowNormalized() {
		return normalize(getNow());
	}

	private Date normalize(Date date) {
		Date lowCutoff = timeGenerator.getEarliestNotificationTimeOnDate(date);
		Date highCutoff = timeGenerator.getLatestNotificationTimeOnDate(date);
		Date normalizedDate;
		if (date.before(lowCutoff)) {
			normalizedDate = lowCutoff;
		} else if (date.after(highCutoff)) {
			normalizedDate = highCutoff;
		} else {
			normalizedDate = date;
		}
		return normalizedDate;
	}

}
