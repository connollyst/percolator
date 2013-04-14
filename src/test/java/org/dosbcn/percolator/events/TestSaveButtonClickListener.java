package org.dosbcn.percolator.events;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.dosbcn.percolator.MockCardActivity;
import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.MockCardService;
import org.dosbcn.percolator.events.SaveButtonClickListener;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Test cases for the {@link SaveButtonClickListener}.
 * 
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestSaveButtonClickListener {

	// TODO this is a great test, but it's a mess..
	// this should be multiple, more directed tests.

	@Test
	public void testSaveOnClick() {
		MockCardActivity cardActivity = new MockCardActivity();
		MockCardService cardService = cardActivity.getMockCardService();
		SaveButtonClickListener listener = new SaveButtonClickListener(
				cardActivity);
		cardActivity.findTitleField().setText("Hello");
		cardActivity.findDescriptionField().setText("World");
		listener.onClick();
		List<Card> cards = cardService.getAll();
		assertEquals(1, cards.size());
		Card card = cards.get(0);
		assertEquals("Hello", card.getTitle());
		assertEquals("World", card.getDescription());
	}

}
