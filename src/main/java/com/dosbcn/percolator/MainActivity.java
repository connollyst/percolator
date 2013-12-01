package com.dosbcn.percolator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.dosbcn.percolator.data.CardService;
import com.dosbcn.percolator.data.CardServiceImpl;
import com.dosbcn.percolator.events.SaveButtonClickListener;
import com.dosbcn.percolator.events.TitleInputListener;

/**
 * The main {@link Activity} in this application, the first activity called when
 * the application starts.
 *
 * @author Sean Connolly
 */
public class MainActivity extends FragmentActivity implements
		WelcomeDialogFragment.WelcomeDialogListener {

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
		setContentView(R.layout.main);
		initListeners();
		if (isFirstCreate()) {
			showWelcomeDialog();
		} else {
			// Request focus and show soft keyboard automatically
			findTitleField().requestFocus();
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		}
	}

	/**
	 * Initialize all listeners for this activity.
	 */
	private void initListeners() {
		findTitleField().addTextChangedListener(new TitleInputListener(this));
		findSaveButton().setOnClickListener(new SaveButtonClickListener(this));
	}

	private boolean isFirstCreate() {
		return !wasUserWelcomed();
	}

	private void showWelcomeDialog() {
		new WelcomeDialogFragment()
				.show(getSupportFragmentManager(), "welcome");
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

	/**
	 * Called when the application pauses. The current state is saved so we can
	 * restore it later.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	/**
	 * Called when the application resumes. Any previously stored state is
	 * restored.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		loadState();
	}

	/**
	 * Save the current state so it can be restored later.
	 */
	private void saveState() {
		String title = findTitleField().getText().toString();
		String description = findDescriptionField().getText().toString();
		Log.d(LOG_TAG, "Saving state: '" + title + "' & '" + description + "'");
		SharedPreferences storage = getStateStorage();
		SharedPreferences.Editor storageEditor = storage.edit();
		storageEditor.putString("cardTitle", title);
		storageEditor.putString("cardDescription", description);
		storageEditor.commit();
	}

	/**
	 * Restore previously saved state, if any.
	 */
	private void loadState() {
		SharedPreferences example = getStateStorage();
		String title = example.getString("cardTitle", "");
		String description = example.getString("cardDescription", "");
		Log.d(LOG_TAG, "Loading state: '" + title + "' & '" + description + "'");
		findTitleField().setText(title);
		findDescriptionField().setText(description);
	}

	/**
	 * Get the {@link SharedPreferences} in which we can store/retrieve
	 * application state.
	 *
	 * @return the state storage location
	 */
	private SharedPreferences getStateStorage() {
		return getSharedPreferences(getClass().getName(), MODE_PRIVATE);
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

	/**
	 * Listener for when the 'Welcome' dialog is dismissed.<br/>
	 * As this is the user's first time here, let's start them off with an
	 * example use.
	 */
	@Override
	public void onWelcomeDismissed(DialogFragment dialog) {
		showExample();
		recordUserWelcomed();
	}

	private void showExample() {
		findTitleField().setText(R.string.example_title);
		findDescriptionField().setText(R.string.example_description);
		findTitleField().clearFocus();
	}

	private void recordUserWelcomed() {
		SharedPreferences storage = getStateStorage();
		SharedPreferences.Editor storageEditor = storage.edit();
		storageEditor.putBoolean("wasWelcomed", true);
		storageEditor.commit();
	}

	private boolean wasUserWelcomed() {
		SharedPreferences storage = getStateStorage();
		return storage.getBoolean("wasWelcomed", false);
	}

}
