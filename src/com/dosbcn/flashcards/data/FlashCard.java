package com.dosbcn.flashcards.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accounts")
public class FlashCard {

	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField
	private String title;
	@DatabaseField
	private String description;
	@DatabaseField
	private FlashCardColor color;

	public FlashCard() {

	}

	public FlashCard(String title, String description, FlashCardColor color) {
		super();
		this.title = title;
		this.description = description;
		this.color = color;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FlashCardColor getColor() {
		return color;
	}

	public void setColor(FlashCardColor color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return new StringBuilder(FlashCard.class.getSimpleName())
				.append("(id=").append(id).append(", title='").append(title)
				.append("', description='").append(description).append("')")
				.toString();
	}

}
