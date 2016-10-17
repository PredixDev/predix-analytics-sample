/*
 * Copyright (c) 2015 - 2016 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.ge.predix.analytics.customconnector.refimpl.postgresdb;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.helpers.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = PostgresRefImplConnectorApplication.class)
public class ApiV1AnalyticsCustomdataResourceTest {

	protected ClientCredentialsResourceDetails clientCredentialsResource;
	protected OAuth2RestTemplate oAuth2RestTemplate;

	@Autowired
	Environment environment;

	@Before
	public void setup() {
		clientCredentialsResource = new ClientCredentialsResourceDetails();
		clientCredentialsResource.setClientId(environment.getProperty("uaa_client_id"));
		clientCredentialsResource.setClientSecret(environment.getProperty("uaa_client_secret"));
		clientCredentialsResource.setAccessTokenUri(environment.getProperty("uaa_oauth_token_url"));

		oAuth2RestTemplate = new OAuth2RestTemplate(clientCredentialsResource);
	}

	@Test
	public void testPostRead() throws Exception {

		String dataConnectorReadUrl = environment.getProperty("ref_postgres_data_connector_read_url");
		String analyticReadDataRequestJSON = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("analytic-read-data-request.json"));
		ResponseEntity<String> response = postRequestWithJsonPayload(dataConnectorReadUrl, analyticReadDataRequestJSON);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		String expectedAnalyticReadDataResponseString = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("expected-analytic-read-data-response.json"));
		String actualAnalyticReadDataResponseString = response.getBody();
		JSONAssert.assertEquals(expectedAnalyticReadDataResponseString, actualAnalyticReadDataResponseString, JSONCompareMode.LENIENT);
	}

	public ResponseEntity<String> postRequestWithJsonPayload(String url, String jsonPayload) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON);
		headers.set("Accept", MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);
		return oAuth2RestTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
	}

}