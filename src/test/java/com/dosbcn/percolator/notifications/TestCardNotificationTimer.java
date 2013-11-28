package com.dosbcn.percolator.notifications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.dosbcn.percolator.data.Card;
import com.dosbcn.percolator.data.CardColor;
import com.dosbcn.percolator.data.CardStage;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Test cases for the {@link CardNotificationTimer}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestCardNotificationTimer {

	private static final DateTime MID_DAY = new DateTime(1985, 8, 6, 12, 0);
	private static final DateTime LATE_NIGHT = new DateTime(1985, 8, 6, 22, 0);
	private static final DateTime EARLY_MORNING = new DateTime(1985, 8, 6, 2, 0);

	/* Test Notification Day */

	@Test
	public void testSetOneDayNotification() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime startDate = MID_DAY;
		DateTime currentDate = MID_DAY;
		DateTime expectedDate = startDate.plusDays(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneWeekNotification() {
		CardStage stage = CardStage.ONE_WEEK;
		DateTime startDate = MID_DAY;
		DateTime currentDate = MID_DAY;
		DateTime expectedDate = startDate.plusWeeks(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneMonthNotification() {
		CardStage stage = CardStage.ONE_MONTH;
		DateTime startDate = MID_DAY;
		DateTime currentDate = MID_DAY;
		DateTime expectedDate = startDate.plusMonths(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	/* Test Notification Day For Early Morning Cards */

	@Test
	public void testSetOneDayNotificationInTheEarlyMorning() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime startDate = EARLY_MORNING;
		DateTime currentDate = MID_DAY;
		DateTime expectedDate = startDate.plusDays(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneWeekNotificationInTheEarlyMorning() {
		CardStage stage = CardStage.ONE_WEEK;
		DateTime startDate = EARLY_MORNING;
		DateTime currentDate = MID_DAY;
		DateTime expectedDate = startDate.plusWeeks(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneMonthNotificationInTheEarlyMorning() {
		CardStage stage = CardStage.ONE_MONTH;
		DateTime startDate = EARLY_MORNING;
		DateTime currentDate = MID_DAY;
		DateTime expectedDate = startDate.plusMonths(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	/* Test Notification Day For Late Night Cards */

	@Test
	public void testSetOneDayNotificationLateAtNight() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime startDate = LATE_NIGHT;
		DateTime currentDate = MID_DAY;
		DateTime expectedDate = startDate.plusDays(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneWeekNotificationLateAtNight() {
		CardStage stage = CardStage.ONE_WEEK;
		DateTime startDate = LATE_NIGHT;
		DateTime currentDate = MID_DAY;
		DateTime expectedDate = startDate.plusWeeks(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneMonthNotificationLateAtNight() {
		CardStage stage = CardStage.ONE_MONTH;
		DateTime startDate = LATE_NIGHT;
		DateTime currentDate = MID_DAY;
		DateTime expectedDate = startDate.plusMonths(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	/* Test Notification Day For Today */

	@Test
	public void testSetOneDayNotificationForToday() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime startDate = MID_DAY;
		DateTime currentDate = MID_DAY.plusDays(1);
		DateTime expectedDate = currentDate;
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneWeekNotificationForToday() {
		CardStage stage = CardStage.ONE_WEEK;
		DateTime startDate = MID_DAY;
		DateTime currentDate = MID_DAY.plusWeeks(1);
		DateTime expectedDate = currentDate;
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneMonthNotificationForToday() {
		CardStage stage = CardStage.ONE_MONTH;
		DateTime startDate = MID_DAY;
		DateTime currentDate = MID_DAY.plusMonths(1);
		DateTime expectedDate = currentDate;
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	/* Test Notification Day For Today, Early In The Morning */

	@Test
	public void testSetOneDayNotificationForTodayInTheEarlyMorning() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime startDate = MID_DAY;
		DateTime currentDate = EARLY_MORNING.plusDays(1);
		DateTime expectedDate = currentDate;
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneWeekNotificationForTodayInTheEarlyMorning() {
		CardStage stage = CardStage.ONE_WEEK;
		DateTime startDate = MID_DAY;
		DateTime currentDate = EARLY_MORNING.plusWeeks(1);
		DateTime expectedDate = currentDate;
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneMonthNotificationForTodayInTheEarlyMorning() {
		CardStage stage = CardStage.ONE_MONTH;
		DateTime startDate = MID_DAY;
		DateTime currentDate = EARLY_MORNING.plusMonths(1);
		DateTime expectedDate = currentDate;
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	/* Test Notification Day For Today, Late At Night */

	@Test
	public void testSetOneDayNotificationForTodayLateAtNight() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime startDate = MID_DAY;
		DateTime currentDate = LATE_NIGHT.plusDays(1);
		DateTime expectedDate = currentDate.plusDays(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneWeekNotificationForTodayLateAtNight() {
		CardStage stage = CardStage.ONE_WEEK;
		DateTime startDate = MID_DAY;
		DateTime currentDate = LATE_NIGHT.plusWeeks(1);
		DateTime expectedDate = currentDate.plusDays(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	@Test
	public void testSetOneMonthNotificationForTodayLateAtNight() {
		CardStage stage = CardStage.ONE_MONTH;
		DateTime startDate = MID_DAY;
		DateTime currentDate = LATE_NIGHT.plusMonths(1);
		DateTime expectedDate = currentDate.plusDays(1);
		assertNotificationDate(currentDate, startDate, stage, expectedDate);
	}

	// TODO test missed notifications (ASAP notifications)

	/* Test Maximum Notifications Per Day */

	/**
	 * Assert that we can schedule multiple notifications on the same day, up to
	 * the maximum.
	 */
	@Test
	public void testSetMaximumNotificationOnSameDay() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime now = MID_DAY;
		CardNotificationTimer timer = new MockCardNotificationTimer(now);
		DateTime[] times = getNotificationTimes(timer, now, stage, 2);
		DateTime time1 = times[0];
		DateTime time2 = times[1];
		int day1 = time1.getDayOfYear();
		int day2 = time2.getDayOfYear();
		assertEquals("notifications should be on same day", day1, day2);
	}

	/**
	 * Assert that excess notifications roll over to another day if we schedule
	 * more than the maximum for one day.
	 */
	@Test
	public void testSetTooManyNotificationOnSameDay() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime now = MID_DAY;
		CardNotificationTimer timer = new MockCardNotificationTimer(now);
		DateTime[] times = getNotificationTimes(timer, now, stage, 3);
		DateTime time2 = times[1];
		DateTime time3 = times[2];
		int day2 = time2.getDayOfYear();
		int day3 = time3.getDayOfYear();
		assertFalse("3th day should be different than 2rd", day2 == day3);
	}

	/**
	 * Assert that a notification is scheduled for the expected date, given the
	 * current date and time, the date and time the card was started, and the
	 * current stage of the card.
	 *
	 * @param currentDate
	 *            the current date and time
	 * @param startDate
	 *            the date and time the card was started
	 * @param stage
	 *            the stage of the card
	 * @param expectedDate
	 *            the expected date of the notification
	 */
	private void assertNotificationDate(DateTime currentDate,
			DateTime startDate, CardStage stage, DateTime expectedDate) {
		CardNotificationTimer timer = new MockCardNotificationTimer(currentDate);
		DateTime[] times = getNotificationTimes(timer, startDate, stage, 1);
		DateTime time = times[0];
		assertEquals(expectedDate.toLocalDate(), time.toLocalDate());
		// TODO assert that the time is in acceptable hours
	}

	private DateTime[] getNotificationTimes(CardNotificationTimer timer,
			DateTime startDate, CardStage stage, int count) {
		DateTime[] times = new DateTime[count];
		for (int i = 0; i < count; i++) {
			Card card = mockCard(startDate, stage);
			DateTime time = timer.getNextNotificationTime(card);
			times[i] = time;
		}
		return times;
	}

	private Card mockCard(DateTime startDate, CardStage stage) {
		Card card = new Card("MockCardTitle", "MockCardDescription",
				CardColor.WHITE);
		card.setStage(stage);
		card.setStartDate(startDate);
		return card;
	}
}
