package com.dosbcn.percolator.notifications;

import com.dosbcn.percolator.data.Card;
import org.joda.time.DateTime;

public interface CardNotificationTimer {

	/**
	 * Returns the next time that a notification should be sent for this
	 * {@link Card}.<br/>
	 * The time is guaranteed to be within the range of 'Ok times' and not on a
	 * 'blacked out' date.
	 *
	 * @param card
	 *            the card to get the notification time for
	 * @return the next notification time
	 */
	DateTime getNextNotificationTime(Card card);

}
