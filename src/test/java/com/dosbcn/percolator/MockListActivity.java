package com.dosbcn.percolator;

import com.dosbcn.percolator.ListActivity;
import com.dosbcn.percolator.data.CardService;
import com.dosbcn.percolator.data.MockCardService;

/**
 * A mock extension of the {@link com.dosbcn.percolator.ListActivity} for testing purposes.
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
