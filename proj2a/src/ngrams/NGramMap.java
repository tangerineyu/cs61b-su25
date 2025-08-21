package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {


    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    private HashMap<String, TimeSeries> map = new HashMap<>();
    private TimeSeries totalCount = new TimeSeries();
    public NGramMap(String wordsFilename, String countsFilename) {
        if (wordsFilename == null || countsFilename == null) {
            throw new IllegalArgumentException("Filenames cannot be null.");
        }
        if (wordsFilename.isEmpty() || countsFilename.isEmpty()) {
            throw new IllegalArgumentException("Filenames cannot be empty.");
        }
        // Load the words and counts files, populating the map.
        // This is a placeholder; actual file reading and parsing logic should be implemented.
        // For now, we assume the map is populated with some data.
        // Example: map.put("example", new TimeSeries());
        In wordsfile = new In(wordsFilename);
        In countsfile = new In(countsFilename);
        totalCount = new TimeSeries();
        while (countsfile.hasNextLine()) {
            String[] tokens = countsfile.readLine().split(",");
            int year = Integer.parseInt(tokens[0]);
            double count = Double.parseDouble(tokens[1]);
            totalCount.put(year, count);
        }
        while (wordsfile.hasNextLine()) {
            String[] tokens = wordsfile.readLine().split("\t");
            String word = tokens[0];
            int year = Integer.parseInt(tokens[1]);
            double count = Double.parseDouble(tokens[2]);
            map.putIfAbsent(word, new TimeSeries());
            map.get(word).put(year, count);
        }


    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (startYear < MIN_YEAR || endYear > MAX_YEAR || startYear > endYear) {
            return new TimeSeries();
        }
        if (!map.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries ts = map.get(word);
        return new TimeSeries(ts, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!map.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(map.get(word), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        if (totalCount.isEmpty()) {
            return new TimeSeries();
        }
        return new TimeSeries(totalCount, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (startYear < MIN_YEAR || endYear > MAX_YEAR || startYear > endYear) {
            return new TimeSeries();
        }
        if (!map.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries wordHistory = new TimeSeries(map.get(word), startYear, endYear);
        TimeSeries countHistory = new TimeSeries(totalCount, startYear, endYear);
        if (countHistory.isEmpty()) {
            return new TimeSeries(); // Avoid division by zero
        }
        return wordHistory.dividedBy(countHistory);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!map.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries wordHistory = countHistory(word);
        TimeSeries countHistory = totalCountHistory();
        return wordHistory.dividedBy(countHistory);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        if (startYear < MIN_YEAR || endYear > MAX_YEAR || startYear > endYear) {
            return new TimeSeries();
        }
        TimeSeries result = new TimeSeries();
        for (String word : words) {
            if (!map.containsKey(word)) {
                continue;
            }
            TimeSeries wordHistory = new TimeSeries(map.get(word), startYear, endYear);
            TimeSeries countHistory = new TimeSeries(totalCount, startYear, endYear);
            if (countHistory.isEmpty()) {
                continue; // Avoid division by zero
            }
            TimeSeries relativeFrequency = wordHistory.dividedBy(countHistory);
            result = result.plus(relativeFrequency);
        }
        return result;

    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries result = new TimeSeries();
        for (String word : words) {
            if (!map.containsKey(word)) {
                continue;
            }
            TimeSeries wordHistory = countHistory(word);
            TimeSeries countHistory = totalCountHistory();
            if (countHistory.isEmpty()) {
                continue; // Avoid division by zero
            }
            TimeSeries relativeFrequency = wordHistory.dividedBy(countHistory);
            result = result.plus(relativeFrequency);
        }
        return result;
    }

}
