package com.ninchester.songstrend.api.domain;

public class TimeSeries {

    private long count;
    private String date;

    public TimeSeries(long count, String date) {
        this.count = count;
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TimeSeries{" +
                "count=" + count +
                ", date='" + date + '\'' +
                '}';
    }
}
