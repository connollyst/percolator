package com.dosbcn.percolator;

import org.robolectric.Robolectric;

/**
 * @author Sean Connolly
 */
public class PercolatorTestHelper {

	public static MainActivity createMainActivity() {
		return Robolectric.buildActivity(MainActivity.class).create().get();
	}

	public static ListActivity createListActivity() {
		return Robolectric.buildActivity(ListActivity.class).create().get();
	}

}
