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

import com.fasterxml.jackson.annotation.JsonProperty;

public class WindLocoRTMValues {
	private double[] wind_speed;
	private double[] rtm;
	private double[] loco_speed;

	public double[] getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(double[] wind_speed) {
		this.wind_speed = wind_speed;
	}

	@JsonProperty("RTM")
	public double[] getRTM() {
		return rtm;
	}

	public void setRTM(double[] rtm) {
		this.rtm = rtm;
	}

	public double[] getLoco_speed() {
		return loco_speed;
	}

	public void setLoco_speed(double[] loco_speed) {
		this.loco_speed = loco_speed;
	}
}
