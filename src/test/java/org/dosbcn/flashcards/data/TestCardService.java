package org.dosbcn.flashcards.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.dosbcn.flashcards.notifications.CardNotificationTimerImpl;
import org.dosbcn.flashcards.notifications.CardToaster;
import org.dosbcn.flashcards.notifications.time.MockCardAlarmQueue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Test cases for the {@link CardService}.
 * 
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestCardService {

	@Test
	public void testCreateOnSave() {
		CardService service = mockCardService();
		Card card = mockCard();
		service.save(card);
		assertNotSame(0, service.getAll().size());
	}

	@Test
	public void testNotificationDateSetOnSave() {
		CardService service = mockCardService();
		Card card = mockCard();
		service.save(card);
		card = refreshCardFromService(card, service);
		assertNotNull("Notification date not initialized.",
				card.getNextNotificationDate());
	}

	@Test
	public void testAlarmSetOnSave() {
		CardService service = mockCardService();
		Card card = mockCard();
		service.save(card);
		card = refreshCardFromService(card, service);

	}

	@Test
	public void testToastedOnSave() {
		fail("Test not implemented.");
	}

	@Test
	public void testEventFiredOnSave() {
		fail("Test not implemented.");
	}

	private CardService mockCardService() {
		return new CardServiceImpl(new MockCardRepository(),
				new CardNotificationTimerImpl(), new MockCardAlarmQueue(),
				mock(CardToaster.class));
	}

	private Card mockCard() {
		String title = "MockTitle";
		String description = "MockDescription";
		CardColor color = CardColor.WHITE;
		Card card = new Card(title, description, color);
		return card;
	}

	private Card refreshCardFromService(Card card, CardService service) {
		return service.get(card.getId());
	}
}
