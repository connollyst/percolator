package org.dosbcn.flashcards.events;

import org.dosbcn.flashcards.data.Card;

/**
 * A mock {@link EventListener} for testing purposes.<br/>
 * Provides an {@code isEventFired} function to easily assert that the listener
 * was or was not triggered as expected.
 * 
 * @author Sean Connolly
 */
public class MockCardEventListener implements EventListener<Card> {

	private boolean eventFired = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEvent(Card data) {
		eventFired = true;
	}

	/**
	 * Has the event been fired yet?
	 * 
	 * @return true/false if the event has/hasn't been fired
	 */
	public boolean isEventFired() {
		return eventFired;
	}

}
