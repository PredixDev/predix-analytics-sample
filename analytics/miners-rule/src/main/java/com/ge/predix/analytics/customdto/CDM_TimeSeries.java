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

import java.util.List;

public class CDM_TimeSeries {

    private List<Long> time_stamp;
    private List<Double> cdm_values;
    private List<Double> quality;

    public List<Long> getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(List<Long> time_stamp) {
        this.time_stamp = time_stamp;
    }

    public List<Double> getCdm_values() {
        return cdm_values;
    }

    public void setCdm_values(List<Double> cdm_values) {
        this.cdm_values = cdm_values;
    }

    public List<Double> getQuality() {
        return quality;
    }

    public void setQuality(List<Double> quality) {
        this.quality = quality;
    }
}
