package org.dosbcn.flashcards.data;

import org.dosbcn.flashcards.notifications.CardNotificationTimer;
import org.dosbcn.flashcards.notifications.CardNotificationTimerImpl;
import org.dosbcn.flashcards.notifications.MockCardAlarmQueue;
import org.dosbcn.flashcards.notifications.MockCardToaster;

/**
 * A mock {@link CardService} implementation for testing purposes.<br/>
 * Actually extends the {@link CardServiceImpl} implementation so it's behavior
 * should be the same, however, all it's dependencies are mocked out for easy
 * testing.
 * 
 * @author Sean Connolly
 */
public class MockCardService extends CardServiceImpl implements CardService {

	public MockCardService() {
		super(new MockCardRepository(), new CardNotificationTimerImpl(),
				new MockCardAlarmQueue(), new MockCardToaster());
	}

	public MockCardRepository getMockCardRepository() {
		return (MockCardRepository) getCardRepository();
	}

	public CardNotificationTimer getMockCardNotificationTimer() {
		// TODO do we need to mock the timer?
		return getCardNotificationTimer();
	}

	public MockCardAlarmQueue getMockCardAlarmQueue() {
		return (MockCardAlarmQueue) getCardAlarmQueue();
	}

	public MockCardToaster getMockCardToaster() {
		return (MockCardToaster) getCardToaster();
	}

}
