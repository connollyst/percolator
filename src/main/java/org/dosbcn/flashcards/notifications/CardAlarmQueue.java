package org.dosbcn.flashcards.notifications;

import java.util.Date;

import org.dosbcn.flashcards.data.Card;

public interface CardAlarmQueue {

	Date getAlarm(Card card);

	void setAlarms(Iterable<Card> cards);

	void setAlarm(Card card);

}
