package org.dosbcn.flashcards.notifications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.dosbcn.flashcards.CardActivity;
import org.dosbcn.flashcards.MockCardActivity;
import org.dosbcn.flashcards.data.Card;
import org.dosbcn.flashcards.data.CardColor;
import org.dosbcn.flashcards.data.CardService;
import org.dosbcn.flashcards.data.CardStage;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TestCardAlarm {

	@Test
	public void testCardAlarmShowsNotification() {
		CardActivity activity = new MockCardActivity();
		CardService service = activity.getService();
		Card card = mockCard(service);
		CardAlarmIntent intent = new CardAlarmIntent(activity, card);
		MockCardAlarm alarm = new MockCardAlarm(service);
		// Trigger the alarm and assert the notification was shown
		alarm.onReceive(activity, intent);
		assertTrue(alarm.getMockNotifier().wasNotificationShown());
	}

	@Test
	public void testCardAlarmUpdatesNextDate() {
		CardActivity activity = new MockCardActivity();
		CardService service = activity.getService();
		Card card = mockCard(service);
		CardAlarmIntent intent = new CardAlarmIntent(activity, card);
		CardAlarm alarm = new MockCardAlarm(service);
		Date initialDate = card.getNextNotificationDate();
		// Trigger the alarm and assert the notification time was updated
		alarm.onReceive(activity, intent);
		card = service.get(card.getId());
		Date updatedDate = card.getNextNotificationDate();
		assertNotSame("next date was not set", initialDate, updatedDate);
	}

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
		CardActivity activity = new MockCardActivity();
		CardService service = activity.getService();
		Card card = mockCard(service, initialStage);
		CardAlarmIntent intent = new CardAlarmIntent(activity, card);
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
		return card;
	}

	private Card mockCard(CardService service) {
		Card card = new Card("MockCardTitle", "MockCardDescription",
				CardColor.WHITE);
		service.save(card);
		return card;
	}
}
