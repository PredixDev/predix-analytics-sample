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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.predix.insight.dto.customdataprovider.AnalyticReadDataRequest;
import com.ge.predix.insight.dto.customdataprovider.AnalyticReadDataResponse;
import com.ge.predix.insight.dto.customdataprovider.AnalyticWriteDataRequest;
import com.ge.predix.insight.dto.customdataprovider.AnalyticWriteDataResponse;

@Path("/api/v1/analytics/customdata")
@Service
public class ApiV1AnalyticsCustomdataResource {

	@Autowired
	private CustomDataResourceManager customDataResourceManager;

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/read")
	public Response postRead(AnalyticReadDataRequest analyticReadDataRequest, @Context HttpHeaders httpHeaders, @Context UriInfo uriInfo) {
		AnalyticReadDataResponse analyticReadDataResponse = customDataResourceManager.getAnalyticReadDataResponse(analyticReadDataRequest);
		return Response.ok().entity(analyticReadDataResponse).build();
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/write")
	public Response postWrite(AnalyticWriteDataRequest analyticWriteDataRequest, @Context HttpHeaders httpHeaders, @Context UriInfo uriInfo) {
		AnalyticWriteDataResponse analyticWriteDataResponse = customDataResourceManager.getAnalyticWriteDataResponse(analyticWriteDataRequest);
		return Response.status(Response.Status.CREATED).entity(analyticWriteDataResponse).build();
	}

	@GET
	@Path("/healthcheck")
	public Response healthcheck(@Context HttpHeaders httpHeaders, @Context UriInfo uriInfo) {
		return Response.status(Response.Status.OK).build();
	}
}