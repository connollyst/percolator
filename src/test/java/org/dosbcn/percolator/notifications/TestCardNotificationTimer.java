package org.dosbcn.percolator.notifications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardColor;
import org.dosbcn.percolator.data.CardStage;
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

	/**
	 * Assert that we can schedule multiple notifications on the same day, up to
	 * the maximum.
	 */
	@Test
	public void testStageMaximumNotificationOnSameDay() {
		CardNotificationTimer timer = new CardNotificationTimerImpl();
		Card card1 = mockCard(CardStage.ONE_DAY);
		Card card2 = mockCard(CardStage.ONE_DAY);
		Card card3 = mockCard(CardStage.ONE_DAY);
		DateTime time1 = timer.getNextNotificationTime(card1);
		DateTime time2 = timer.getNextNotificationTime(card2);
		DateTime time3 = timer.getNextNotificationTime(card3);
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
	@SuppressWarnings("unused")
	public void testStageTooManyNotificationOnSameDay() {
		CardNotificationTimer timer = new CardNotificationTimerImpl();
		Card card1 = mockCard(CardStage.ONE_DAY);
		Card card2 = mockCard(CardStage.ONE_DAY);
		Card card3 = mockCard(CardStage.ONE_DAY);
		Card card4 = mockCard(CardStage.ONE_DAY);
		DateTime time1 = timer.getNextNotificationTime(card1);
		DateTime time2 = timer.getNextNotificationTime(card2);
		DateTime time3 = timer.getNextNotificationTime(card3);
		DateTime time4 = timer.getNextNotificationTime(card4);
		int day3 = time3.getDayOfYear();
		int day4 = time4.getDayOfYear();
		assertFalse("4th day should be different than 3rd", day3 == day4);
	}

	private Card mockCard(CardStage stage) {
		Card card = new Card("MockCardTitle", "MockCardDescription",
				CardColor.WHITE);
		card.setStage(stage);
		return card;
	}
}
