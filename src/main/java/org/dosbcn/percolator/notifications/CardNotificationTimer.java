package org.dosbcn.percolator.notifications;

import org.dosbcn.percolator.data.Card;

import java.util.Date;

public interface CardNotificationTimer {

    Date getNextNotificationTime(Card card);

}
