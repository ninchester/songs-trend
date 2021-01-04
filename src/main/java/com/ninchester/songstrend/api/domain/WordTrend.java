package com.ninchester.songstrend.api.domain;

import java.util.List;

public class WordTrend {
    private String word;
    private List<TimeSeries> timeSeries;

    public WordTrend(String word, List<TimeSeries> timeSeries) {
        this.word = word;
        this.timeSeries = timeSeries;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<TimeSeries> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(List<TimeSeries> timeSeries) {
        this.timeSeries = timeSeries;
    }

    @Override
    public String toString() {
        return "WordTrend{" +
                "word='" + word + '\'' +
                ", timeSeries=" + timeSeries +
                '}';
    }
}
