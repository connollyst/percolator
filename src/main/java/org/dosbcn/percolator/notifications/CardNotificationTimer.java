package org.dosbcn.percolator.notifications;

import org.dosbcn.percolator.data.Card;
import org.joda.time.DateTime;

public interface CardNotificationTimer {

    /**
     * Returns the next time that a notification should be sent for this
     * {@link Card}.<br/>
     * The time is guaranteed to be within the range of 'Ok times'.
     *
     * @param card
     * @return
     */
    DateTime getNextNotificationTime(Card card);

}
