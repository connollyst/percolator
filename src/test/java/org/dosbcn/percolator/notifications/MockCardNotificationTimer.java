package org.dosbcn.percolator.notifications;

import org.joda.time.DateTime;

/**
 * A mock extension of the {@link CardNotificationTimerImpl} for testing
 * purposes.
 *
 * @author Sean Connolly
 */
public class MockCardNotificationTimer extends CardNotificationTimerImpl {

	private final DateTime now;

	public MockCardNotificationTimer(DateTime now) {
		this.now = now;
	}

	/**
	 * Returns a {@inheritDoc DateTime} for the current system time, <i>as set
	 * when constructing the mock</i>.
	 *
	 * @return the mock system time
	 */
	@Override
	protected DateTime getNow() {
		return now;
	}

}
