package com.dosbcn.percolator.notifications;

import com.dosbcn.percolator.data.Card;

public class MockCardNotifier implements CardNotifier {

	private boolean notificationShown = false;
	private boolean notificationClosed = false;

	@Override
	public void showNotification(Card card) {
		notificationShown = true;
	}

	@Override
	public void closeNotification(Card card) {
		notificationClosed = true;
	}

	public boolean wasNotificationShown() {
		return notificationShown;
	}

	public boolean wasNotificationClosed() {
		return notificationClosed;
	}

}
