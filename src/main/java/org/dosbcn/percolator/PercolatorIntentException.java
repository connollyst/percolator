package org.dosbcn.percolator;

/**
 * A {@link RuntimeException} indicating an Android Intent could not be cast to
 * a specific Percolator intent.
 *
 * @author Sean Connolly
 */
public class PercolatorIntentException
        extends ClassCastException {

    /**
     * Default constructor.
     *
     * @param message
     *         the description of the exception
     * @param cause
     *         the root of the exception
     */
    public PercolatorIntentException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }

}
