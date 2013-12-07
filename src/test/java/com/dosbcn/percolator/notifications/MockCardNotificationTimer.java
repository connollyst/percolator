package com.dosbcn.percolator.notifications;

import org.joda.time.DateTime;

import com.dosbcn.percolator.data.CardRepository;
import com.dosbcn.percolator.notifications.time.MockTimeUtilities;

/**
 * A mock extension of the {@link CardNotificationTimerImpl} for testing
 * purposes.
 *
 * @author Sean Connolly
 */
public class MockCardNotificationTimer extends CardNotificationTimerImpl {

	public MockCardNotificationTimer(CardRepository repository, DateTime now) {
		super(repository, new MockTimeUtilities(now));
	}

}
