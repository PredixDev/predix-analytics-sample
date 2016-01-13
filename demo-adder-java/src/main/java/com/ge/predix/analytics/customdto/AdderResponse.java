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

public class AdderResponse {

	protected Long result;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AdderResponse that = (AdderResponse) o;

		if (result != null ? !result.equals(that.result) : that.result != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return result != null ? result.hashCode() : 0;
	}

	@Override public String toString() {
		return "AdderOutput{" +
			"result=" + result +

			'}';
	}

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

}
