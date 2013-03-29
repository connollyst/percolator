package org.dosbcn.flashcards.notifications;

import org.dosbcn.flashcards.data.CardService;

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
