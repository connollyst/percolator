package com.dosbcn.flashcards;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dosbcn.flashcards.CardViewAdapter.ViewHolder;
import com.dosbcn.flashcards.data.Card;
import com.dosbcn.flashcards.data.CardRepository;

/**
 * The main, and only, {@link Activity} in this application.<br/>
 * Our interface is a single {@link ListView} and is managed by simply extending
 * the {@link ListActivity}.
 * 
 * @author Sean Connolly
 */
public class MainActivity extends ListActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();

	private final CardRepository cardRepository;
	private final CardNotifier cardNotifier;
	private final CardToaster cardToaster;

	public MainActivity() {
		cardRepository = new CardRepository(this);
		cardNotifier = new CardNotifier(this);
		cardToaster = new CardToaster(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflateHeader();

		// Define a new Adapter & assign it to the ListView
		List<Card> cards = cardRepository.fetchAll();
		CardViewAdapter adapter = new CardViewAdapter(this, cards);
		setListAdapter(adapter);

		//
		EditText titleField = getNewCardTitleField();
		EditText descriptionField = getNewCardDescriptionField();
		Button saveButton = getNewCardSaveButton();
		saveButton.setOnClickListener(new SaveButtonListener(adapter,
				cardRepository, cardToaster, titleField, descriptionField));

		// Prepare the loader.
		// Either re-connect with an existing one, or start a new one.
		getLoaderManager().initLoader(0, null, this);
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
		ViewHolder holder = (ViewHolder) view.getTag();
		Log.i(LOG_TAG, "Item clicked: " + holder);
		toggleVisibility(holder);
		// Send notification for this card
		Card card = (Card) listView.getItemAtPosition(position);
		cardNotifier.sendNotification(card);
	}

	private void toggleVisibility(ViewHolder holder) {
		View description = holder.description;
		int oldVisibility = description.getVisibility();
		int newVisibility;
		switch (oldVisibility) {
		case View.VISIBLE:
			newVisibility = View.GONE;
			break;
		case View.GONE:
			newVisibility = View.VISIBLE;
			break;
		default:
			Log.e(LOG_TAG, "Cannot toggle visibility; unrecognized state: "
					+ oldVisibility);
			newVisibility = View.GONE;
			break;
		}
		description.setVisibility(newVisibility);
	}

	private void inflateHeader() {
		LayoutInflater inflator = LayoutInflater.from(getApplicationContext());
		View headerView = inflator.inflate(R.layout.header, null);
		getListView().addHeaderView(headerView);
	}

	private EditText getNewCardTitleField() {
		return (EditText) findViewById(R.id.title_input);
	}

	private EditText getNewCardDescriptionField() {
		return (EditText) findViewById(R.id.description_input);
	}

	private Button getNewCardSaveButton() {
		return (Button) findViewById(R.id.save_button);
	}

}
