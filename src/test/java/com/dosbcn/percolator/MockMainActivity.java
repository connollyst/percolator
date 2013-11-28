package com.dosbcn.percolator;

import com.dosbcn.percolator.MainActivity;
import com.dosbcn.percolator.data.MockCardService;

/**
 * A mock extension of the {@link com.dosbcn.percolator.MainActivity} for testing purposes.
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
