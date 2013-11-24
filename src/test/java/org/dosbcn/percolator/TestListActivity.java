package org.dosbcn.percolator;

import static org.junit.Assert.assertEquals;

import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardColor;
import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.data.MockCardService;
import org.dosbcn.percolator.view.CardViewAdapter;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.ListView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Test cases for the {@link ListActivity}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestListActivity {

	/**
	 * Display the {@link ListActivity} with one card in the store. It should be
	 * the only card displayed.
	 */
	@Test
	public void testListWithOneCard() {
		Card card = new Card("Card", "Description", CardColor.GREEN);
		ListActivity activity = mockListActivity(card);
		assertCardOrder(activity, card);
	}

	/**
	 * Display the {@link ListActivity} with two cards in the store. Both cards
	 * should be displayed and the second, with it's later start date, should be
	 * listed first.
	 */
	@Test
	public void testListWithTwoCards() {
		Card card1 = new Card("Card1", "Description1", CardColor.GREEN);
		Card card2 = new Card("Card2", "Description2", CardColor.GREEN);
		card1.setStartDate(new DateTime(1984, 8, 6, 12, 0));
		card2.setStartDate(new DateTime(1984, 8, 6, 12, 30));
		ListActivity activity = mockListActivity(card1, card2);
		assertCardOrder(activity, card2, card1);
	}

	/**
	 * Display the {@link ListActivity} with three cards in the store. All cards
	 * should be displayed and in the correct order. Note that their start dates
	 * differs from their insertion order to ensure their returned order is
	 * based on the start date.
	 */
	@Test
	public void testListWithThreeCards() {
		Card card1 = new Card("Card1", "Description1", CardColor.GREEN);
		Card card2 = new Card("Card2", "Description2", CardColor.GREEN);
		Card card3 = new Card("Card3", "Description3", CardColor.GREEN);
		card1.setStartDate(new DateTime(1984, 8, 6, 12, 0)); // first
		card2.setStartDate(new DateTime(1984, 8, 6, 12, 59)); // third
		card3.setStartDate(new DateTime(1984, 8, 6, 12, 30)); // second
		ListActivity activity = mockListActivity(card1, card2, card3);
		assertCardOrder(activity, card2, card3, card1);
	}

	/**
	 * Create a {@link MockListActivity} with the given cards.
	 *
	 * @param cards
	 *            the test cards
	 * @return the mock activity
	 */
	private ListActivity mockListActivity(Card... cards) {
		CardService service = new MockCardService();
		for (Card card : cards) {
			service.save(card);
		}
		return new MockListActivity(service);
	}

	/**
	 * Assert that the cards in the {@link CardViewAdapter} are ordered as
	 * expected.
	 *
	 * @param activity
	 *            the list activity
	 * @param cards
	 *            the array of expected cards, in their expected order
	 */
	private void assertCardOrder(ListActivity activity, Card... cards) {
		ListView view = activity.getListView();
		CardViewAdapter adapter = (CardViewAdapter) view.getAdapter();
		int expectedCards = cards.length;
		int actualCards = adapter.getCount();
		assertEquals("expected " + expectedCards + " card(s)", expectedCards,
				actualCards);
		for (int i = 0; i < expectedCards; i++) {
			Card expectedCard = cards[i];
			Card actualCard = adapter.getItem(i);
			assertEquals("expected '" + expectedCard.getTitle() + "' at index "
					+ i, expectedCard, actualCard);
		}
	}

}
