package com.dosbcn.flashcards.notifications;

import android.util.Log;

/**
 * The stages of a {@link Card}'s life-cycle.<br/>
 * As notifications are sent to the user, the {@link Card} advances through
 * stages until it is {@code COMPLETE}. At that point, reminders should cease.
 * 
 * @author Sean Connolly
 */
public enum CardNotificationStage {

	ONE_DAY, ONE_WEEK, ONE_MONTH, COMPLETE;

	private static final String LOG_TAG = CardNotificationStage.class
			.getSimpleName();

	/**
	 * Returns the {@link CardNotificationStage} that should follow the one
	 * provided.
	 * 
	 * @param stage
	 *            the 'current' stage
	 * @return the 'next' stage
	 */
	public static CardNotificationStage nextStage(CardNotificationStage stage) {
		switch (stage) {
		case ONE_DAY:
			return ONE_WEEK;
		case ONE_WEEK:
			return ONE_MONTH;
		case ONE_MONTH:
			return COMPLETE;
		default:
			Log.w(LOG_TAG, "");
			return COMPLETE;
		}
	}

}
