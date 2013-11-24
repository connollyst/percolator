package org.dosbcn.percolator.data;

import java.util.*;

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
        System.out.println("Getting all cards");
		List<Card> all = new ArrayList<Card>(cards.values());
		Collections.sort(all);
		return all;
	}

	@Override
	public Card fetchById(int id) {
		return cards.get(id);
	}

}
