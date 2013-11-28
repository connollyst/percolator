package com.dosbcn.percolator.notifications;

import android.content.Context;
import android.content.Intent;
import com.dosbcn.percolator.data.Card;

public class CardAlarmIntent
        extends Intent {

    public static final String CARD_ID_EXTRA = "org.dosbcn.flashcards.card.id";

    public CardAlarmIntent(Context context, Card card) {
        super(context, CardAlarm.class);
        putExtra(CARD_ID_EXTRA, card.getId());
    }

}
