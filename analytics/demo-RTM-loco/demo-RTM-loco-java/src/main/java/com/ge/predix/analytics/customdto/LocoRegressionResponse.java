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

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocoRegressionResponse {

	@JsonProperty(value = "R2")
	protected double[] r2;

	@JsonProperty(value = "Prediction")
	protected double[] prediction;

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		LocoRegressionResponse that = (LocoRegressionResponse) o;

		if (!Arrays.equals(r2, that.r2))
			return false;
		return Arrays.equals(prediction, that.prediction);

	}

	@Override public int hashCode() {
		int result = Arrays.hashCode(r2);
		result = 31 * result + Arrays.hashCode(prediction);
		return result;
	}

	@Override public String toString() {
		return "LocoRegressionResponse{" +
			"r2=" + Arrays.toString(r2) +
			", prediction=" + Arrays.toString(prediction) +
			'}';
	}

	public double[] getR2() {
		return r2;
	}

	public void setR2(double[] r2) {
		this.r2 = r2;
	}

	public double[] getPrediction() {
		return prediction;
	}

	public void setPrediction(double[] prediction) {
		this.prediction = prediction;
	}
}
