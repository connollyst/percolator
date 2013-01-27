package com.dosbcn.flashcards.data;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.dosbcn.flashcards.CardNotifier;
import com.dosbcn.flashcards.CardToaster;
import com.dosbcn.flashcards.events.EventListener;

public class CardService {

	private static final String LOG_TAG = CardService.class.getSimpleName();

	private final CardRepository repository;
	private final CardNotifier notifier;
	private final CardToaster toaster;

	private EventListener<Card> onAddListener;

	public CardService(Context context) {
		repository = new CardRepository(context);
		notifier = new CardNotifier(context);
		toaster = new CardToaster(context);
	}

	public List<Card> getAll() {
		return repository.fetchAll();
	}

	public void save(Card card) {
		repository.create(card);
		notifier.queue(card);
		toaster.cardSaved();
		// TODO get new card from repo
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
