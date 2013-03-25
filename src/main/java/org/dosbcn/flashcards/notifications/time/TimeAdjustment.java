package org.dosbcn.flashcards.notifications.time;

public class TimeAdjustment {

	private final int field;
	private final int amount;

	public TimeAdjustment(int field, int amount) {
		super();
		this.field = field;
		this.amount = amount;
	}

	public int getField() {
		return field;
	}

	public int getAmount() {
		return amount;
	}

}