package org.dosbcn.flashcards.notifications;

import org.dosbcn.flashcards.data.Card;
import org.dosbcn.flashcards.data.CardColor;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TestCardAlarm {

	@Test
	public void testCardAlarm() {
		CardAlarm alarm = new MockCardAlarm();
		Card card = new Card("MockCardTitle", "MockCardDescription",
				CardColor.WHITE);
		CardAlarmIntent intent = new CardAlarmIntent(null, card);
		alarm.onReceive(null, intent);
	}
}
