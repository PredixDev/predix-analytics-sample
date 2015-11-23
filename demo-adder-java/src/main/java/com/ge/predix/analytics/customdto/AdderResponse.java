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
