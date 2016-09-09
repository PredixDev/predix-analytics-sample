package com.ge.predix.analytics.customdto;

import java.util.ArrayList;
import java.util.List;

public class StressesTimeSeries {

    private List<Long> time_stamp;
    private List<List<Double>> stresses;
    private List<Double> quality;

    public List<Long> getTime_stamp() {
        if (this.time_stamp == null) {
            this.time_stamp = new ArrayList<Long>();
        }
        return time_stamp;
    }

    public void setTime_stamp(List<Long> time_stamp) {
        this.time_stamp = time_stamp;
    }

    public List<List<Double>> getStresses() {
        if (this.stresses == null) {
            this.stresses = new ArrayList<List<Double>>();
        }
        return stresses;
    }

    public void setStresses(List<List<Double>> stresses) {
        this.stresses = stresses;
    }

    public List<Double> getQuality() {
        return quality;
    }

    public void setQuality(List<Double> quality) {
        this.quality = quality;
    }
}
