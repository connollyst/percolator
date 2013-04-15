package org.dosbcn.percolator;

import org.dosbcn.percolator.data.MockCardService;

/**
 * A mock extension of the {@link MainActivity} for testing purposes.
 *
 * @author Sean Connolly
 */
public class MockMainActivity extends MainActivity {

    public MockMainActivity() {
        super(new MockCardService());
        onCreate(null);
    }

    public MockCardService getMockCardService() {
        return (MockCardService) getService();
    }

}
