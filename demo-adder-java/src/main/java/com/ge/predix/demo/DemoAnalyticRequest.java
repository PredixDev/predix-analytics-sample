/*
 * Copyright (c) 2015 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */
package com.ge.predix.demo;

public class DemoAnalyticRequest {

	protected long number1;
	protected long number2;

	/**
	 * Gets the value of the number1 property.
	 *
	 */
	public long getNumber1() {
		return number1;
	}

	/**
	 * Sets the value of the number1 property.
	 *
	 */
	public void setNumber1(long value) {
		this.number1 = value;
	}

	/**
	 * Gets the value of the number2 property.
	 *
	 */
	public long getNumber2() {
		return number2;
	}

	/**
	 * Sets the value of the number2 property.
	 *
	 */
	public void setNumber2(long value) {
		this.number2 = value;
	}

	@Override public String toString() {
		return "DemoAnalyticRequest{" +
			"number1=" + number1 +
			", number2=" + number2 +
			'}';
	}

}
