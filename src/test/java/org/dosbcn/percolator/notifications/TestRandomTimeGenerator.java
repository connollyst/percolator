package org.dosbcn.percolator.notifications;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.dosbcn.percolator.notifications.time.RandomTimeGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * Test cases for the {@link RandomTimeGenerator}.
 *
 * @author Sean Connolly
 */
@RunWith(RobolectricTestRunner.class)
public class TestRandomTimeGenerator {

    private static final int TEST_ITERATION_COUNT = 1000;
    private final RandomTimeGenerator timeGenerator = new RandomTimeGenerator();

    @Test
    public void testGetRandomTimeOneDayFromDate()
            throws InterruptedException, ExecutionException {
        final Date now = Calendar.getInstance().getTime();
        RandomTimeStatistics stats = generateStatistics(new Callable<Date>() {
            @Override
            public Date call()
                    throws Exception {
                return timeGenerator.getRandomTimeOneDayFromDate(now);
            }
        });
        System.out.println("Minimum: " + stats.getMinimumTime());
        System.out.println("Mean: " + stats.getMean());
        System.out.println("Maximum" + stats.getMaximumTime());
    }

    /**
     * Return statistics about the {@link Date}s returned from a large number
     * of calls to the provided {@link Callable}.
     *
     * @param generatorCall
     *         the function to call to generate a random time
     */
    private RandomTimeStatistics generateStatistics(Callable<Date> generatorCall)
            throws InterruptedException, ExecutionException {
        RandomTimeStatistics stats = new RandomTimeStatistics();
        for (int i = 0; i < TEST_ITERATION_COUNT; i++) {
            RunnableFuture<Date> generatorRun = new FutureTask<Date>(generatorCall);
            generatorRun.run();
            stats.addRandomTime(generatorRun.get());
        }
        return stats;
    }

    /**
     * Some basic statistics about a series of generated {@link Date}s.<br/>
     * The dates are assumed to have a normal distribution.
     */
    private class RandomTimeStatistics {

        private final List<Double> randomTimes;
        private final Mean meanCalculator;
        private Date minimumTime = null;
        private Date maximumTime = null;

        public RandomTimeStatistics() {
            randomTimes = new ArrayList<Double>(TEST_ITERATION_COUNT);
            meanCalculator = new Mean();
        }

        public void addRandomTime(Date randomTime) {
            randomTimes.add((double) randomTime.getTime());
            if (minimumTime == null || randomTime.before(minimumTime)) {
                minimumTime = randomTime;
            }
            if (maximumTime == null || randomTime.after(maximumTime)) {
                maximumTime = randomTime;
            }
        }

        public Date getMean() {
            double[] values = getValues();
            double[] weights = getWeights();
            double meanTime = meanCalculator.evaluate(values, weights);
            return new Date((long) meanTime);
        }

        public Date getMinimumTime() {
            return minimumTime;
        }

        public Date getMaximumTime() {
            return maximumTime;
        }

        private double[] getValues() {
            Double[] values = randomTimes.toArray(new Double[randomTimes.size()]);
            return ArrayUtils.toPrimitive(values);
        }

        private double[] getWeights() {
            double[] weights = new double[randomTimes.size()];
            Arrays.fill(weights, 1d);
            return weights;
        }
    }


}
