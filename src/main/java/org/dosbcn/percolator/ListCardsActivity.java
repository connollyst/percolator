package org.dosbcn.percolator;

import java.util.List;

import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.data.CardServiceImpl;
import org.dosbcn.percolator.events.CardAddListener;
import org.dosbcn.percolator.view.CardViewAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.widget.ListView;

/**
 * The {@link Activity} displaying the list of flash cards.<br/>
 * Our interface is a single {@link ListView} and is managed by simply extending
 * the {@link ListActivity}.
 * 
 * @author Sean Connolly
 */
public class ListCardsActivity extends FragmentActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private final CardService service;

	public ListCardsActivity() {
		service = new CardServiceImpl(this);
	}

	protected ListCardsActivity(CardService service) {
		this.service = service;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initApplication();
	}

	private void initApplication() {
		CardViewAdapter adapter = initAdapter();
		initServiceListeners(adapter);
		initLoadManager();
	}

	private CardViewAdapter initAdapter() {
		List<Card> cards = service.getAll();
		CardViewAdapter adapter = new CardViewAdapter(this, cards);
		getListView().setAdapter(adapter);
		return adapter;
	}

	private void initServiceListeners(final CardViewAdapter adapter) {
		service.setOnAddListener(new CardAddListener(adapter));
	}

	protected void initLoadManager() {
		// TODO do we need all this LoadManger crap?
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// do nothing
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// do nothing
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// do nothing
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// do nothing
	}

	public CardService getService() {
		return service;
	}

	public ListView getListView() {
		return (ListView) findViewById(R.id.list_view);
	}

}
