package com.dosbcn.percolator.notifications;

import static org.junit.Assert.assertEquals;

import com.dosbcn.percolator.data.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.dosbcn.percolator.MainActivity;

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
	@Test
	public void testScheduleMultipleNotificationsOnTheSameDay() {
		DateTime now = new DateTime(1985, 8, 6, 12, 29, 59);
		LocalDate today = now.toLocalDate();
		LocalDate tomorrow = today.plusDays(1);
		MainActivity activity = new MainActivity();
		CardService service = activity.getService();
		CardRepository repository = service.getCardRepository();
		Card card1 = new Card("Title1", "Desc1", CardColor.WHITE);
		card1.setNextNotificationDate(tomorrow);
		repository.create(card1);
		Card card2 = new Card("Title2", "Desc2", CardColor.WHITE);
		card2.setStage(CardStage.ONE_DAY);
		card2.setStartDate(now);
		CardNotificationTimer timer = new MockCardNotificationTimer(repository,
				now);
		DateTime notificationDateTime = timer.getNextNotificationTime(card2);
		LocalDate notificationDate = notificationDateTime.toLocalDate();
		assertEquals(tomorrow, notificationDate);
	}

	@Test
	public void testScheduleTooManyNotificationsOnTheSameDay() {
		DateTime now = new DateTime(1985, 8, 6, 12, 29, 59);
		LocalDate today = now.toLocalDate();
		LocalDate tomorrow = today.plusDays(1);
		LocalDate dayAfterTomorrow = tomorrow.plusDays(1);
		MainActivity activity = new MainActivity();
		CardService service = activity.getService();
		CardRepository repository = service.getCardRepository();
		Card card1 = new Card("Title1", "Desc1", CardColor.WHITE);
		Card card2 = new Card("Title2", "Desc2", CardColor.WHITE);
		card1.setNextNotificationDate(tomorrow);
		card2.setNextNotificationDate(tomorrow);
		repository.create(card1);
		repository.create(card2);
		Card card3 = new Card("Title3", "Desc3", CardColor.WHITE);
		card3.setStage(CardStage.ONE_DAY);
		card3.setStartDate(now);
		CardNotificationTimer timer = new MockCardNotificationTimer(repository,
				now);
		DateTime notificationDateTime = timer.getNextNotificationTime(card3);
		LocalDate notificationDate = notificationDateTime.toLocalDate();
		assertEquals(dayAfterTomorrow, notificationDate);
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
		CardNotificationTimer timer = mockTimer(currentDate);
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

	private CardNotificationTimer mockTimer(DateTime now) {
		MainActivity activity = new MainActivity();
		CardRepository repository = activity.getService().getCardRepository();
		return new MockCardNotificationTimer(repository, now);
	}

	private Card mockCard(DateTime startDate, CardStage stage) {
		Card card = new Card("MockCardTitle", "MockCardDescription",
				CardColor.WHITE);
		card.setStage(stage);
		card.setStartDate(startDate);
		return card;
	}

}
