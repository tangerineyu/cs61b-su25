package ngrams;

import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }
    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        //if (startYear < MIN_YEAR || endYear > MAX_YEAR || startYear > endYear) {
        //}
        for (Integer year : ts.keySet()) {
            if (year >= startYear && year <= endYear) {
                Double count = ts.get(year);
                this.put(year, count);
            }
        }
    }

    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
        if (this.isEmpty()) {
            return List.of();
        }
        return keySet().stream().toList();
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
        if (this.isEmpty()) {
            return List.of();
        }
        return values().stream().toList();
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        if (this.isEmpty() && ts.isEmpty()) {
            return new TimeSeries();
        }
        TimeSeries res = new TimeSeries();
        for (Integer year : this.keySet()) {
            double value = this.get(year);
            if (ts.containsKey(year)) {
                value += ts.get(year);
            }
            res.put(year, value);
        }
        for (Integer year : ts.keySet()) {
            if (!this.containsKey(year)) {
                res.put(year, ts.get(year));
            }
        }
        return res;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries res = new TimeSeries();
        for (Integer year : this.keySet()) {
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException("Invalid year range: " + year);
            }
            double divisor = ts.get(year);
            if (divisor == 0) {
                throw new IllegalArgumentException("Division by zero for year: " + year);
            }
            res.put(year, this.get(year) / divisor);
        }

        return res;
    }

}
