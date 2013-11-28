package com.dosbcn.percolator.notifications;

import static com.dosbcn.percolator.AssertTime.assertEvenDistribution;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import com.dosbcn.percolator.notifications.time.MockTimeUtilities;
import com.dosbcn.percolator.notifications.time.RandomTimeGenerator;
import com.dosbcn.percolator.notifications.time.TimeUtilities;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * Test cases for the {@link RandomTimeGenerator}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestRandomTimeGenerator {

	// Sample a large number of random times to evaluate the generator's
	// behavior
	private static final int TEST_ITERATION_COUNT = 10000;
	private static final int EXPECTED_MIN_HOUR = 11;
	private static final int EXPECTED_MAX_HOUR = 21;
	private static final int MORNING_HOUR = 8;
	private static final int AFTERNOON_HOUR = 13;
	private static final int EVENING_HOUR = 18;
	private static final int NIGHT_HOUR = 20;

	/**
	 * Assert that random times generated today, for tomorrow, are returned with
	 * an even distribution and don't stray outside the cutoff times.
	 */
	@Test
	public void testRandomTimeInDay() throws InterruptedException,
			ExecutionException {
		DateTime now = mockTodayAtHour(AFTERNOON_HOUR);
		TimeUtilities timeUtils = new MockTimeUtilities(now);
		final LocalDate tomorrow = mockTomorrowAtHour(AFTERNOON_HOUR)
				.toLocalDate();
		final RandomTimeGenerator timeGenerator = new RandomTimeGenerator(
				timeUtils);
		RandomTimeTestStatistics stats = generateStatistics(new Callable<DateTime>() {
			@Override
			public DateTime call() throws Exception {
				return timeGenerator.getRandomTimeInDay(tomorrow);
			}
		});
		DateTime lowerLimit = mockTomorrowAtHour(EXPECTED_MIN_HOUR);
		DateTime upperLimit = mockTomorrowAtHour(EXPECTED_MAX_HOUR);
		assertEvenDistribution(stats, lowerLimit, upperLimit);
	}

	/**
	 * Assert that random times generated <i>in the morning</i> today, for
	 * today, are returned with a distribution like any other day.
	 */
	@Test
	public void testRandomTimeTodayFromMorningHour()
			throws InterruptedException, ExecutionException {
		DateTime now = mockTodayAtHour(MORNING_HOUR);
		TimeUtilities timeUtils = new MockTimeUtilities(now);
		final LocalDate today = now.toLocalDate();
		final RandomTimeGenerator timeGenerator = new RandomTimeGenerator(
				timeUtils);
		RandomTimeTestStatistics stats = generateStatistics(new Callable<DateTime>() {
			@Override
			public DateTime call() throws Exception {
				return timeGenerator.getRandomTimeInDay(today);
			}
		});
		DateTime lowerLimit = mockTodayAtHour(EXPECTED_MIN_HOUR);
		DateTime upperLimit = mockTodayAtHour(EXPECTED_MAX_HOUR);
		assertEvenDistribution(stats, lowerLimit, upperLimit);
	}

	/**
	 * Assert that random times generated <i>in the afternoon</i> today, for
	 * today, are returned with a distribution limited to the remaining hours in
	 * the day.
	 */
	@Test
	public void testRandomTimeTodayFromAfternoonHour()
			throws InterruptedException, ExecutionException {
		DateTime now = mockTodayAtHour(AFTERNOON_HOUR);
		TimeUtilities timeUtils = new MockTimeUtilities(now);
		final LocalDate today = now.toLocalDate();
		final RandomTimeGenerator timeGenerator = new RandomTimeGenerator(
				timeUtils);
		RandomTimeTestStatistics stats = generateStatistics(new Callable<DateTime>() {
			@Override
			public DateTime call() throws Exception {
				return timeGenerator.getRandomTimeInDay(today);
			}
		});
		DateTime lowerLimit = mockTodayAtHour(AFTERNOON_HOUR);
		DateTime upperLimit = mockTodayAtHour(EXPECTED_MAX_HOUR);
		assertEvenDistribution(stats, lowerLimit, upperLimit);
	}

	/**
	 * Assert that random times generated <i>in the evening</i> today, for
	 * today, are returned with a distribution limited to the remaining hours in
	 * the day.
	 */
	@Test
	public void testRandomTimeTodayFromEveningHour()
			throws InterruptedException, ExecutionException {
		DateTime now = mockTodayAtHour(EVENING_HOUR);
		TimeUtilities timeUtils = new MockTimeUtilities(now);
		final LocalDate today = now.toLocalDate();
		final RandomTimeGenerator timeGenerator = new RandomTimeGenerator(
				timeUtils);
		RandomTimeTestStatistics stats = generateStatistics(new Callable<DateTime>() {
			@Override
			public DateTime call() throws Exception {
				return timeGenerator.getRandomTimeInDay(today);
			}
		});
		DateTime lowerLimit = mockTodayAtHour(EVENING_HOUR);
		DateTime upperLimit = mockTodayAtHour(EXPECTED_MAX_HOUR);
		assertEvenDistribution(stats, lowerLimit, upperLimit);
	}

	/**
	 * Assert that random times generated <i>at night</i> today, for today, are
	 * returned with a distribution limited to the remaining hours in the day.
	 */
	@Test
	public void testRandomTimeTodayFromNightHour() throws InterruptedException,
			ExecutionException {
		DateTime now = mockTodayAtHour(NIGHT_HOUR);
		TimeUtilities timeUtils = new MockTimeUtilities(now);
		final LocalDate today = now.toLocalDate();
		final RandomTimeGenerator timeGenerator = new RandomTimeGenerator(
				timeUtils);
		RandomTimeTestStatistics stats = generateStatistics(new Callable<DateTime>() {
			@Override
			public DateTime call() throws Exception {
				return timeGenerator.getRandomTimeInDay(today);
			}
		});
		DateTime lowerLimit = mockTodayAtHour(NIGHT_HOUR);
		DateTime upperLimit = mockTodayAtHour(EXPECTED_MAX_HOUR);
		assertEvenDistribution(stats, lowerLimit, upperLimit);
	}

	/**
	 * Return statistics about the {@link DateTime}s returned from a large
	 * number of calls to the provided {@link Callable}.
	 *
	 * @param generatorCall
	 *            the function to call to generate a random time
	 */
	private RandomTimeTestStatistics generateStatistics(
			Callable<DateTime> generatorCall) throws InterruptedException,
			ExecutionException {
		RandomTimeTestStatistics stats = new RandomTimeTestStatistics(
				TEST_ITERATION_COUNT);
		for (int i = 0; i < TEST_ITERATION_COUNT; i++) {
			RunnableFuture<DateTime> generatorRun = new FutureTask<DateTime>(
					generatorCall);
			generatorRun.run();
			stats.addRandomTime(generatorRun.get());
		}
		return stats;
	}

	/**
	 * Mock up a {@link DateTime} to represent today at a specific hour.
	 *
	 * @param hour
	 *            the hour
	 * @return the specified hour, tomorrow.
	 */
	private DateTime mockTodayAtHour(int hour) {
		return new DateTime(1985, 8, 6, hour, 0, 0, 0);
	}

	/**
	 * Mock up a {@link DateTime} to represent tomorrow at a specific hour.
	 *
	 * @param hour
	 *            the hour
	 * @return the specified hour, tomorrow.
	 */
	private DateTime mockTomorrowAtHour(int hour) {
		return mockTodayAtHour(hour).plus(Period.days(1));
	}

}
