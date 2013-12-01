package com.dosbcn.percolator.notifications;

import android.content.Context;
import android.content.Intent;
import com.dosbcn.percolator.data.Card;

public class CardIntent
        extends Intent {

    public static final String CARD_ID_EXTRA = "com.dosbcn.percolator.card.id";

    public CardIntent(Context context, Class intent, Card card) {
        super(context, intent);
        putExtra(CARD_ID_EXTRA, card.getId());
    }

}
