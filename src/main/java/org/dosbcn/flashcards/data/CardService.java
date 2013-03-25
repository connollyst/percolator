package org.dosbcn.flashcards.data;

import java.util.List;

import org.dosbcn.flashcards.events.EventListener;

public interface CardService {

	/**
	 * Return all {@link Card}s in the database.
	 * 
	 * @return all cards
	 */
	List<Card> getAll();

	/**
	 * Return the stored {@link Card} with the given id.
	 * 
	 * @param id
	 *            a valid card id
	 * @return the card for the id
	 */
	Card get(int id);

	/**
	 * Save a new card to the database.
	 * 
	 * @param card
	 */
	void save(Card card);

	/**
	 * Queues all active alarms, replacing any existing alarms for those cards.
	 */
	void resetAllAlarms();

	/**
	 * Increment the card's {@link CardStage} to the next
	 * appropriate. For example, if it is currently at the second stage, it will
	 * be updated to the third. Changes are saved to the database.
	 * 
	 * @param card
	 *            the card to update
	 */
	void incrementCardStage(Card card);

	/**
	 * Registers an {@link EventListener} for the onAdd event.
	 * 
	 * @param onAddListener
	 *            the onAdd event listener
	 */
	void setOnAddListener(EventListener<Card> onAddListener);

}
