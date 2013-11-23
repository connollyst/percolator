package org.dosbcn.percolator.notifications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardColor;
import org.dosbcn.percolator.data.CardStage;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

/**
 * Test cases for the {@link CardNotificationTimer}.
 *
 * @author Sean Connolly
 */
public class TestCardNotificationTimer {

	private static final DateTime MID_DAY = new DateTime(1985, 8, 6, 12, 0);
	private static final DateTime LATE_NIGHT = new DateTime(1985, 8, 6, 22, 0);
	private static final DateTime EARLY_MORNING = new DateTime(1985, 8, 6, 2, 0);

	/* Test Notification Day */

	@Test
	public void testSetOneDayNotification() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime startDate = MID_DAY;
		LocalDate expectedDate = startDate.plusDays(1).toLocalDate();
		assertNotificationDate(expectedDate, startDate, stage);
	}

	@Test
	public void testSetOneWeekNotification() {
		CardStage stage = CardStage.ONE_WEEK;
		DateTime startDate = MID_DAY;
		LocalDate expectedDate = startDate.plusWeeks(1).toLocalDate();
		assertNotificationDate(expectedDate, startDate, stage);
	}

	@Test
	public void testSetOneMonthNotification() {
		CardStage stage = CardStage.ONE_MONTH;
		DateTime startDate = MID_DAY;
		LocalDate expectedDate = startDate.plusMonths(1).toLocalDate();
		assertNotificationDate(expectedDate, startDate, stage);
	}

	/* Test Notification Day For Late Night Cards */

	@Test
	public void testSetOneDayNotificationLateAtNight() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime startDate = LATE_NIGHT;
		LocalDate expectedDate = startDate.plusDays(1).toLocalDate();
		assertNotificationDate(expectedDate, startDate, stage);
	}

	@Test
	public void testSetOneWeekNotificationLateAtNight() {
		CardStage stage = CardStage.ONE_WEEK;
		DateTime startDate = LATE_NIGHT;
		LocalDate expectedDate = startDate.plusWeeks(1).toLocalDate();
		assertNotificationDate(expectedDate, startDate, stage);
	}

	@Test
	public void testSetOneMonthNotificationLateAtNight() {
		CardStage stage = CardStage.ONE_MONTH;
		DateTime startDate = LATE_NIGHT;
		LocalDate expectedDate = startDate.plusMonths(1).toLocalDate();
		assertNotificationDate(expectedDate, startDate, stage);
	}

	/* Test Notification Day For Early Morning Cards */

	@Test
	public void testSetOneDayNotificationInTheEarlyMorning() {
		CardStage stage = CardStage.ONE_DAY;
		DateTime startDate = EARLY_MORNING;
		LocalDate expectedDate = startDate.plusDays(1).toLocalDate();
		assertNotificationDate(expectedDate, startDate, stage);
	}

	@Test
	public void testSetOneWeekNotificationInTheEarlyMorning() {
		CardStage stage = CardStage.ONE_WEEK;
		DateTime startDate = EARLY_MORNING;
		LocalDate expectedDate = startDate.plusWeeks(1).toLocalDate();
		assertNotificationDate(expectedDate, startDate, stage);
	}

	@Test
	public void testSetOneMonthNotificationInTheEarlyMorning() {
		CardStage stage = CardStage.ONE_MONTH;
		DateTime startDate = EARLY_MORNING;
		LocalDate expectedDate = startDate.plusMonths(1).toLocalDate();
		assertNotificationDate(expectedDate, startDate, stage);
	}

	// TODO test missed notifications (ASAP notifications)

	// TODO test notification to go off today (early, late, midday)

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
		DateTime[] times = getNotificationTimes(timer, now, stage, 3);
		DateTime time1 = times[0];
		DateTime time2 = times[1];
		DateTime time3 = times[2];
		int day1 = time1.getDayOfYear();
		int day2 = time2.getDayOfYear();
		int day3 = time3.getDayOfYear();
		assertEquals("notifications should be on same day", day1, day2);
		assertEquals("notifications should be on same day", day2, day3);
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
		DateTime[] times = getNotificationTimes(timer, now, stage, 4);
		DateTime time3 = times[2];
		DateTime time4 = times[3];
		int day3 = time3.getDayOfYear();
		int day4 = time4.getDayOfYear();
		assertFalse("4th day should be different than 3rd", day3 == day4);
	}

	private void assertNotificationDate(LocalDate expectedDate,
			DateTime startDate, CardStage stage) {
		CardNotificationTimer timer = new MockCardNotificationTimer(startDate);
		DateTime[] times = getNotificationTimes(timer, startDate, stage, 1);
		DateTime time = times[0];
		LocalDate notificationDate = time.toLocalDate();
		assertEquals(expectedDate, notificationDate);
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
