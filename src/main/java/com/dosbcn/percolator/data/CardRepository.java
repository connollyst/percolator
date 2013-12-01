package com.dosbcn.percolator.data;

import java.util.List;

public interface CardRepository {

	void create(Card card);

	void update(Card card);

	List<Card> fetchAll();

	Card fetchById(int id);

}
