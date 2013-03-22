package org.dosbcn.flashcards.notifications;

import org.dosbcn.flashcards.data.Card;

public interface CardNotifier {

	void showNotification(Card card);

}
