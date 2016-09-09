/*
 * Copyright (c) 2015 - 2016 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */
package com.ge.predix.analytics.customdto;

import java.util.ArrayList;
import java.util.List;

public class TimeSeries {

    private List<Integer> time_stamp;
    private List<List<Double>> values;

    public List<Integer> getTime_stamp() {
        if (time_stamp == null) {
            time_stamp = new ArrayList<>();
        }
        return time_stamp;
    }

    public void setTime_stamp(List<Integer> time_stamp) {
        this.time_stamp = time_stamp;
    }

    public List<List<Double>> getValues() {
        if (values == null) {
            values = new ArrayList<>();
        }
        return values;
    }

    public void setValues(List<List<Double>> values) {
        this.values = values;
    }
}
