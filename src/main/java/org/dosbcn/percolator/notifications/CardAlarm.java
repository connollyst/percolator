package org.dosbcn.percolator.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.data.CardServiceImpl;

/**
 * The alarm, {@link BroadcastReceiver}, element of the app.<br/>
 * Receives broadcasts from {@link AlarmManager} managed alarms when it is time
 * to show a {@link Notification} for a {@link Card}.
 *
 * @author Sean Connolly
 */
public class CardAlarm
        extends BroadcastReceiver {

    protected static final String ERROR_NULL_INTENT = CardAlarm.class
            .getSimpleName() + " received an alarm with a null intent.";
    protected static final String ERROR_BAD_INTENT = CardAlarm.class
            .getSimpleName() + " received an alarm with an invalid intent: ";
    protected static final String ERROR_ID_MISSING = CardAlarmIntent.CARD_ID_EXTRA
            + " missing from " + CardAlarmIntent.class.getSimpleName();
    private static final String LOG_TAG = CardAlarm.class.getName();
    private CardService service;
    private CardNotifier notifier;

    @Override
    public void onReceive(Context context, Intent intent) {
        initialize(context);
        handleAlarm(intent);
    }

    protected void initialize(Context context) {
        Log.i(LOG_TAG, "Preparing notification alarm.");
        service = new CardServiceImpl(context);
        notifier = new CardNotifierImpl(context);
    }

    protected void handleAlarm(Intent intent) {
        Card card = getCardFromIntent(intent);
        showNotification(card);
        updateStage(card);
    }

    private Card getCardFromIntent(Intent intent) {
        int cardId = getCardIdFromIntent(intent);
        return service.get(cardId);
    }

    private int getCardIdFromIntent(Intent intent) {
        int id = intent.getIntExtra(CardAlarmIntent.CARD_ID_EXTRA, -1);
        if (id == -1) {
            throw new RuntimeException(ERROR_ID_MISSING);
        }
        return id;
    }

    private void showNotification(Card card) {
        notifier.showNotification(card);
    }

    private void updateStage(Card card) {
        service.incrementCardStage(card);
    }

    public CardService getService() {
        return service;
    }

    protected void setService(CardService service) {
        this.service = service;
    }

    public CardNotifier getNotifier() {
        return notifier;
    }

    protected void setNotifier(CardNotifier notifier) {
        this.notifier = notifier;
    }
}
