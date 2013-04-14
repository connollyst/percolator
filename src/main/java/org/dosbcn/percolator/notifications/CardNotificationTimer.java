package org.dosbcn.percolator.notifications;

import java.util.Date;

import org.dosbcn.percolator.data.Card;

public interface CardNotificationTimer {

	Date getNextNotificationTime(Card card);

}
