package org.dosbcn.flashcards.notifications;

import org.dosbcn.flashcards.data.Card;

public interface CardAlarmQueue {

	/**
	 * Set an Android alarm for each of the specified {@code cards}. The alarms
	 * will be handled by the {@link CardAlarm} broadcast receiver who will
	 * display an appropriate notification.
	 * 
	 * @param cards
	 *            the cards the notifications will display
	 */
	void setAlarms(Iterable<Card> cards);

	/**
	 * Set an Android alarm for the specified {@code card}. The alarm will be
	 * handled by the {@link CardAlarm} broadcast receiver who will display an
	 * appropriate notification.
	 * 
	 * @param card
	 *            the card the notification will display
	 */
	void setAlarm(Card card);

}
