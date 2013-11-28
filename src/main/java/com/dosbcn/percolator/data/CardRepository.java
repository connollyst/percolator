package com.dosbcn.percolator.data;

import java.util.List;

public interface CardRepository {

	public void create(Card card);

	public void update(Card card);

	public List<Card> fetchAll();

	public Card fetchById(int id);

}
