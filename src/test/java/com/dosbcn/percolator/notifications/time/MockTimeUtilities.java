package com.dosbcn.percolator.notifications.time;

import org.joda.time.DateTime;

/**
 * A mock extension of the {@link TimeUtilities} for testing purposes.
 *
 * @author Sean Connolly
 */
public class MockTimeUtilities extends TimeUtilities {

	private final DateTime now;

	/**
	 * Create a new mock time utilities which perceives 'now' to be the given
	 * time.
	 *
	 * @param now
	 *            the time to use as 'now'
	 */
	public MockTimeUtilities(DateTime now) {
		this.now = now;
	}

	/**
	 * Rather than returning the real time, returns a prescribed time for
	 * testing with.
	 *
	 * @return the prescribed time
	 */
	@Override
	public DateTime getNow() {
		return now;
	}

}
