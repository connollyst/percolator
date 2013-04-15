package org.dosbcn.percolator;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import org.dosbcn.percolator.data.CardService;
import org.dosbcn.percolator.data.CardServiceImpl;
import org.dosbcn.percolator.events.GotoListButtonClickListener;
import org.dosbcn.percolator.events.SaveButtonClickListener;

/**
 * The main {@link Activity} in this application.<br/>
 * Our interface is a single {@link ListView} and is managed by simply extending
 * the {@link ListActivity}.
 *
 * @author Sean Connolly
 */
public class CardActivity extends Activity {

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
        setContentView(R.layout.home);
        initSaveButtonListener();
        initGotoListButtonListener();
        // Make sure an alarm is queued for all active cards, the queue is smart
        // enough to keep appropriate timing and avoid duplicates etc.
        service.resetAllAlarms();
        // TODO do this when the application starts.. now the activity
        // TODO do this in another thread so the application starts quickly
        // http://developer.android.com/guide/components/processes-and-threads.html
    }

    private void initSaveButtonListener() {
        findSaveButton().setOnClickListener(new SaveButtonClickListener(this));
    }

    private void initGotoListButtonListener() {
        findGotoListButton().setOnClickListener(new GotoListButtonClickListener());
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

    public Button findGotoListButton() {
        return (Button) findViewById(R.id.list_button);
    }

}
