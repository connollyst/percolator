package org.dosbcn.percolator;

import android.app.Application;
import android.util.Log;
import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.data.CardServiceImpl;

/**
 * The base Percolator application.
 *
 * @author Sean Connolly
 */
public class PercolatorApplication extends Application {

	private static final String LOG_TAG = PercolatorApplication.class.getName();

	private final CardService service;

	public PercolatorApplication() {
		service = new CardServiceImpl(this);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(LOG_TAG, "Percolator started, resetting all alarms.");
		service.resetAllAlarms();
		// TODO do this in another thread so the application starts quickly
		// http://developer.android.com/guide/components/processes-and-threads.html
	}

}
