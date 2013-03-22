package org.dosbcn.flashcards.data;

import java.util.Date;
import java.util.List;

import org.dosbcn.flashcards.CardActivity;
import org.dosbcn.flashcards.events.EventListener;
import org.dosbcn.flashcards.notifications.CardAlarmQueue;
import org.dosbcn.flashcards.notifications.CardAlarmQueueImpl;
import org.dosbcn.flashcards.notifications.CardNotificationStage;
import org.dosbcn.flashcards.notifications.CardNotificationTimer;
import org.dosbcn.flashcards.notifications.CardNotificationTimerImpl;
import org.dosbcn.flashcards.notifications.CardToaster;
import org.dosbcn.flashcards.notifications.CardToasterImpl;

import android.content.Context;
import android.util.Log;

/**
 * The {@link CardService} is the central point of connectivity between the
 * {@link CardActivity} on the front end and all resources on the back end;
 * {@link CardRepository}, {@link CardAlarmQueue}, etc.
 * 
 * @author Sean Connolly
 */
public class CardServiceImpl implements CardService {

	private static final String LOG_TAG = CardService.class.getSimpleName();

	private final CardRepository repository;
	private final CardNotificationTimer timer;
	private final CardAlarmQueue alarmQueue;
	private final CardToaster toaster;

	private EventListener<Card> onAddListener;

	public CardServiceImpl(Context context) {
		repository = new CardRepositoryImpl(context);
		timer = new CardNotificationTimerImpl();
		alarmQueue = new CardAlarmQueueImpl(context);
		toaster = new CardToasterImpl(context);
	}

	CardServiceImpl(CardRepository repository, CardNotificationTimer timer,
			CardAlarmQueue alarmQueue, CardToaster toaster) {
		this.repository = repository;
		this.timer = timer;
		this.alarmQueue = alarmQueue;
		this.toaster = toaster;
	}

	/**
	 * Return all {@link Card}s in the database.
	 * 
	 * @return all cards
	 */
	public List<Card> getAll() {
		return repository.fetchAll();
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
	 */
	public void save(Card card) {
		Date notificationTime = timer.getNextNotificationTime(card);
		card.setNextNotificationDate(notificationTime);
		repository.create(card);
		alarmQueue.setAlarm(card);
		toaster.cardSaved();
		onAddEvent(card);
	}

	/**
	 * Queues all active alarms, replacing any existing alarms for those cards.
	 */
	public void resetAllAlarms() {
		alarmQueue.setAlarms(getAll());
	}

	/**
	 * Increment the card's {@link CardNotificationStage} to the next
	 * appropriate. For example, if it is currently at the second stage, it will
	 * be updated to the third. Changes are saved to the database.
	 * 
	 * @param card
	 *            the card to update
	 */
	public void incrementCardStage(Card card) {
		CardNotificationStage currentStage = card.getStage();
		CardNotificationStage nextStage = CardNotificationStage
				.nextStage(currentStage);
		card.setStage(nextStage);
		repository.update(card);
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
		} else {
			Log.w(LOG_TAG, "No onAdd listener registered!");
		}
	}
}
