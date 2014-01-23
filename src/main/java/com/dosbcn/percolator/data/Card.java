package com.dosbcn.percolator.data;

import com.dosbcn.percolator.data.ormlite.JodaDateTimeType;
import com.dosbcn.percolator.data.ormlite.JodaLocalDateType;
import com.dosbcn.percolator.data.ormlite.JodaLocalTimeType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

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

	static final String COLUMN_NAME_START_DATE = "startdate";
	static final String COLUMN_NAME_NOTIFICATION_DATE = "nextnotificationdate";
	static final String COLUMN_NAME_NOTIFICATION_TIME = "nextnotificationtime";

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
	@DatabaseField(persisterClass = JodaDateTimeType.class, columnName = COLUMN_NAME_START_DATE)
	private DateTime startDateTime;
	@DatabaseField(persisterClass = JodaLocalDateType.class, columnName = COLUMN_NAME_NOTIFICATION_DATE)
	private LocalDate nextNotificationDate;
	@DatabaseField(persisterClass = JodaLocalTimeType.class, columnName = COLUMN_NAME_NOTIFICATION_TIME)
	private LocalTime nextNotificationTime;

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
		this.startDateTime = new DateTime();
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

	/**
	 * Get the date and time this card was started (create or reset).
	 *
	 * @return the start date &amp; time
	 */
	public DateTime getStartDateTime() {
		return startDateTime;
	}

	/**
	 * Set the date and time this card was started (create or reset).
	 *
	 * @param startDateTime
	 *            the start date &amp; time
	 */
	public void setStartDateTime(DateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * Get the next notification date.
	 *
	 * @return the next notification date
	 */
	public LocalDate getNextNotificationDate() {
		return nextNotificationDate;
	}

	/**
	 * Set the next notification time.<br/>
	 * Note: you may also mean to set the next notification date
	 *
	 * @param nextNotificationDate
	 *            the next notification date
	 *
	 * @see #setNextNotificationTime(org.joda.time.LocalTime)
	 * @see #setNextNotificationDateTime(org.joda.time.DateTime)
	 */
	public void setNextNotificationDate(LocalDate nextNotificationDate) {
		this.nextNotificationDate = nextNotificationDate;
	}

	/**
	 * Get the next notification time.
	 *
	 * @return the next notification time
	 */
	public LocalTime getNextNotificationTime() {
		return nextNotificationTime;
	}

	/**
	 * Set the next notification time.<br/>
	 * Note: you may also mean to set the next notification date
	 *
	 * @param nextNotificationTime
	 *            the next notification time
	 *
	 * @see #setNextNotificationDate(org.joda.time.LocalDate)
	 * @see #setNextNotificationDateTime(org.joda.time.DateTime)
	 */
	public void setNextNotificationTime(LocalTime nextNotificationTime) {
		this.nextNotificationTime = nextNotificationTime;
	}

	/**
	 * Get the next notification date and time.
	 *
	 * @return the next notification date &amp; time, or {@code null} if no
	 *         notification is scheduled
	 */
	public DateTime getNextNotificationDateTime() {
		if (nextNotificationDate == null) {
			return null;
		} else {
			return nextNotificationDate.toDateTime(nextNotificationTime);
		}
	}

	/**
	 * Set the next notification date and time.
	 *
	 * @param nextNotificationDateTime
	 *            the next notification date &amp; time
	 */
	public void setNextNotificationDateTime(DateTime nextNotificationDateTime) {
		if (nextNotificationDateTime == null) {
			this.nextNotificationDate = null;
			this.nextNotificationTime = null;
		} else {
			this.nextNotificationDate = nextNotificationDateTime.toLocalDate();
			this.nextNotificationTime = nextNotificationDateTime.toLocalTime();
		}
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
		return -this.getStartDateTime().compareTo(that.getStartDateTime());
	}

}
