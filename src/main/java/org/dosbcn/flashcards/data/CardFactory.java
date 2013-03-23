package org.dosbcn.flashcards.data;

public class CardFactory {

	public static Card createCard(String title, String description,
			CardColor color) {
		return new Card(title, description, CardColor.WHITE);
	}

}
