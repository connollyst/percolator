package com.dosbcn.percolator.events;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.dosbcn.percolator.RobolectricHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.dosbcn.percolator.MainActivity;
import com.dosbcn.percolator.data.Card;
import com.dosbcn.percolator.data.CardService;

/**
 * Test cases for the {@link SaveButtonClickListener}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestSaveButtonClickListener {

	// TODO this is a great test, but it's a mess.. this should be multiple,
	// more directed tests.

	@Test
	public void testSaveOnClick() {
		MainActivity cardActivity = RobolectricHelper.createMainActivity();
		CardService cardService = cardActivity.getService();
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
