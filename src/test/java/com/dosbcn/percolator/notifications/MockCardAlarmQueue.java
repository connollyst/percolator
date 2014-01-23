package com.dosbcn.percolator.notifications;

import android.app.AlarmManager;
import com.dosbcn.percolator.data.Card;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * A mock {@link CardAlarmQueue} implementation for testing purposes.<br/>
 * Actually extends the default {@link CardAlarmQueueImpl} implementation so as
 * to reuse it's implemented functionality as much as possible. Here we pretty
 * much just stub out any functions that would need to interact with a real
 * Android context.
 *
 * @author Sean Connolly
 */
public class MockCardAlarmQueue extends CardAlarmQueueImpl {

	private final Map<Integer, DateTime> alarms;

	public MockCardAlarmQueue() {
		super(null);
		this.alarms = new HashMap<Integer, DateTime>();
	}

	public DateTime getAlarm(Card card) {
		return alarms.get(card.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setAlarm(Card card, DateTime alarmDateTime) {
		alarms.put(card.getId(), alarmDateTime);
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
