package com.dosbcn.flashcards.events;

import com.dosbcn.flashcards.CardViewAdapter;
import com.dosbcn.flashcards.data.Card;

public class CardAddListener implements EventListener<Card> {

	private final CardViewAdapter adapter;

	public CardAddListener(CardViewAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void fireEvent(Card card) {
		adapter.add(card);
	}

}
