package com.dosbcn.flashcards.data;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.dosbcn.flashcards.CardActivity;
import com.dosbcn.flashcards.events.EventListener;
import com.dosbcn.flashcards.notifications.CardAlarmQueue;
import com.dosbcn.flashcards.notifications.CardToaster;

/**
 * The {@link CardService} is the central point of connectivity between the
 * {@link CardActivity} on the front end and all resources on the back end;
 * {@link CardRepository}, {@link CardAlarmQueue}, etc.
 * 
 * @author Sean Connolly
 */
public class CardService {

	private static final String LOG_TAG = CardService.class.getSimpleName();

	private final CardRepository repository;
	private final CardAlarmQueue alarmQueue;
	private final CardToaster toaster;

	private EventListener<Card> onAddListener;

	public CardService(Context context) {
		repository = new CardRepository(context);
		alarmQueue = new CardAlarmQueue(context);
		toaster = new CardToaster(context);
	}

	public List<Card> getAll() {
		return repository.fetchAll();
	}

	public Card get(int id) {
		return repository.fetchById(id);
	}

	public void save(Card card) {
		repository.create(card);
		alarmQueue.setAlarm(card);
		toaster.cardSaved();
		onAddEvent(card);
	}

	public void resetAllAlarms() {
		alarmQueue.setAlarms(getAll());
	}

	public void setOnAddListener(EventListener<Card> onAddListener) {
		this.onAddListener = onAddListener;
	}

	private void onAddEvent(Card card) {
		if (onAddListener != null) {
			onAddListener.onEvent(card);
		} else {
			Log.w(LOG_TAG, "No onAdd listener registered!");
		}
	}
}
