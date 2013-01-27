package com.dosbcn.flashcards.data;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.dosbcn.flashcards.CardAlarm;
import com.dosbcn.flashcards.CardAlarmQueue;
import com.dosbcn.flashcards.CardNotifier;
import com.dosbcn.flashcards.CardToaster;
import com.dosbcn.flashcards.events.EventListener;

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
		alarmQueue.add(card);
		toaster.cardSaved();
		onAddEvent(card);
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
