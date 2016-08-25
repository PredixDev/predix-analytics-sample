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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ge.predix.analytics.customdto.AdderResponse;
import com.ge.predix.analytics.customdto.Data;
import com.ge.predix.analytics.customdto.TimeseriesOutput;

public class DemoJavaAdderWithTimeseriesEntryPoint {

	Logger logger = LoggerFactory.getLogger(DemoJavaAdderWithTimeseriesEntryPoint.class);
	ObjectMapper mapper = new ObjectMapper();

	public String add2NumberArrays(String inputData, Map<String, byte[]>  inputModelMap) throws IOException {
		Double sumThreshold = Double.parseDouble(new String(inputModelMap.get("threshold")));
		logger.info("Threshold set to:" + sumThreshold);
		JsonNode node = mapper.readTree(inputData);
		JsonNode dataNode = node.get("data");
		JsonNode timeseriesNode = dataNode.get("time_series");
		JsonNode timestampNode = timeseriesNode.get("time_stamp");
		List<Double> results = null;
		List<String> timestampArray = null;
		if (timestampNode.isArray()) {
			ArrayNode timestamps = (ArrayNode) timestampNode;

			JsonNode number1Node = timeseriesNode.get("numberArray1");
			ArrayNode number1Values = (ArrayNode) number1Node;

			JsonNode number2Node = timeseriesNode.get("numberArray2");
			ArrayNode number2Values = (ArrayNode) number2Node;

			results = new ArrayList<>();
			timestampArray = new ArrayList<>();

			for (int i = 0; i < timestamps.size(); i++) {
				timestampArray.add(timestamps.get(i).asText());
				double temp = number1Values.get(i).asDouble() + number2Values.get(i).asDouble();
				if (temp > sumThreshold) results.add(-1D);
				else results.add(number1Values.get(i).asDouble() + number2Values.get(i).asDouble());

			}
		}

		AdderResponse outputResponse = new AdderResponse();
		Data data = new Data();
		TimeseriesOutput output = new TimeseriesOutput();
		data.setTime_series(output);
		output.setTime_stamp(timestampArray);
		output.setSum(results);
		outputResponse.setData(data);

		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		return mapper.writeValueAsString(outputResponse);
	}

	public String add2NumberArrays(String jsonStr) throws IOException {

		JsonNode node = mapper.readTree(jsonStr);
		JsonNode dataNode = node.get("data");
		JsonNode timeseriesNode = dataNode.get("time_series");
		JsonNode timestampNode = timeseriesNode.get("time_stamp");
		List<Double> results = null;
		List<String> timestampArray = null;
		if (timestampNode.isArray()) {
			ArrayNode timestamps = (ArrayNode) timestampNode;

			JsonNode number1Node = timeseriesNode.get("numberArray1");
			ArrayNode number1Values = (ArrayNode) number1Node;

			JsonNode number2Node = timeseriesNode.get("numberArray2");
			ArrayNode number2Values = (ArrayNode) number2Node;

			results = new ArrayList<>();
			timestampArray = new ArrayList<>();

			for (int i = 0; i < timestamps.size(); i++) {
				timestampArray.add(timestamps.get(i).asText());
				results.add(number1Values.get(i).asDouble() + number2Values.get(i).asDouble());
			}
		}

		AdderResponse outputResponse = new AdderResponse();
		Data data = new Data();
		TimeseriesOutput output = new TimeseriesOutput();
		data.setTime_series(output);
		output.setTime_stamp(timestampArray);
		output.setSum(results);
		outputResponse.setData(data);

		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		return mapper.writeValueAsString(outputResponse);

	}

}