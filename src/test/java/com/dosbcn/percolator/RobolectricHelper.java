package com.dosbcn.percolator;

import org.robolectric.Robolectric;

/**
 * A helper for creating Percolator activities with Robolectric.
 *
 * @author Sean Connolly
 */
public class RobolectricHelper {

	public static MainActivity createMainActivity() {
		return Robolectric.buildActivity(MainActivity.class).create().get();
	}

	public static ListActivity createListActivity() {
		return Robolectric.buildActivity(ListActivity.class).create().get();
	}

}
