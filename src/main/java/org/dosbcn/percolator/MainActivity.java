package org.dosbcn.percolator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.data.CardServiceImpl;
import org.dosbcn.percolator.events.SaveButtonClickListener;

/**
 * The main {@link Activity} in this application.<br/>
 * Our interface is a single {@link ListView} and is managed by simply extending
 * the {@link android.app.ListActivity}.
 *
 * @author Sean Connolly
 */
public class MainActivity extends Activity {

	private final CardService service;

	public MainActivity() {
		service = new CardServiceImpl(this);
	}

	protected MainActivity(CardService service) {
		this.service = service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initTitleChangeListener();
		initSaveButtonListener();
		// Request focus and show soft keyboard automatically
		findTitleField().requestFocus();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		// Make sure an alarm is queued for all active cards, the queue is smart
		// enough to keep appropriate timing and avoid duplicates etc.
		service.resetAllAlarms();
		// TODO do this when the application starts.. not the activity
		// TODO do this in another thread so the application starts quickly
		// http://developer.android.com/guide/components/processes-and-threads.html
	}

	private void initTitleChangeListener() {
		findTitleField().addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				boolean hasTitle = s.length() > 0;
				findSaveButton().setEnabled(hasTitle);
			}
		});
	}

	private void initSaveButtonListener() {
		findSaveButton().setOnClickListener(new SaveButtonClickListener(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_list:
			openList();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void openList() {
		Intent nextActivity = new Intent(getApplicationContext(),
				ListActivity.class);
		startActivity(nextActivity);
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
