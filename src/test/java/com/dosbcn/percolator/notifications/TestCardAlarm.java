package com.dosbcn.percolator.notifications;

import static org.junit.Assert.*;

import com.dosbcn.percolator.RobolectricHelper;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Intent;

import com.dosbcn.percolator.MainActivity;
import com.dosbcn.percolator.data.Card;
import com.dosbcn.percolator.data.CardColor;
import com.dosbcn.percolator.data.CardService;
import com.dosbcn.percolator.data.CardStage;

/**
 * Test cases for the {@link CardAlarm}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestCardAlarm {

	@Test
	public void testCardAlarmShowsNotification() {
		MainActivity activity = RobolectricHelper.createMainActivity();
		CardService service = activity.getService();
		Card card = mockCard(service);
		Intent intent = new CardIntent(activity, CardAlarm.class, card);
		MockCardAlarm alarm = new MockCardAlarm(service);
		// Trigger the alarm and assert the notification was shown
		alarm.onReceive(activity, intent);
		assertTrue(alarm.getMockNotifier().wasNotificationShown());
	}

	@Test
	public void testCardAlarmUpdatesNextDate() {
		MainActivity activity = RobolectricHelper.createMainActivity();
		CardService service = activity.getService();
		Card card = mockCard(service);
		Intent intent = new CardIntent(activity, CardAlarm.class, card);
		CardAlarm alarm = new MockCardAlarm(service);
		DateTime initialDate = card.getNextNotificationDateTime();
		// Trigger the alarm and assert the notification time was updated
		alarm.onReceive(activity, intent);
		card = service.get(card.getId());
		DateTime updatedDate = card.getNextNotificationDateTime();
		assertNotSame("next date was not set", initialDate, updatedDate);
	}

	@Test
	public void testCardAlarmUpdatesStageFromOneDay() {
		assertCardAlarmUpdatesStage(CardStage.ONE_DAY);
	}

	@Test
	public void testCardAlarmUpdatesStageFromOneWeek() {
		assertCardAlarmUpdatesStage(CardStage.ONE_WEEK);
	}

	@Test
	public void testCardAlarmUpdatesStageFromOneMonth() {
		assertCardAlarmUpdatesStage(CardStage.ONE_MONTH);
	}

	@Test
	public void testCardAlarmUpdatesStageFromComplete() {
		assertCardAlarmUpdatesStage(CardStage.COMPLETE);
	}

	/**
	 * Test that all stages can be incremented.<br/>
	 * Note that this is covered in the more explicit tests above, but ensures
	 * that if we add a new {@link CardStage} without creating a test, we're
	 * still covered.
	 */
	@Test
	public void testCardAlarmUpdatesStage() {
		for (CardStage stage : CardStage.values()) {
			assertCardAlarmUpdatesStage(stage);
		}
	}

	/**
	 * Assert that the card's {@link CardStage} is updated to the next stage
	 * when the alarm fires.
	 *
	 * @param initialStage
	 *            the stage card has before the alarm
	 */
	private void assertCardAlarmUpdatesStage(CardStage initialStage) {
		MainActivity activity = RobolectricHelper.createMainActivity();
		CardService service = activity.getService();
		Card card = mockCard(service, initialStage);
		Intent intent = new CardIntent(activity, CardAlarm.class, card);
		CardAlarm alarm = new MockCardAlarm(service);
		// Trigger the alarm and assert the card's stage is updated
		alarm.onReceive(activity, intent);
		card = service.get(card.getId());
		CardStage expectedStage = CardStage.nextStage(initialStage);
		assertEquals(expectedStage, card.getStage());
	}

	private Card mockCard(CardService service, CardStage stage) {
		Card card = mockCard(service);
		card.setStage(stage);
		service.save(card);
		return card;
	}

	private Card mockCard(CardService service) {
		Card card = new Card("MockCardTitle", "MockCardDescription",
				CardColor.WHITE);
		service.save(card);
		return card;
	}
}
