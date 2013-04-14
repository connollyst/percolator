package org.dosbcn.percolator.notifications;

import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.notifications.CardAlarm;

import android.content.Context;

public class MockCardAlarm extends CardAlarm {

	public MockCardAlarm(CardService service) {
		setNotifier(new MockCardNotifier());
		setService(service);
	}

	@Override
	protected void initialize(Context context) {
		// do nothing
	}

	public MockCardNotifier getMockNotifier() {
		return (MockCardNotifier) getNotifier();
	}

}
