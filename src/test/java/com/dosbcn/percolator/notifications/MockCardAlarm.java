package com.dosbcn.percolator.notifications;

import android.content.Context;
import com.dosbcn.percolator.data.CardService;

/**
 * A mock extension of the {@link CardAlarm} for testing purposes.
 *
 * @author Sean Connolly
 */
public class MockCardAlarm extends CardAlarm {

	/**
	 * Default constructor.
	 *
	 * @param service
	 *            the card service to use in testing
	 */
	public MockCardAlarm(CardService service) {
		setNotifier(new MockCardNotifier());
		setService(service);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize(Context context) {
		// do nothing
	}

	/**
	 * Return the {@link MockCardNotifier} being used in the test.<br/>
	 * Note: this is the same as {@code getNotifier} except it handles the cast
	 * for us.
	 *
	 * @return the mock card notifier
	 */
	public MockCardNotifier getMockNotifier() {
		return (MockCardNotifier) getNotifier();
	}

}
