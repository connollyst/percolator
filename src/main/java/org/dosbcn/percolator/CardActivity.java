package org.dosbcn.percolator;

import java.util.List;

import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.data.CardServiceImpl;
import org.dosbcn.percolator.events.CardAddListener;
import org.dosbcn.percolator.events.GlobalLayoutListener;
import org.dosbcn.percolator.events.SaveButtonClickListener;
import org.dosbcn.percolator.view.CardViewAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * The main, and only, {@link Activity} in this application.<br/>
 * Our interface is a single {@link ListView} and is managed by simply extending
 * the {@link ListActivity}.
 * 
 * @author Sean Connolly
 */
public class CardActivity extends FragmentActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private final CardService service;

	public CardActivity() {
		service = new CardServiceImpl(this);
	}

	protected CardActivity(CardService service) {
		this.service = service;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initApplication();
		// Make sure an alarm is queued for all active cards, the queue is smart
		// enough to keep appropriate timing and avoid duplicates etc.
		service.resetAllAlarms();
		// TODO do this in another thread so the application starts quickly
		// http://developer.android.com/guide/components/processes-and-threads.html
	}

	private void initApplication() {
		inflateHeader();
		CardViewAdapter adapter = initAdapter();
		initServiceListeners(adapter);
		initSaveButtonListener();
		initLoadManager();
	}

	private void inflateHeader() {
		LayoutInflater inflator = LayoutInflater.from(getApplicationContext());
		View headerView = inflator.inflate(R.layout.header, null);
		// Add a listener to resize the header to take up all the content
		ViewTreeObserver observer = getListView().getViewTreeObserver();
		if (observer.isAlive()) {
			OnGlobalLayoutListener listener = new GlobalLayoutListener(
					getApplicationContext(), getListView());
			observer.addOnGlobalLayoutListener(listener);
		}
		getListView().addHeaderView(headerView);
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

	private void initSaveButtonListener() {
		findSaveButton().setOnClickListener(new SaveButtonClickListener(this));
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

	public EditText findTitleField() {
		return (EditText) findViewById(R.id.title_input);
	}

	public EditText findDescriptionField() {
		return (EditText) findViewById(R.id.description_input);
	}

	public Button findSaveButton() {
		return (Button) findViewById(R.id.save_button);
	}

}
