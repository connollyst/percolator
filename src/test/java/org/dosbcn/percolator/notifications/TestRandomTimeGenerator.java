package org.dosbcn.percolator.notifications;

import com.xtremelabs.robolectric.RobolectricTestRunner;
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
    private final RandomTimeGenerator timeGenerator = new RandomTimeGenerator();

    @Test
    public void testGetRandomTimeASAP()
            throws InterruptedException, ExecutionException {

    }

    @Test
    public void testGetRandomTimeOneDayFromDate()
            throws InterruptedException, ExecutionException {
        final DateTime now = new DateTime();
        RandomTimeTestStatistics stats = generateStatistics(new Callable<DateTime>() {
            @Override
            public DateTime call()
                    throws Exception {
                return new DateTime(timeGenerator.getRandomTimeOneDayFromDate(now));
            }
        });
        DateTime lowerLimit = getHourTomorrow(EXPECTED_MIN_HOUR);
        DateTime upperLimit = getHourTomorrow(EXPECTED_MAX_HOUR);
        assertEvenDistribution(stats, lowerLimit, upperLimit);
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

}
