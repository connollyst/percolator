package org.dosbcn.percolator.notifications;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.dosbcn.percolator.notifications.time.MockRandomTimeGenerator;
import org.dosbcn.percolator.notifications.time.RandomTimeGenerator;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import static org.dosbcn.percolator.AssertTime.assertEvenDistribution;

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
    private static final int EXPECTED_MAX_HOUR = 21;
    private static final int MORNING_HOUR = 8;
    private static final int AFTERNOON_HOUR = 13;
    private static final int EVENING_HOUR = 18;
    private static final int NIGHT_HOUR = 20;

    @Test
    public void testGetRandomTimeASAPStartingInTheMorning()
            throws InterruptedException, ExecutionException {
        DateTime now = mockToday(MORNING_HOUR);
        RandomTimeTestStatistics stats = generateStatisticsWithGetRandomTimeASAP(now);
        DateTime lowerLimit = mockToday(EXPECTED_MIN_HOUR);
        DateTime upperLimit = mockToday(EXPECTED_MAX_HOUR);
        assertEvenDistribution(stats, lowerLimit, upperLimit);
    }

    @Test
    public void testGetRandomTimeASAPStartingInTheAfternoon()
            throws InterruptedException, ExecutionException {
        DateTime now = mockToday(AFTERNOON_HOUR);
        RandomTimeTestStatistics stats = generateStatisticsWithGetRandomTimeASAP(now);
        DateTime lowerLimit = mockToday(AFTERNOON_HOUR);
        DateTime upperLimit = mockToday(EXPECTED_MAX_HOUR);
        assertEvenDistribution(stats, lowerLimit, upperLimit);
    }

    @Test
    public void testGetRandomTimeOneDayFromDate()
            throws InterruptedException, ExecutionException {
        final DateTime now = mockToday(MORNING_HOUR);
        final RandomTimeGenerator timeGenerator = new RandomTimeGenerator();
        RandomTimeTestStatistics stats = generateStatistics(new Callable<DateTime>() {
            @Override
            public DateTime call()
                    throws Exception {
                return new DateTime(timeGenerator.getRandomTimeOneDayFromDate(now));
            }
        });
        DateTime lowerLimit = mockTomorrow(EXPECTED_MIN_HOUR);
        DateTime upperLimit = mockTomorrow(EXPECTED_MAX_HOUR);
        assertEvenDistribution(stats, lowerLimit, upperLimit);
    }

    private RandomTimeTestStatistics generateStatisticsWithGetRandomTimeASAP(DateTime currentTime)
            throws InterruptedException, ExecutionException {
        final RandomTimeGenerator timeGenerator = new MockRandomTimeGenerator(currentTime);
        return generateStatistics(new Callable<DateTime>() {
            @Override
            public DateTime call()
                    throws Exception {
                return new DateTime(timeGenerator.getRandomTimeASAP());
            }
        });
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
     * Mock up a {@link DateTime} to represent today at a specific hour.
     *
     * @param hour
     *         the hour
     * @return the specified hour, tomorrow.
     */
    private DateTime mockToday(int hour) {
        return new DateTime(1985, 8, 6, hour, 0, 0, 0);
    }

    /**
     * Mock up a {@link DateTime} to represent tomorrow at a specific hour.
     *
     * @param hour
     *         the hour
     * @return the specified hour, tomorrow.
     */
    private DateTime mockTomorrow(int hour) {
        return mockToday(hour).plus(Period.days(1));
    }

}
