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

public class Data {
	protected TimeseriesOutput time_series;

	public TimeseriesOutput getTime_series() {
		return time_series;
	}

	public void setTime_series(TimeseriesOutput time_series) {
		this.time_series = time_series;
	}
}
