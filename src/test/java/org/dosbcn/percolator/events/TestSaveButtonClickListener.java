package org.dosbcn.percolator.events;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.dosbcn.percolator.MockMainActivity;
import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.MockCardService;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the {@link SaveButtonClickListener}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestSaveButtonClickListener {

    // TODO this is a great test, but it's a mess.. this should be multiple, more directed tests.

    @Test
    public void testSaveOnClick() {
        MockMainActivity cardActivity = new MockMainActivity();
        MockCardService cardService = cardActivity.getMockCardService();
        SaveButtonClickListener listener = new SaveButtonClickListener(
                cardActivity);
        cardActivity.findTitleField().setText("Hello");
        cardActivity.findDescriptionField().setText("World");
        listener.onClick(null);
        List<Card> cards = cardService.getAll();
        assertEquals(1, cards.size());
        Card card = cards.get(0);
        assertEquals("Hello", card.getTitle());
        assertEquals("World", card.getDescription());
    }

}
