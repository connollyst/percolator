package com.dosbcn.percolator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.dosbcn.percolator.R;
import com.dosbcn.percolator.data.CardService;
import com.dosbcn.percolator.data.CardServiceImpl;
import com.dosbcn.percolator.events.SaveButtonClickListener;

/**
 * The main {@link Activity} in this application.<br/>
 * Our interface is a single {@link ListView} and is managed by simply extending
 * the {@link android.app.ListActivity}.
 *
 * @author Sean Connolly
 */
public class MainActivity extends Activity {

	private static final String LOG_TAG = MainActivity.class.getName();

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
		Log.d(LOG_TAG, "Creating..");
		setContentView(R.layout.main);
		initTitleChangeListener();
		initSaveButtonListener();
		// Request focus and show soft keyboard automatically
		findTitleField().requestFocus();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(LOG_TAG, "Pausing..");
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(LOG_TAG, "Resuming..");
		loadState();
	}

	private void saveState() {
		String title = findTitleField().getText().toString();
		String description = findDescriptionField().getText().toString();
		Log.d(LOG_TAG, "Saving state: '" + title + "' & '" + description + "'");
		SharedPreferences example = getState();
		SharedPreferences.Editor edit = example.edit();
		edit.putString("cardTitle", title);
		edit.putString("cardDescription", description);
		edit.commit();
	}

	private void loadState() {
		SharedPreferences example = getState();
		String title = example.getString("cardTitle", "");
		String description = example.getString("cardDescription", "");
		Log.d(LOG_TAG, "Loading state: '" + title + "' & '" + description + "'");
		findTitleField().setText(title);
		findDescriptionField().setText(description);
	}

	private SharedPreferences getState() {
		String name = getClass().getName();
		return getSharedPreferences(name, MODE_PRIVATE);
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
