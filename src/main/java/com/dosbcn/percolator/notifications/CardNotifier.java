package com.dosbcn.percolator.notifications;

import com.dosbcn.percolator.data.Card;

public interface CardNotifier {

	/**
	 * Displays an Android {@link android.app.Notification} for the given
	 * {@link Card}.
	 *
	 * @param card
	 *            the card to display a notification for
	 */
	void showNotification(Card card);

	/**
	 * Cancels the Android {@link android.app.Notification} for the given
	 * {@link Card}.
	 *
	 * @param card
	 *            the card to cancel the notification for
	 */
	void closeNotification(Card card);

}
