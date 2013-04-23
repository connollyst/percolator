package org.dosbcn.percolator.notifications.time;

import org.joda.time.DateTime;

/**
 * A mock extension of the {@link RandomTimeGenerator} for testing purposes.
 *
 * @author Sean Connolly
 */
public class MockRandomTimeGenerator
        extends RandomTimeGenerator {

    private final DateTime now;

    public MockRandomTimeGenerator(DateTime now) {
        this.now = now;
    }

    /**
     * Returns a {@inheritDoc DateTime} for the current system time, <i>as set when constructing the mock</i>.
     *
     * @return the mock system time
     */
    @Override
    protected DateTime getNow() {
        return now;
    }

}
