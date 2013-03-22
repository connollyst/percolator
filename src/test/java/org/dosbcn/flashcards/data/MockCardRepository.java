package org.dosbcn.flashcards.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockCardRepository implements CardRepository {

	private int nextId = 1;
	private Map<Integer, Card> cards = new HashMap<Integer, Card>();

	@Override
	public void create(Card card) {
		card.setId(nextId++);
		update(card);
	}

	@Override
	public void update(Card card) {
		cards.put(card.getId(), card);
	}

	@Override
	public List<Card> fetchAll() {
		return new ArrayList<Card>(cards.values());
	}

	@Override
	public Card fetchById(int id) {
		return cards.get(id);
	}

}
