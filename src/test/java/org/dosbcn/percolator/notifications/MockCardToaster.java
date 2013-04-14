package org.dosbcn.percolator.notifications;

import org.dosbcn.percolator.notifications.CardToaster;

public class MockCardToaster implements CardToaster {

	boolean toasted = false;

	@Override
	public void cardSaved() {
		toasted = true;
	}

	public boolean isToasted() {
		return toasted;
	}

}
