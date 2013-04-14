package org.dosbcn.percolator.notifications;

import java.util.Date;

import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.notifications.CardAlarmQueue;
import org.dosbcn.percolator.notifications.CardAlarmQueueImpl;

import android.app.AlarmManager;
import android.util.SparseArray;

/**
 * A mock {@link CardAlarmQueue} implementation for testing purposes.<br/>
 * Actually extends the default {@link CardAlarmQueueImpl} implementation so as
 * to reuse it's implemented functionality as much as possible. Here we pretty
 * much just stub out any functions that would need to interact with a real
 * Android context.
 * 
 * @author Sean Connolly
 */
public class MockCardAlarmQueue extends CardAlarmQueueImpl implements
		CardAlarmQueue {

	private final SparseArray<Date> alarms;

	public MockCardAlarmQueue() {
		super(null);
		this.alarms = new SparseArray<Date>();
	}

	public Date getAlarm(Card card) {
		return alarms.get(card.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setAlarm(Card card, Date alarmDate) {
		alarms.put(card.getId(), alarmDate);
		// there is no Android context, don't actually set any alarm
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AlarmManager getAlarmManager() {
		// No AlarmManager exists in unit tests
		return null;
	}

}
