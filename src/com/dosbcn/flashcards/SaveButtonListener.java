package com.dosbcn.flashcards;

import android.view.View;
import android.widget.EditText;

import com.dosbcn.flashcards.data.FlashCard;
import com.dosbcn.flashcards.data.FlashCardColor;
import com.dosbcn.flashcards.data.FlashCardRepository;

public class SaveButtonListener implements View.OnClickListener {

	private final FlashCardViewAdapter adapter;
	private final FlashCardRepository flashCardRepo;
	private final EditText titleField;
	private final EditText descriptionField;

	public SaveButtonListener(FlashCardViewAdapter adapter,
			FlashCardRepository flashCardRepo, EditText titleField,
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
		FlashCard flashCard = new FlashCard(title, description,
				FlashCardColor.WHITE);
		flashCardRepo.create(flashCard);
		// TODO can we just add the new card?
		adapter.clear();
		adapter.addAll(flashCardRepo.fetchAll());
	}

}
