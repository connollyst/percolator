package org.dosbcn.percolator;

import android.widget.ListView;
import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardColor;
import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.data.MockCardService;
import org.dosbcn.percolator.view.CardViewAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the {@link ListActivity}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestListActivity {

	/**
	 * Display the ListActivity with one Card in the store, it should be the
	 * only Card displayed.
	 */
	@Test
	public void testListWithOneCard() {
		Card card = new Card("Title", "Description", CardColor.GREEN);
		CardService service = new MockCardService();
		service.save(card);
		ListActivity activity = new MockListActivity(service);
		ListView view = activity.getListView();
		CardViewAdapter adapter = (CardViewAdapter) view.getAdapter();
		assertEquals("expected one card", 1, adapter.getCount());
		Card card1 = adapter.getItem(0);
		assertEquals("expected the test card", card, card1);
	}
}
