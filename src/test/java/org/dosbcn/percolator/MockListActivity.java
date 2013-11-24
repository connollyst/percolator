package org.dosbcn.percolator;

import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.data.MockCardService;

/**
 * A mock extension of the {@link ListActivity} for testing purposes.
 *
 * @author Sean Connolly
 */
public class MockListActivity extends ListActivity {

	public MockListActivity() {
		this(new MockCardService());
	}

	public MockListActivity(CardService cardService) {
		super(cardService);
		onCreate(null);
	}

	public MockCardService getMockCardService() {
		return (MockCardService) getService();
	}

}
