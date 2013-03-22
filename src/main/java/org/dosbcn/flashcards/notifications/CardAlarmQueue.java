package org.dosbcn.flashcards.notifications;

import org.dosbcn.flashcards.data.Card;

public interface CardAlarmQueue {

	public void setAlarms(Iterable<Card> cards);

	public void setAlarm(Card card);

}
