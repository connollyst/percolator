package org.dosbcn.percolator.data;

import android.util.Log;

/**
 * The stages of a {@link Card}'s life-cycle.<br/>
 * As notifications are sent to the user, the {@link Card} advances through
 * stages until it is {@code COMPLETE}. At that point, reminders should cease.
 *
 * @author Sean Connolly
 */
public enum CardStage {

	ONE_DAY, ONE_WEEK, ONE_MONTH, COMPLETE;

	private static final String LOG_TAG = CardStage.class.getName();

	/**
	 * Returns the {@link CardStage} that should follow the one provided.
	 *
	 * @param stage
	 *            the 'current' stage
	 * @return the 'next' stage
	 */
	public static CardStage nextStage(CardStage stage) {
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