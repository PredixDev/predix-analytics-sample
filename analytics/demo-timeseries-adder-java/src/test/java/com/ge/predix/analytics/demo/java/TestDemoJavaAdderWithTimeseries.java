/*
 * Copyright (c) 2015 - 2016 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.ge.predix.analytics.demo.java;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class TestDemoJavaAdderWithTimeseries {
	@Test
	public void testAdd2Numbers() throws IOException, JSONException {
		String inputDataString = getInputDataString();
		DemoJavaAdderWithTimeseriesEntryPoint adder = new DemoJavaAdderWithTimeseriesEntryPoint();

		String result = adder.add2NumberArrays(inputDataString);
		assertNotNull(result);
		JSONAssert.assertEquals(getExpectedResultString(), result, true);
	}

	private String getInputDataString() throws IOException {
		InputStream inputDataStream = getClass().getClassLoader().getResourceAsStream("analyticInputDataWithTimeseriesData.json");
		return IOUtils.toString(inputDataStream);
	}

	private String getExpectedResultString() throws IOException {
		InputStream expectedOutputDataStream = getClass().getClassLoader().getResourceAsStream("analyticOutputDataWithTimeseriesData.json");
		return IOUtils.toString(expectedOutputDataStream);
	}
}
