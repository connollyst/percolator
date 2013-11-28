package com.dosbcn.percolator;

import com.dosbcn.percolator.notifications.RandomTimeTestStatistics;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import static junit.framework.Assert.assertTrue;

/**
 * Some utility time assertions.<br/>
 * They can be statically imported into test cases for easy use.
 *
 * @author Sean Connolly
 */
public class AssertTime {

	private static final Period ONE_HOUR = Period.hours(1);

	// TODO assert UNIFORM distribution

	/**
	 * Assert that the {@link RandomTimeTestStatistics} reflect an even
	 * distribution.<br/>
	 * The lower and upper limits should be respected but, given the large
	 * number of random times sampled, we expect the lowest and highest times
	 * seen to be close to those limits. We also assert that the mean of all the
	 * sampled times is roughly half way between the lower and upper limits.
	 *
	 * @param stats
	 *            the calculated time sample statistics
	 * @param lowerLimit
	 *            the expected lower limit
	 * @param upperLimit
	 *            the expected upper limit
	 */
	public static void assertEvenDistribution(RandomTimeTestStatistics stats,
			DateTime lowerLimit, DateTime upperLimit) {
		DateTime actualMinimum = stats.getMinimumTime();
		DateTime actualMaximum = stats.getMaximumTime();
		DateTime actualMean = stats.getMean();

		// We expect the random times to be within the limits
		assertAfter("lower limit", lowerLimit, actualMinimum);
		assertBefore("upper limit", upperLimit, actualMaximum);

		// We expect the random times include the full gamut of valid times
		Interval lowerInterval = new Interval(lowerLimit,
				lowerLimit.plus(ONE_HOUR));
		Interval upperInterval = new Interval(upperLimit.minus(ONE_HOUR),
				upperLimit);
		assertBetween("lower extent", lowerInterval, actualMinimum);
		assertBetween("upper extent", upperInterval, actualMaximum);

		// And they should be evenly distributed around the expected mean
		DateTime expectedMean = getExpectedMean(lowerLimit, upperLimit);
		Interval meanInterval = new Interval(expectedMean.minus(ONE_HOUR),
				expectedMean.plus(ONE_HOUR));
		assertBetween("mean", meanInterval, actualMean);
	}

	private static DateTime getExpectedMean(DateTime start, DateTime end) {
		long startMillis = start.getMillis();
		long endMillis = end.getMillis();
		long midMillis = ((endMillis - startMillis) / 2) + startMillis;
		return new DateTime(midMillis);
	}

	public static void assertBefore(String message, DateTime expected,
			DateTime actual) {
		assertTrue(message + ": expected " + actual + " to be before "
				+ expected, actual.isBefore(expected));
	}

	public static void assertAfter(String message, DateTime expected,
			DateTime actual) {
		assertTrue(message + ": expected " + actual + " to be after "
				+ expected, actual.isAfter(expected));
	}

	public static void assertBetween(String message, Interval expected,
			DateTime actual) {
		assertTrue(message + ": expected " + actual + " to be between "
				+ expected.getStart() + " and " + expected.getEnd(),
				expected.contains(actual));
	}

}
