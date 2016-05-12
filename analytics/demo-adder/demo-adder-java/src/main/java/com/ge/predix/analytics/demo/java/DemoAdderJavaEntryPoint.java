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
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.predix.analytics.customdto.AdderResponse;

public class DemoAdderJavaEntryPoint {

	Logger logger = LoggerFactory.getLogger(DemoAdderJavaEntryPoint.class);
	ObjectMapper mapper = new ObjectMapper();

	public String add2Numbers(String jsonStr) throws IOException {

		HashMap<String, Integer> jsonDataMap = mapper.readValue(jsonStr, HashMap.class);
		long number1 = jsonDataMap.get("number1");
		long number2 = jsonDataMap.get("number2");

		AdderResponse output = null;
		output = new AdderResponse();
		output.setResult(number1 + number2);

		return mapper.writeValueAsString(output);

	}

}