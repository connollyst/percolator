package org.dosbcn.percolator;

import org.dosbcn.percolator.data.CardServiceImpl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Sean Connolly
 */
public class BootReceiver extends BroadcastReceiver {

	private static final String LOG_TAG = BootReceiver.class.getName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(LOG_TAG, "Device boot detected, queueing outstanding alarms.");
		new CardServiceImpl(context).resetAllAlarms();
		// TODO do this in another thread
		// http://developer.android.com/guide/components/processes-and-threads.html
	}

}
