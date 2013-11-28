package com.dosbcn.percolator.notifications;

import com.dosbcn.percolator.notifications.time.MockTimeUtilities;
import org.joda.time.DateTime;

/**
 * A mock extension of the {@link CardNotificationTimerImpl} for testing
 * purposes.
 *
 * @author Sean Connolly
 */
public class MockCardNotificationTimer extends CardNotificationTimerImpl {

	public MockCardNotificationTimer(DateTime now) {
		super(new MockTimeUtilities(now));
	}

}
