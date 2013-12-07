package com.dosbcn.percolator.data;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.dosbcn.percolator.MainActivity;
import com.dosbcn.percolator.PercolatorTestHelper;
import com.dosbcn.percolator.events.MockCardEventListener;
import com.dosbcn.percolator.notifications.CardNotificationTimerImpl;
import com.dosbcn.percolator.notifications.MockCardAlarmQueue;
import com.dosbcn.percolator.notifications.MockCardToaster;

/**
 * Test cases for the {@link CardService}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestCardService {

	private CardRepository cardRepository;
	private MockCardAlarmQueue cardAlarmQueue;
	private MockCardToaster cardToaster;
	private CardService cardService;

	@Before
	public void beforeEachTest() {
		MainActivity activity = PercolatorTestHelper.createMainActivity();
		cardRepository = activity.getService().getCardRepository();
		cardAlarmQueue = new MockCardAlarmQueue();
		cardToaster = new MockCardToaster();
		cardService = new CardServiceImpl(cardRepository,
				new CardNotificationTimerImpl(cardRepository), cardAlarmQueue,
				cardToaster);
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
		DateTime alarm = cardAlarmQueue.getAlarm(card);
		assertEquals(card.getNextNotificationDateTime(), alarm);
	}

	@Test
	public void testToastedOnSave() {
		Card card = mockCard();
		cardService.save(card);
		assertTrue("Toast not sent.", cardToaster.isToasted());
	}

	@Test
	public void testEventFiredOnSave() {
		MockCardEventListener listener = new MockCardEventListener();
		cardService.setOnAddListener(listener);
		cardService.save(mockCard());
		assertTrue("onAdd event was not fired.", listener.isEventFired());
	}

	private Card mockCard() {
		String title = "MockTitle";
		String description = "MockDescription";
		CardColor color = CardColor.WHITE;
		return new Card(title, description, color);
	}

	private Card refreshCardFromDB(Card card) {
		return cardRepository.fetchById(card.getId());
	}
}
