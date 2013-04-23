package org.dosbcn.percolator.notifications;

import com.google.common.primitives.Doubles;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Some basic statistics about a series of generated {@link DateTime}s.<br/>
 * Note: the dates are assumed to have a normal distribution.
 *
 * @author Sean Connolly
 */
public class RandomTimeTestStatistics {


    private final List<Double> randomTimes;
    private final Mean meanCalculator;
    private DateTime minimumTime = null;
    private DateTime maximumTime = null;

    public RandomTimeTestStatistics(int size) {
        randomTimes = new ArrayList<Double>(size);
        meanCalculator = new Mean();
    }

    public void addRandomTime(DateTime randomTime) {
        randomTimes.add((double) randomTime.getMillis());
        if (minimumTime == null || randomTime.isBefore(minimumTime)) {
            minimumTime = randomTime;
        }
        if (maximumTime == null || randomTime.isAfter(maximumTime)) {
            maximumTime = randomTime;
        }
    }

    public DateTime getMean() {
        double[] values = getValues();
        double[] weights = getWeights();
        double meanTime = meanCalculator.evaluate(values, weights);
        return new DateTime((long) meanTime);
    }

    public DateTime getMinimumTime() {
        return minimumTime;
    }

    public DateTime getMaximumTime() {
        return maximumTime;
    }

    private double[] getValues() {
        return Doubles.toArray(randomTimes);
    }

    private double[] getWeights() {
        double[] weights = new double[randomTimes.size()];
        Arrays.fill(weights, 1d);
        return weights;
    }
}
