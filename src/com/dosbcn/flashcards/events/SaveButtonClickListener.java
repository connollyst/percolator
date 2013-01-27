package com.dosbcn.flashcards.events;

import android.view.View;
import android.widget.EditText;

import com.dosbcn.flashcards.MainActivity;
import com.dosbcn.flashcards.data.Card;
import com.dosbcn.flashcards.data.CardColor;

public class SaveButtonClickListener implements View.OnClickListener {

	private final MainActivity activity;
	private final EditText titleField;
	private final EditText descriptionField;

	public SaveButtonClickListener(MainActivity activity) {
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
	}

}
