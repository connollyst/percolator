package com.dosbcn.percolator.data;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.dosbcn.percolator.MainActivity;
import com.dosbcn.percolator.RobolectricHelper;

/**
 * Test cases for the {@link CardRepository}
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestCardRepository {

	@Test
	public void testCountWhenEmpty() {
		CardRepository repository = getRepository();
		assertEquals(0, repository.count());
	}

	@Test
	public void testCountWithOne() {
		CardRepository repository = getRepository();
		repository.create(new Card("Title", "Desc", CardColor.WHITE));
		assertEquals(1, repository.count());
	}

	@Test
	public void testCountWithMany() {
		CardRepository repository = getRepository();
		repository.create(new Card("Title1", "Desc1", CardColor.WHITE));
		repository.create(new Card("Title2", "Desc2", CardColor.WHITE));
		repository.create(new Card("Title3", "Desc3", CardColor.WHITE));
		assertEquals(3, repository.count());
	}

	@Test
	public void testCountByDayWhenEmpty() {
		CardRepository repository = getRepository();
		LocalDate day = new LocalDate(1985, 8, 6);
		assertEquals(0, repository.count(day));
	}

	@Test
	public void testCountByDayWithOne() {
		CardRepository repository = getRepository();
		LocalDate day = new LocalDate(1985, 8, 6);
		Card card = new Card("Title", "Desc", CardColor.WHITE);
		card.setNextNotificationDate(day);
		repository.create(card);
		assertEquals(1, repository.count(day));
	}

	/**
	 * Assert we can correctly count the number of notifications scheduled for a
	 * particular day when there are many scheduled for that day.
	 */
	@Test
	public void testCountByDayWithMany() {
		CardRepository repository = getRepository();
		LocalDate day = new LocalDate(1985, 8, 6);
		Card card1 = new Card("Title1", "Desc1", CardColor.WHITE);
		Card card2 = new Card("Title2", "Desc2", CardColor.WHITE);
		Card card3 = new Card("Title3", "Desc3", CardColor.WHITE);
		card1.setNextNotificationDate(day);
		card2.setNextNotificationDate(day);
		card3.setNextNotificationDate(day);
		repository.create(card1);
		repository.create(card2);
		repository.create(card3);
		assertEquals(3, repository.count(day));
	}

	/**
	 * Assert we can correctly count the number of notifications scheduled for a
	 * particular day when there are many scheduled for that day, and there are
	 * also many scheduled for other days.
	 */
	@Test
	public void testCountByDayWithManyAndManyOthers() {
		CardRepository repository = getRepository();
		LocalDate day = new LocalDate(1985, 8, 6);
		Card card1 = new Card("Title1", "Desc1", CardColor.WHITE);
		Card card2 = new Card("Title2", "Desc2", CardColor.WHITE);
		Card card3 = new Card("Title3", "Desc3", CardColor.WHITE);
		card1.setNextNotificationDate(day);
		card2.setNextNotificationDate(day);
		card3.setNextNotificationDate(day);
		repository.create(card1);
		repository.create(card2);
		repository.create(card3);
		Card card4 = new Card("Title4", "Desc4", CardColor.WHITE);
		Card card5 = new Card("Title5", "Desc5", CardColor.WHITE);
		Card card6 = new Card("Title6", "Desc6", CardColor.WHITE);
		Card card7 = new Card("Title7", "Desc7", CardColor.WHITE);
		Card card8 = new Card("Title8", "Desc8", CardColor.WHITE);
		Card card9 = new Card("Title9", "Desc9", CardColor.WHITE);
		card4.setNextNotificationDate(day.minusDays(3));
		card5.setNextNotificationDate(day.minusDays(2));
		card6.setNextNotificationDate(day.minusDays(1));
		card7.setNextNotificationDate(day.plusDays(1));
		card8.setNextNotificationDate(day.plusDays(2));
		card9.setNextNotificationDate(day.plusDays(3));
		assertEquals(3, repository.count(day));
	}

	/**
	 * Assert the custom ORMLite processor,
	 * {@link com.dosbcn.percolator.data.ormlite.JodaLocalDateType}, marshalls
	 * and unmarshalls {@link LocalDate} objects correctly.
	 */
	@Test
	public void testNextNotificationDate() {
		CardRepository repository = getRepository();
		LocalDate day = new LocalDate(1985, 8, 6);
		Card card = new Card("Title", "Desc", CardColor.WHITE);
		card.setNextNotificationDate(day);
		repository.create(card);
		card = repository.fetchById(card.getId());
		assertEquals(card.getNextNotificationDate(), day);
	}

	/**
	 * Assert the custom ORMLite processor,
	 * {@link com.dosbcn.percolator.data.ormlite.JodaLocalTimeType}, marshalls
	 * and unmarshalls {@link LocalTime} objects correctly.
	 */
	@Test
	public void testNextNotificationTime() {
		CardRepository repository = getRepository();
		LocalTime time = new LocalTime(12, 29, 59, 137);
		Card card = new Card("Title", "Desc", CardColor.WHITE);
		card.setNextNotificationTime(time);
		repository.create(card);
		card = repository.fetchById(card.getId());
		assertEquals(card.getNextNotificationTime(), time);
	}

	/**
	 * Assert that the utility function,
	 * {@link com.dosbcn.percolator.data.Card#getNextNotificationDateTime()},
	 * persists and retrieves dates and times correctly.
	 */
	@Test
	public void testNextNotificationDateTime() {
		CardRepository repository = getRepository();
		LocalDate day = new LocalDate(1985, 8, 6);
		LocalTime time = new LocalTime(12, 29, 59, 137);
		Card card = new Card("Title", "Desc", CardColor.WHITE);
		card.setNextNotificationDate(day);
		card.setNextNotificationTime(time);
		repository.create(card);
		card = repository.fetchById(card.getId());
		assertEquals(card.getNextNotificationDate(), day);
		assertEquals(card.getNextNotificationTime(), time);
		assertEquals(card.getNextNotificationDateTime(), day.toDateTime(time));
	}

	/**
	 * Get a {@link CardRepository} to work with. We go through the
	 * {@link com.dosbcn.percolator.MainActivity} to let Robolectric create a
	 * new SQLite database for us.
	 *
	 * @return a card repository
	 */
	private CardRepository getRepository() {
		MainActivity activity = RobolectricHelper.createMainActivity();
		CardService service = activity.getService();
		return service.getCardRepository();
	}

}
