package com.dosbcn.percolator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.dosbcn.percolator.R;
import com.dosbcn.percolator.data.Card;
import com.dosbcn.percolator.data.CardService;
import com.dosbcn.percolator.data.CardServiceImpl;
import com.dosbcn.percolator.events.CardAddListener;
import com.dosbcn.percolator.view.CardViewAdapter;

import java.util.List;

/**
 * The {@link Activity} displaying the list of flash cards.<br/>
 * Our interface is a single {@link ListView} and is managed by simply extending
 * the {@link android.app.ListActivity}.
 *
 * @author Sean Connolly
 */
public class ListActivity extends SherlockListActivity {

	private static final String LOG_TAG = ListActivity.class.getName();
	private final CardService service;

	public ListActivity() {
		service = new CardServiceImpl(this);
	}

	protected ListActivity(CardService service) {
		this.service = service;
	}

	public CardService getService() {
		return service;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		initApplication();
	}

	private void initApplication() {
		CardViewAdapter adapter = initAdapter();
		initServiceListeners(adapter);
	}

	private CardViewAdapter initAdapter() {
		Log.i(LOG_TAG, "Initializing adapter.");
		List<Card> cards = service.getAll();
		Log.i(LOG_TAG, "Found cards: " + cards.size());
		CardViewAdapter adapter = new CardViewAdapter(this, cards);
		getListView().setAdapter(adapter);
		return adapter;
	}

	private void initServiceListeners(final CardViewAdapter adapter) {
		service.setOnAddListener(new CardAddListener(adapter));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.list_activity_actions, menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// No supported actions yet
		return super.onOptionsItemSelected(item);
	}

}
