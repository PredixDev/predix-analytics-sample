package com.ge.predix.analytics.demo.java;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.predix.analytics.customdto.AdderResponse;

public class DemoAdderJavaEntryPoint {

	Logger logger = LoggerFactory.getLogger(DemoAdderJavaEntryPoint.class);
	ObjectMapper mapper = new ObjectMapper();

	public String add2Numbers(String jsonStr) {

		try {
			HashMap<String, Integer> jsonDataMap = mapper.readValue(jsonStr, HashMap.class);
			long number1 = jsonDataMap.get("number1");
			long number2 = jsonDataMap.get("number2");

			AdderResponse output = null;
			output = new AdderResponse();
			output.setResult(number1 + number2);

			return mapper.writeValueAsString(output);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}