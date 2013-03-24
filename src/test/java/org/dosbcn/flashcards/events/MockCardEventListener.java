package org.dosbcn.flashcards.events;

import org.dosbcn.flashcards.data.Card;

public class MockCardEventListener implements EventListener<Card> {

	private boolean eventFired = false;

	@Override
	public void onEvent(Card data) {
		eventFired = true;
	}

	public boolean isEventFired() {
		return eventFired;
	}

}
