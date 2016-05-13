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

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class DemoRTMLocoTest {
	@Test
	public void DemoRTMLocoTest() {
		DemoRTMLocoJavaEntryPoint drtm = new DemoRTMLocoJavaEntryPoint();

		String jsonInput = null;
		try {
			jsonInput = getTestData();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
		String result = drtm.RTM(jsonInput);
		//System.out.println(result);

	}

	private String getTestData() throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream("LocoData.json");
		try {
			String fileContents = IOUtils.toString(is);
			return fileContents;
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
		return null;
	}

}
