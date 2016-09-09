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

public class Input {

    private CDM_TimeSeries current_cdm;
    private StressesTimeSeries recentStresses;

    public CDM_TimeSeries getCurrent_cdm() {
        if (current_cdm == null) {
            current_cdm = new CDM_TimeSeries();
        }
        return current_cdm;
    }

    public void setCurrent_cdm(CDM_TimeSeries current_cdm) {
        this.current_cdm = current_cdm;
    }

    public StressesTimeSeries getRecentStresses() {
        if (recentStresses == null) {
            recentStresses = new StressesTimeSeries();
        }
        return recentStresses;
    }

    public void setRecentStresses(StressesTimeSeries recentStresses) {
        this.recentStresses = recentStresses;
    }

}
