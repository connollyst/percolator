package org.dosbcn.flashcards.notifications;

import org.dosbcn.flashcards.data.MockCardService;

import android.content.Context;

public class MockCardAlarm extends CardAlarm {

	@Override
	protected void initialize(Context context) {
		setNotifier(new MockCardNotifier());
		setService(new MockCardService());
	}

}
