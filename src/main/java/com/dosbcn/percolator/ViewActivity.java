package com.dosbcn.percolator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import com.dosbcn.percolator.data.Card;
import com.dosbcn.percolator.data.CardService;
import com.dosbcn.percolator.data.CardServiceImpl;
import com.dosbcn.percolator.events.SaveButtonClickListener;
import com.dosbcn.percolator.notifications.CardIntent;
import com.dosbcn.percolator.notifications.CardNotifier;
import com.dosbcn.percolator.notifications.CardNotifierImpl;

/**
 * The main {@link android.app.Activity} in this application.<br/>
 * Our interface is a single {@link android.widget.ListView} and is managed by
 * simply extending the {@link android.app.ListActivity}.
 *
 * @author Sean Connolly
 */
public class ViewActivity extends Activity {

	private static final String LOG_TAG = ViewActivity.class.getName();

	private final CardService service;
	private final CardNotifier notifier;

	public ViewActivity() {
		service = new CardServiceImpl(this);
		notifier = new CardNotifierImpl(this);
	}

	protected ViewActivity(CardService service, CardNotifier notifier) {
		this.service = service;
		this.notifier = notifier;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		loadCard();
	}

	private void loadCard() {
		int cardId = getIntent().getIntExtra(CardIntent.CARD_ID_EXTRA, -1);
		if (cardId > -1) {
			Card card = service.get(cardId);
			findTitleTextView().setText(card.getTitle());
			findDescriptionTextView().setText(card.getDescription());
            notifier.closeNotification(card);
		} else {
			Log.e(LOG_TAG, "Tried to display view but no card id provided.");
		}
	}

	public CardService getService() {
		return service;
	}

	public TextView findTitleTextView() {
		return (TextView) findViewById(R.id.title_text);
	}

	public TextView findDescriptionTextView() {
		return (TextView) findViewById(R.id.description_text);
	}

}
