package com.dosbcn.percolator.data;

import org.joda.time.LocalDate;

import java.util.List;

public interface CardRepository {

	void create(Card card);

	void update(Card card);

	List<Card> fetchAll();

	Card fetchById(int id);

    long count();

    long count(LocalDate day);

}
