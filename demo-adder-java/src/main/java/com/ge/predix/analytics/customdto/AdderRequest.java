package com.ge.predix.analytics.customdto;

public class AdderRequest {

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
		return "AdderInput{" +
			"number1=" + number1 +
			", number2=" + number2 +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AdderRequest that = (AdderRequest) o;

		if (number1 != that.number1)
			return false;
		if (number2 != that.number2)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (number1 ^ (number1 >>> 32));
		result = 31 * result + (int) (number2 ^ (number2 >>> 32));
		return result;
	}
}
