package org.dosbcn.percolator.events;

import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.view.CardViewAdapter;

/**
 * An {@link EventListener} indicating a new {@link Card} has been created.
 * 
 * @author Sean Connolly
 */
public class CardAddListener implements EventListener<Card> {

	private final CardViewAdapter adapter;

	public CardAddListener(CardViewAdapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * Handle the event.<br/>
	 * The card is added to the {@link CardViewAdapter} to be displayed.
	 * 
	 * @param card
	 *            the new card
	 */
	@Override
	public void onEvent(Card card) {
		adapter.add(card);
	}

}
