package org.dosbcn.flashcards.events;

import android.view.View;
import android.widget.EditText;

import org.dosbcn.flashcards.CardActivity;
import org.dosbcn.flashcards.data.Card;
import org.dosbcn.flashcards.data.CardColor;

public class SaveButtonClickListener implements View.OnClickListener {

	private final CardActivity activity;
	private final EditText titleField;
	private final EditText descriptionField;

	public SaveButtonClickListener(CardActivity activity) {
		this.activity = activity;
		this.titleField = activity.findTitleField();
		this.descriptionField = activity.findDescriptionField();
	}

	@Override
	public void onClick(View view) {
		String title = titleField.getText().toString();
		String description = descriptionField.getText().toString();
		Card card = new Card(title, description, CardColor.WHITE);
		activity.getService().save(card);
		clearForm();
	}

	private void clearForm() {
		titleField.setText("");
		descriptionField.setText("");
	}
}
