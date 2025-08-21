package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap map;
    public HistoryHandler(NGramMap map) {
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        List<TimeSeries> seriesList = new ArrayList<>();
        for (String w : words) {
            TimeSeries ts = map.weightHistory(w, q.startYear(), q.endYear());
            if (ts != null) {
                seriesList.add(ts);
            } else {
                System.out.println("No data found for word: " + w);
            }
        }

            XYChart chart = Plotter.generateTimeSeriesChart(words, seriesList);
            String encodedImage = Plotter.encodeChartAsString(chart);
            if (encodedImage != null) {
                return  encodedImage;
            } else {
                return "图表编码失败";
            }



    }
}
