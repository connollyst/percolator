package org.dosbcn.percolator.notifications;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.dosbcn.percolator.notifications.time.RandomTimeGenerator;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import static junit.framework.Assert.assertTrue;

/**
 * Test cases for the {@link RandomTimeGenerator}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestRandomTimeGenerator {

    // Sample a large number of random times to evaluate the generator's behavior
    private static final int TEST_ITERATION_COUNT = 1000;
    private static final int EXPECTED_MIN_HOUR = 11;
    private static final int EXPECTED_MEAN_HOUR = 16;
    private static final int EXPECTED_MAX_HOUR = 21;
    private static final Period ONE_HOUR = Period.hours(1);
    private final RandomTimeGenerator timeGenerator = new RandomTimeGenerator();

    @Test
    public void testGetRandomTimeOneDayFromDate()
            throws InterruptedException, ExecutionException {
        final DateTime now = new DateTime();
        RandomTimeTestStatistics stats = generateStatistics(new Callable<DateTime>() {
            @Override
            public DateTime call()
                    throws Exception {
                return new DateTime(timeGenerator.getRandomTimeOneDayFromDate(now.toDate()));
            }
        });
        DateTime actualMinimum = stats.getMinimumTime();
        DateTime actualMaximum = stats.getMaximumTime();
        DateTime actualMean = stats.getMean();

        // We expect the random times to be within the limits
        DateTime lowerLimit = getHourTomorrow(EXPECTED_MIN_HOUR);
        DateTime upperLimit = getHourTomorrow(EXPECTED_MAX_HOUR);
        assertAfter(lowerLimit, actualMinimum);
        assertBefore(upperLimit, actualMaximum);

        // We expect the random times include the full gamut of valid times
        Interval lowerInterval = new Interval(lowerLimit, lowerLimit.plus(ONE_HOUR));
        Interval upperInterval = new Interval(upperLimit.minus(ONE_HOUR), upperLimit);
        assertBetween(lowerInterval, actualMinimum);
        assertBetween(upperInterval, actualMaximum);

        // And they should be evenly distributed around the expected mean
        DateTime expectedMean = getHourTomorrow(EXPECTED_MEAN_HOUR);
        Interval meanInterval = new Interval(expectedMean.minus(ONE_HOUR), expectedMean.plus(ONE_HOUR));
        assertBetween(meanInterval, actualMean);
    }

    /**
     * Return statistics about the {@link DateTime}s returned from a large
     * number of calls to the provided {@link Callable}.
     *
     * @param generatorCall
     *         the function to call to generate a random time
     */
    private RandomTimeTestStatistics generateStatistics(Callable<DateTime> generatorCall)
            throws InterruptedException, ExecutionException {
        RandomTimeTestStatistics stats = new RandomTimeTestStatistics(TEST_ITERATION_COUNT);
        for (int i = 0; i < TEST_ITERATION_COUNT; i++) {
            RunnableFuture<DateTime> generatorRun = new FutureTask<DateTime>(generatorCall);
            generatorRun.run();
            stats.addRandomTime(generatorRun.get());
        }
        return stats;
    }

    /**
     * Get a specific hour, tomorrow, exactly.
     *
     * @param hour
     *         the hour
     * @return the specified hour, tomorrow.
     */
    private DateTime getHourTomorrow(int hour) {
        // Tomorrow as the specified hour exactly..
        return new DateTime()
                .plus(Period.days(1))
                .withHourOfDay(hour)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0)
                .withMillisOfSecond(0);
    }

    private void assertBefore(DateTime expected, DateTime actual) {
        assertTrue("expected " + actual + " to be before " + expected,
                actual.isBefore(expected));
    }

    private void assertAfter(DateTime expected, DateTime actual) {
        assertTrue("expected " + actual + " to be after " + expected,
                actual.isAfter(expected));
    }

    private void assertBetween(Interval expected, DateTime actual) {
        assertTrue("expected " + actual + " to be between "
                + expected.getStart() + " and " + expected.getEnd(),
                expected.contains(actual));
    }

}
