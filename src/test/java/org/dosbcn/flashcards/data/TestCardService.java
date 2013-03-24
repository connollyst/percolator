package org.dosbcn.flashcards.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.dosbcn.flashcards.notifications.CardNotificationTimerImpl;
import org.dosbcn.flashcards.notifications.CardToaster;
import org.dosbcn.flashcards.notifications.MockCardAlarmQueue;
import org.dosbcn.flashcards.notifications.MockCardToaster;
import org.junit.Before;
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

	private MockCardRepository cardRepository;
	private MockCardAlarmQueue cardAlarmQueue;
	private MockCardToaster cardToaster;
	private CardService cardService;

	@Before
	public void beforeEachTest() {
		cardRepository = new MockCardRepository();
		cardAlarmQueue = new MockCardAlarmQueue();
		cardToaster = new MockCardToaster();
		cardService = new CardServiceImpl(cardRepository,
				new CardNotificationTimerImpl(), cardAlarmQueue, cardToaster);
	}

	@Test
	public void testCreateOnSave() {
		Card card = mockCard();
		cardService.save(card);
		assertNotSame(0, cardService.getAll().size());
	}

	@Test
	public void testNotificationDateSetOnSave() {
		Card card = mockCard();
		cardService.save(card);
		card = refreshCardFromDB(card);
		assertNotNull("Notification date not initialized.",
				card.getNextNotificationDate());
	}

	@Test
	public void testAlarmSetOnSave() {
		Card card = mockCard();
		cardService.save(card);
		card = refreshCardFromDB(card);
		Date alarm = cardAlarmQueue.getAlarm(card);
		assertEquals(card.getNextNotificationDate(), alarm);
	}

	@Test
	public void testToastedOnSave() {
		Card card = mockCard();
		cardService.save(card);
		assertTrue("Toast not sent.", cardToaster.isToasted());
	}

	@Test
	public void testEventFiredOnSave() {
		fail("Test not implemented.");
	}

	private Card mockCard() {
		String title = "MockTitle";
		String description = "MockDescription";
		CardColor color = CardColor.WHITE;
		Card card = new Card(title, description, color);
		return card;
	}

	private Card refreshCardFromDB(Card card) {
		return cardRepository.fetchById(card.getId());
	}
}
