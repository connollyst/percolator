package com.dosbcn.flashcards;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dosbcn.flashcards.data.Card;
import com.dosbcn.flashcards.data.CardService;
import com.dosbcn.flashcards.events.CardAddListener;
import com.dosbcn.flashcards.events.GlobalLayoutListener;
import com.dosbcn.flashcards.events.SaveButtonClickListener;

/**
 * The main, and only, {@link Activity} in this application.<br/>
 * Our interface is a single {@link ListView} and is managed by simply extending
 * the {@link ListActivity}.
 * 
 * @author Sean Connolly
 */
public class CardActivity extends ListActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private final CardService service;

	public CardActivity() {
		service = new CardService(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		inflateHeader();
		CardViewAdapter adapter = initAdapter();
		initServiceListeners(adapter);
		initSaveButtonListener();
		getLoaderManager().initLoader(0, null, this);
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
		setListAdapter(adapter);
		return adapter;
	}

	private void initSaveButtonListener() {
		findSaveButton().setOnClickListener(new SaveButtonClickListener(this));
	}

	private void initServiceListeners(final CardViewAdapter adapter) {
		service.setOnAddListener(new CardAddListener(adapter));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
	}

	public CardService getService() {
		return service;
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
