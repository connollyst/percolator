package org.dosbcn.flashcards.notifications.time;

import org.dosbcn.flashcards.data.Card;
import org.dosbcn.flashcards.notifications.CardAlarmQueue;
import org.dosbcn.flashcards.notifications.CardAlarmQueueImpl;

public class MockCardAlarmQueue extends CardAlarmQueueImpl implements
		CardAlarmQueue {

	public MockCardAlarmQueue() {
		super(null);
	}

	@Override
	public void setAlarm(Card card) {
		// there is no Android context, don't actually set any alarm
	}

}
