package com.dosbcn.percolator.notifications;

public class MockCardToaster
        implements CardToaster {

    boolean toasted = false;

    @Override
    public void cardSaved() {
        toasted = true;
    }

    public boolean isToasted() {
        return toasted;
    }

}
