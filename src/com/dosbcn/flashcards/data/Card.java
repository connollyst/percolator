package com.dosbcn.flashcards.data;

import java.util.Calendar;
import java.util.Date;

import com.dosbcn.flashcards.notifications.CardNotificationStage;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cards")
public class Card {

	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField
	private String title;
	@DatabaseField
	private String description;
	@DatabaseField
	private CardColor color;
	@DatabaseField
	private CardNotificationStage stage;
	@DatabaseField
	private Date startDate;

	public Card() {
	}

	public Card(String title, String description, CardColor color) {
		this.title = title;
		this.description = description;
		this.color = color;
		this.stage = CardNotificationStage.ONE_DAY;
		this.startDate = Calendar.getInstance().getTime();
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

	public CardColor getColor() {
		return color;
	}

	public void setColor(CardColor color) {
		this.color = color;
	}

	public CardNotificationStage getStage() {
		return stage;
	}

	public void setStage(CardNotificationStage stage) {
		this.stage = stage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return new StringBuilder(Card.class.getSimpleName()).append("(id=")
				.append(id).append(", title='").append(title)
				.append("', description='").append(description).append("')")
				.toString();
	}

}
