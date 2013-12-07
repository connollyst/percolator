package com.dosbcn.percolator.notifications;

import com.dosbcn.percolator.data.CardRepository;
import com.dosbcn.percolator.data.MockCardRepository;
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
		this(new MockCardRepository(), now);
	}

	public MockCardNotificationTimer(CardRepository repository, DateTime now) {
		super(repository, new MockTimeUtilities(now));
	}

}
