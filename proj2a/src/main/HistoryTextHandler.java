package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap map;
    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder sb = new StringBuilder();
        if (words == null || words.isEmpty()) {
            return "";
        }
        for (String word : words) {
            TimeSeries wordWeightHistory = map.weightHistory(word, startYear, endYear);
            if (!wordWeightHistory.isEmpty()) {
                sb.append(word).append(": ").append(wordWeightHistory.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
