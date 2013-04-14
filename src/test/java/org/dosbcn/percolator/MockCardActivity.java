package org.dosbcn.percolator;

import org.dosbcn.percolator.data.MockCardService;

/**
 * A mock extension of the {@link CardActivity} for testing purposes.
 * 
 * @author Sean Connolly
 */
public class MockCardActivity extends CardActivity {

	public MockCardActivity() {
		super(new MockCardService());
		onCreate(null);
	}

	public MockCardService getMockCardService() {
		return (MockCardService) getService();
	}

}
