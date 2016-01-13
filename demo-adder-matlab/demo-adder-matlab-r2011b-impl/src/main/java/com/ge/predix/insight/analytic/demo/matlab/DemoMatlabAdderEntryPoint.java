/*
 * Copyright (c) 2015 - 2016 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.ge.predix.insight.analytic.demo.matlab;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import Addprj.Class1;

public class DemoMatlabAdderEntryPoint {

	Logger logger = LoggerFactory.getLogger(DemoMatlabAdderEntryPoint.class);

	public String add2Numbers(String jsonStr) {

		try {

			HashMap<String, Double> jsonDataMap = new ObjectMapper().readValue(jsonStr, HashMap.class);
			Double number1 = jsonDataMap.get("number1");
			Double number2 = jsonDataMap.get("number2");

			String[] inputStrArray = { number1.toString(), number2.toString() };
			Class1.main(inputStrArray);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}