package org.dosbcn.flashcards.data;

import static org.mockito.Mockito.mock;

import org.dosbcn.flashcards.notifications.CardAlarmQueue;
import org.dosbcn.flashcards.notifications.CardNotificationTimer;
import org.dosbcn.flashcards.notifications.CardToaster;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TestCardService {

	@Test
	public void testSave() {
		CardService service = new CardService(mock(CardRepository.class),
				mock(CardNotificationTimer.class), mock(CardAlarmQueue.class),
				mock(CardToaster.class));
		service.save(mockCard());
		// TODO make assertions
	}

	private Card mockCard() {
		Card card = new Card();
		card.setTitle("MockTitle");
		card.setDescription("MockDescription");
		return card;
	}
}
