package org.dosbcn.percolator.data;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.dosbcn.percolator.events.MockCardEventListener;
import org.dosbcn.percolator.notifications.CardNotificationTimerImpl;
import org.dosbcn.percolator.notifications.MockCardAlarmQueue;
import org.dosbcn.percolator.notifications.MockCardToaster;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for the {@link CardService}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestCardService {

    private MockCardRepository cardRepository;
    private MockCardAlarmQueue cardAlarmQueue;
    private MockCardToaster cardToaster;
    private CardService cardService;

    @Before
    public void beforeEachTest() {
        cardRepository = new MockCardRepository();
        cardAlarmQueue = new MockCardAlarmQueue();
        cardToaster = new MockCardToaster();
        cardService = new CardServiceImpl(cardRepository,
                new CardNotificationTimerImpl(), cardAlarmQueue, cardToaster);
    }

    @Test
    public void testCreateOnSave() {
        Card card = mockCard();
        cardService.save(card);
        assertNotSame(0, cardService.getAll().size());
    }

    @Test
    public void testNotificationDateSetOnSave() {
        Card card = mockCard();
        cardService.save(card);
        card = refreshCardFromDB(card);
        assertNotNull("Notification date not initialized.",
                card.getNextNotificationDate());
    }

    @Test
    public void testAlarmSetOnSave() {
        Card card = mockCard();
        cardService.save(card);
        card = refreshCardFromDB(card);
        Date alarm = cardAlarmQueue.getAlarm(card);
        assertEquals(card.getNextNotificationDate(), alarm);
    }

    @Test
    public void testToastedOnSave() {
        Card card = mockCard();
        cardService.save(card);
        assertTrue("Toast not sent.", cardToaster.isToasted());
    }

    @Test
    public void testEventFiredOnSave() {
        MockCardEventListener listener = new MockCardEventListener();
        cardService.setOnAddListener(listener);
        cardService.save(mockCard());
        assertTrue("onAdd event was not fired.", listener.isEventFired());
    }

    private Card mockCard() {
        String title = "MockTitle";
        String description = "MockDescription";
        CardColor color = CardColor.WHITE;
        Card card = new Card(title, description, color);
        return card;
    }

    private Card refreshCardFromDB(Card card) {
        return cardRepository.fetchById(card.getId());
    }
}
