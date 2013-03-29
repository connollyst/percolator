package org.dosbcn.flashcards.notifications;

import org.dosbcn.flashcards.data.Card;

import android.content.Context;
import android.content.Intent;

public class CardAlarmIntent extends Intent {

	public static final String CARD_ID_EXTRA = "org.dosbcn.flashcards.card.id";

	protected static final String ERROR_ID_MISSING = CARD_ID_EXTRA
			+ " missing from " + CardAlarmIntent.class.getSimpleName();

	public CardAlarmIntent(Context context, Card card) {
		super(context, CardAlarm.class);
		putExtra(CARD_ID_EXTRA, card.getId());
	}

	public int getCardId() {
		int id = getIntExtra(CARD_ID_EXTRA, -1);
		if (id == -1) {
			throw new RuntimeException(ERROR_ID_MISSING);
		}
		return id;
	}
}
