package org.dosbcn.flashcards.notifications;

import java.util.Date;

import org.dosbcn.flashcards.data.Card;

public interface CardNotificationTimer {

	Date getNextNotificationTime(Card card);

}
