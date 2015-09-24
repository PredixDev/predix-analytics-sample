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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * REST API with the URL of api/v1/demoadder/execution where
 * "api" is the URL prefix expected by Predix Analytics
 * "v1" is the version of the analytic defined in the analytic catalog
 * "demoadder" is the name of the analytic defined in the analytic catalog
 * "execution" is the REST resource name expected by Predix Analytics
 *
 * Note: The version of the analytic defined in the analytic catalog is transformed into URL friendly name by
 * - Substituting dots '.' with underscores '_'
 * - Converting to all lower cases
 *
 * Note: The name of the analytic defined in the analytic catalog is transformed into URL friendly name by
 * - Substituting spaces ' ' with underscores '_'
 * - Converting to all lower cases
 *
 * For example, suppose you defined the following analytic in the analytic catalog
 * Name: Anomaly Detection
 * Version: v1.0
 * Then define the REST URL as api/v1_0/anomaly_detection/execution
 */
@RestController
@RequestMapping(value = DemoAnalyticService.ANALYTIC_REST_URL)
public class DemoAnalyticService {

	public static final String ANALYTIC_NAME = "demo_adder_java";
	public static final String ANALYTIC_VERSION = "v1";
	public static final String ANALYTIC_REST_URL_PREFIX = "api";
	public static final String ANALYTIC_REST_RESOURCE = "execution";
	public static final String ANALYTIC_REST_URL = ANALYTIC_REST_URL_PREFIX + "/" + ANALYTIC_VERSION + "/" + ANALYTIC_NAME;

	// api/v1/demo_analytic_java/execution
	public static final String ANALYTIC_REST_RESOURCE_URL = ANALYTIC_REST_URL + "/" + ANALYTIC_REST_RESOURCE;

	@RequestMapping(value = ANALYTIC_REST_RESOURCE, method = RequestMethod.POST)
	public ResponseEntity<String> execute(@RequestBody String input) {
		try {
			return new ResponseEntity<>(run(input), HttpStatus.OK);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public String run(String input) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		DemoAnalyticRequest request = mapper.readValue(input, DemoAnalyticRequest.class);
		DemoAnalyticResponse response = new DemoAnalyticResponse();
		response.setResult(request.getNumber1() + request.getNumber2());
		return mapper.writeValueAsString(response);
	}

}
