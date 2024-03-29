package com.dosbcn.percolator.data;

import android.content.Context;
import android.util.Log;
import com.dosbcn.percolator.events.EventListener;
import com.dosbcn.percolator.notifications.*;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

/**
 * The {@link CardService} is the central point of connectivity between the
 * {@link com.dosbcn.percolator.MainActivity} on the front end and all resources
 * on the back end; {@link CardRepository}, {@link CardAlarmQueue}, etc.
 *
 * @author Sean Connolly
 */
public class CardServiceImpl implements CardService {

	private static final String LOG_TAG = CardService.class.getName();

	private final CardRepository repository;
	private final CardAlarmQueue alarmQueue;
	private final CardToaster toaster;
	private final CardNotificationTimer timer;

	private EventListener<Card> onAddListener;

	public CardServiceImpl(Context context) {
		repository = new CardRepositoryImpl(context);
		alarmQueue = new CardAlarmQueueImpl(context);
		toaster = new CardToasterImpl(context);
		timer = new CardNotificationTimerImpl(repository);
	}

	CardServiceImpl(CardRepository repository, CardNotificationTimer timer,
			CardAlarmQueue alarmQueue, CardToaster toaster) {
		this.repository = repository;
		this.alarmQueue = alarmQueue;
		this.toaster = toaster;
		this.timer = timer;
	}

	/**
	 * Return all {@link Card}s in the database.
	 *
	 * @return all cards
	 */
	public List<Card> getAll() {
		List<Card> all = repository.fetchAll();
		Collections.sort(all);
		return all;
	}

	/**
	 * Return the stored {@link Card} with the given id.
	 *
	 * @param id
	 *            a valid card id
	 * @return the card for the id
	 */
	public Card get(int id) {
		return repository.fetchById(id);
	}

	/**
	 * Save a new card to the database.
	 *
	 * @param card
	 *            the new card
	 */
	public void save(Card card) {
		incrementNotificationTime(card);
		repository.create(card);
		alarmQueue.setAlarm(card);
		toaster.cardSaved();
		onAddEvent(card);
	}

	/**
	 * Queues all active alarms, replacing any existing alarms for those cards.
	 */
	public void resetAllAlarms() {
		Log.d(LOG_TAG, "Resetting all alarms.");
		alarmQueue.setAlarms(getAll());
	}

	/**
	 * Increment the card's {@link CardStage} to the next appropriate. For
	 * example, if it is currently at the second stage, it will be updated to
	 * the third. Changes are saved to the database.
	 *
	 * @param card
	 *            the card to update
	 */
	public void incrementCardStage(Card card) {
		CardStage currentStage = card.getStage();
		CardStage nextStage = CardStage.nextStage(currentStage);
		card.setStage(nextStage);
		// Update the next notification time to reflect the new stage
		incrementNotificationTime(card);
		repository.update(card);
	}

	/**
	 * Increment the card's next notification time.<br/>
	 * Note: this does not actually queue up the notification.
	 *
	 * @param card
	 *            the card to update
	 */
	private void incrementNotificationTime(Card card) {
		DateTime notificationDateTime = timer.getNextNotificationTime(card);
		card.setNextNotificationDateTime(notificationDateTime);
	}

	/**
	 * Registers an {@link EventListener} for the onAdd event.
	 *
	 * @param onAddListener
	 *            the onAdd event listener
	 */
	public void setOnAddListener(EventListener<Card> onAddListener) {
		this.onAddListener = onAddListener;
	}

	/**
	 * Fires the registered {@link EventListener} for the onAdd event.
	 *
	 * @param card
	 *            the card that was added
	 */
	private void onAddEvent(Card card) {
		if (onAddListener != null) {
			onAddListener.onEvent(card);
		}
	}

	public CardRepository getCardRepository() {
		return repository;
	}

	public CardNotificationTimer getCardNotificationTimer() {
		return timer;
	}

	public CardAlarmQueue getCardAlarmQueue() {
		return alarmQueue;
	}

	public CardToaster getCardToaster() {
		return toaster;
	}

}
