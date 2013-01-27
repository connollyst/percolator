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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dosbcn.flashcards.data.Card;
import com.dosbcn.flashcards.data.CardService;
import com.dosbcn.flashcards.events.CardAddListener;
import com.dosbcn.flashcards.events.ListItemClickListener;
import com.dosbcn.flashcards.events.SaveButtonClickListener;

/**
 * The main, and only, {@link Activity} in this application.<br/>
 * Our interface is a single {@link ListView} and is managed by simply extending
 * the {@link ListActivity}.
 * 
 * @author Sean Connolly
 */
public class MainActivity extends ListActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private final ListItemClickListener clickListener;

	private final CardService service;

	public MainActivity() {
		clickListener = new ListItemClickListener();
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

	private CardViewAdapter initAdapter() {
		List<Card> cards = service.getAll();
		CardViewAdapter adapter = new CardViewAdapter(this, cards);
		setListAdapter(adapter);
		return adapter;
	}

	private void initSaveButtonListener() {
		View.OnClickListener listener = new SaveButtonClickListener(this);
		findSaveButton().setOnClickListener(listener);
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

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		clickListener.onClick(listView, view, position, id);
	}

	private void inflateHeader() {
		LayoutInflater inflator = LayoutInflater.from(getApplicationContext());
		View headerView = inflator.inflate(R.layout.header, null);
		getListView().addHeaderView(headerView);
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
