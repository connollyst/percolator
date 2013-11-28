package com.dosbcn.percolator.notifications;

import com.dosbcn.percolator.data.Card;

public class MockCardNotifier
        implements CardNotifier {

    private boolean notificationShown = false;

    @Override
    public void showNotification(Card card) {
        notificationShown = true;
    }

    public boolean wasNotificationShown() {
        return notificationShown;
    }

}
