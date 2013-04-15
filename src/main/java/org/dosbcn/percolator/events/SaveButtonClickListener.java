package org.dosbcn.percolator.events;

import android.view.View;
import android.widget.EditText;
import org.dosbcn.percolator.MainActivity;
import org.dosbcn.percolator.data.Card;
import org.dosbcn.percolator.data.CardColor;
import org.dosbcn.percolator.data.CardService;

/**
 * A listener for click events on the 'Save' button.<br/>
 * Creates a new card and resets the form.
 *
 * @author Sean Connolly
 */
public class SaveButtonClickListener implements View.OnClickListener {

    private static final String BLANK = "";
    private final CardService service;
    private final EditText titleField;
    private final EditText descriptionField;

    /**
     * Construct a new listener.
     *
     * @param activity
     *         the activity being listened to
     */
    public SaveButtonClickListener(MainActivity activity) {
        this.service = activity.getService();
        this.titleField = activity.findTitleField();
        this.descriptionField = activity.findDescriptionField();
    }

    /**
     * Handle a click event.
     *
     * @param view
     *         the view originating the click event
     */
    @Override
    public void onClick(View view) {
        onClick();
    }

    /**
     * Handle a click event.
     */
    public void onClick() {
        String title = getTitle();
        String description = getDescription();
        Card card = new Card(title, description, CardColor.WHITE);
        service.save(card);
        clearForm();
        resetFocus();
    }

    /**
     * Get the current title from the form.
     *
     * @return the title
     */
    private String getTitle() {
        return titleField.getText().toString();
    }

    /**
     * Get the current description from the form.
     *
     * @return the description
     */
    private String getDescription() {
        return descriptionField.getText().toString();
    }

    /**
     * Clear the form, setting both input fields to blank.
     */
    private void clearForm() {
        titleField.setText(BLANK);
        descriptionField.setText(BLANK);
    }

    /**
     * Reset the focus in the form.
     */
    private void resetFocus() {
        titleField.requestFocus();
    }

}
