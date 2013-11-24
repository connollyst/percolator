package org.dosbcn.percolator.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

/**
 * A card is our single model object. It simply represents a pair or words; here
 * referred to, generically, as a {@code title} and {@code description}. What
 * the words are exactly, depends on what the user wants to be reminded of. For
 * example, the {@code title} may be a word in another language and the
 * {@code description} it's translation in English. Or, perhaps the
 * {@code title} is a big word in English and the {@code description} is it's
 * definition.
 *
 * @author Sean Connolly
 */
@DatabaseTable(tableName = "cards")
public class Card implements Comparable<Card> {

	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField
	private String title;
	@DatabaseField
	private String description;
	@DatabaseField
	private CardColor color;
	@DatabaseField
	private CardStage stage;
	@DatabaseField
	private DateTime startDate;
	@DatabaseField
	private DateTime nextNotificationDate;

	@SuppressWarnings("unused")
	private Card() {
		// A no-arg constructor provide for the DB layer,
		// but private so it can't be accessed.
	}

	public Card(String title, String description, CardColor color) {
		this.title = title;
		this.description = description;
		this.color = color;
		this.stage = CardStage.ONE_DAY;
		this.startDate = new DateTime();
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

	public CardStage getStage() {
		return stage;
	}

	public void setStage(CardStage stage) {
		this.stage = stage;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public DateTime getNextNotificationDate() {
		return nextNotificationDate;
	}

	public void setNextNotificationDate(DateTime nextNotificationDate) {
		this.nextNotificationDate = nextNotificationDate;
	}

	@Override
	public String toString() {
		return new StringBuilder(Card.class.getSimpleName()).append("(id=")
				.append(id).append(", title='").append(title)
				.append("', description='").append(description).append("')")
				.toString();
	}

	@Override
	public int compareTo(Card that) {
		// TODO include other values in case the start date is the same?
		return -this.getStartDate().compareTo(that.getStartDate());
	}

}
