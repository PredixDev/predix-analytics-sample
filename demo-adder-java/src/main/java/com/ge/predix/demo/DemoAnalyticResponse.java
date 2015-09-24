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

public class DemoAnalyticResponse {

	protected Long result;

	/**
	 * Gets the value of the result property.
	 *

	 * @return
	 *     possible object is
	 *     {@link Long }
	 *
	 */
	public Long getResult() {
		return result;
	}

	/**
	 * Sets the value of the result property.
	 *
	 * @param value
	 *     allowed object is
	 *     {@link Long }
	 *
	 */
	public void setResult(Long value) {
		this.result = value;
	}

	@Override public String toString() {
		return "DemoAnalyticResponse{" +
			"result=" + result +
			'}';
	}

}
