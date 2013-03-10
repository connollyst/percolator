package org.dosbcn.flashcards.events;

import org.dosbcn.flashcards.CardViewAdapter;
import org.dosbcn.flashcards.data.Card;

public class CardAddListener implements EventListener<Card> {

	private final CardViewAdapter adapter;

	public CardAddListener(CardViewAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void onEvent(Card card) {
		adapter.add(card);
	}

}
