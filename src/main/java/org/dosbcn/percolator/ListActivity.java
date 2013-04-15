package org.dosbcn.percolator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.data.CardServiceImpl;
import org.dosbcn.percolator.events.CardAddListener;
import org.dosbcn.percolator.view.CardViewAdapter;

import java.util.List;

/**
 * The {@link Activity} displaying the list of flash cards.<br/>
 * Our interface is a single {@link ListView} and is managed by simply extending
 * the {@link android.app.ListActivity}.
 *
 * @author Sean Connolly
 */
public class ListActivity extends android.app.ListActivity {

    private static final String LOG_TAG = ListActivity.class.getSimpleName();
    private final CardService service;

    public ListActivity() {
        service = new CardServiceImpl(this);
    }

    protected ListActivity(CardService service) {
        this.service = service;
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

}