package com.dosbcn.percolator.data;

import com.dosbcn.percolator.notifications.CardNotificationTimer;
import com.dosbcn.percolator.notifications.CardNotificationTimerImpl;
import com.dosbcn.percolator.notifications.MockCardAlarmQueue;
import com.dosbcn.percolator.notifications.MockCardToaster;

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
		this(new MockCardRepository());
	}

	public MockCardService(CardRepository repository) {
		super(repository, new CardNotificationTimerImpl(repository),
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
