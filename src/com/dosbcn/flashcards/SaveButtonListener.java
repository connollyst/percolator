package com.dosbcn.flashcards;

import android.view.View;
import android.widget.EditText;

import com.dosbcn.flashcards.data.Card;
import com.dosbcn.flashcards.data.CardColor;
import com.dosbcn.flashcards.data.CardRepository;

public class SaveButtonListener implements View.OnClickListener {

	private final CardViewAdapter adapter;
	private final CardRepository flashCardRepo;
	private final EditText titleField;
	private final EditText descriptionField;

	public SaveButtonListener(CardViewAdapter adapter,
			CardRepository flashCardRepo, EditText titleField,
			EditText descriptionField) {
		this.adapter = adapter;
		this.flashCardRepo = flashCardRepo;
		this.titleField = titleField;
		this.descriptionField = descriptionField;
	}

	@Override
	public void onClick(View view) {
		String title = titleField.getText().toString();
		String description = descriptionField.getText().toString();
		Card flashCard = new Card(title, description,
				CardColor.WHITE);
		flashCardRepo.create(flashCard);
		// TODO can we just add the new card?
		adapter.clear();
		adapter.addAll(flashCardRepo.fetchAll());
	}

}
