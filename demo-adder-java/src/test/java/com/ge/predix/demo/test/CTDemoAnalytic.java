/*
 * Copyright (c) 2015 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */
package com.ge.predix.demo.test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.predix.demo.DemoAnalyticApplication;
import com.ge.predix.demo.DemoAnalyticRequest;
import com.ge.predix.demo.DemoAnalyticResponse;
import com.ge.predix.demo.DemoAnalyticService;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class CTDemoAnalytic {
	private static final String SPRING_BOOT_PORT = "29090";
	private static ConfigurableApplicationContext context;

	ObjectMapper jsonMapper = new ObjectMapper();

	@BeforeClass
	public static void startServer() {
		context = SpringApplication.run(DemoAnalyticApplication.class, "--server.port=" + SPRING_BOOT_PORT);
	}

	@Test
	public void whenTwoNumberIsAdded_ShouldReturnAddedResult() throws IOException {
		RequestSpecification specification = givenDemoAnalyticInputWithTwoNumbers();
		Response response = whenDoPostToExecuteDemoAnalyticService(specification);
		thenDemoAnalyticResultMustMatchExpected(response);
	}

	private RequestSpecification givenDemoAnalyticInputWithTwoNumbers() {
		DemoAnalyticRequest demoAnalyticRequest = new DemoAnalyticRequest();
		demoAnalyticRequest.setNumber1(100l);
		demoAnalyticRequest.setNumber2(Long.MAX_VALUE - 100);

		String requestJsonString = null;
		try {
			requestJsonString = jsonMapper.writeValueAsString(demoAnalyticRequest);
			System.out.println("Request for demo analytic: " + requestJsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return given().contentType(ContentType.JSON).body(requestJsonString);

	}

	private Response whenDoPostToExecuteDemoAnalyticService(RequestSpecification specification) {
		return specification.when().post("http://localhost:" + SPRING_BOOT_PORT + "/" + DemoAnalyticService.ANALYTIC_REST_RESOURCE_URL);
	}

	private void thenDemoAnalyticResultMustMatchExpected(Response response) throws IOException {
		response.then().statusCode(200);
		String resultJsonString = response.then().extract().asString();

		DemoAnalyticResponse expectedAdderResponse = new DemoAnalyticResponse();
		expectedAdderResponse.setResult(Long.MAX_VALUE);
		String expectedResultJsonString = jsonMapper.writeValueAsString(expectedAdderResponse);

		System.out.println("Expected result from demo analytic: " + expectedResultJsonString);
		System.out.println("Actual result from demo analytic: " + resultJsonString);
		assertEquals(expectedResultJsonString, resultJsonString);
	}

	@AfterClass
	public static void stopServer() {
		if (context != null) {
			context.close();
		}
	}
}