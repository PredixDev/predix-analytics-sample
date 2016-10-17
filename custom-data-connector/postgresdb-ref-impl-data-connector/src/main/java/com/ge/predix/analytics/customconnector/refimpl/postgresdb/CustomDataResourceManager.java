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

import static java.text.MessageFormat.format;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ge.predix.insight.dto.customdataprovider.AnalyticReadDataRequest;
import com.ge.predix.insight.dto.customdataprovider.AnalyticReadDataResponse;
import com.ge.predix.insight.dto.customdataprovider.AnalyticWriteDataRequest;
import com.ge.predix.insight.dto.customdataprovider.AnalyticWriteDataResponse;
import com.ge.predix.insight.dto.customdataprovider.Field;
import com.ge.predix.insight.dto.customdataprovider.OrchestrationExecutionContext;
import com.ge.predix.insight.dto.errorresponse.ErrorResponse;

@Component
public class CustomDataResourceManager {

	private final static Logger LOGGER = LoggerFactory.getLogger(CustomDataResourceManager.class);

	private enum ErrorCode {
		FIELD_EXCEPTION,
		GENERAL_EXCEPTION

	}

	final private JdbcTemplate jdbcTemplate;

	@Autowired
	public CustomDataResourceManager(JdbcTemplate template) {
		this.jdbcTemplate = template;
	}

	AnalyticReadDataResponse getAnalyticReadDataResponse(final AnalyticReadDataRequest analyticReadDataRequest) {

		AnalyticReadDataResponse analyticReadDataResponse = new AnalyticReadDataResponse();
		analyticReadDataResponse.setOrchestrationExecutionContext(analyticReadDataRequest.getOrchestrationExecutionContext());
		analyticReadDataResponse.getField().addAll(analyticReadDataRequest.getField());

		Map<String, String> customAttributes = convertAttributes(analyticReadDataRequest.getCustomAttributes());
		Map<String, String> systemAttributes = convertAttributes(analyticReadDataRequest.getSystemAttributes());

		LOGGER.info("Processing read request...");
		LOGGER.info("data source id : " + analyticReadDataRequest.getDataSourceId());
		LOGGER.info("custom attributes : " + customAttributes.toString());
		LOGGER.info("system attributes : " + systemAttributes.toString());

		boolean isGenericSchema = isGenericSchema(customAttributes);
		try {
			List<Field> fieldList = analyticReadDataResponse.getField();
			for (Field field : fieldList) {
				retrieveDataForField(field, analyticReadDataRequest.getOrchestrationExecutionContext(), isGenericSchema);
			}
		} catch (Exception e) {
			String message = format("Unknown error occured {0}", e.getMessage());
			LOGGER.error(message, e);
			analyticReadDataResponse.setErrorResponse(createErrorResponse(ErrorCode.GENERAL_EXCEPTION, message));
		}
		return analyticReadDataResponse;
	}

	AnalyticWriteDataResponse getAnalyticWriteDataResponse(AnalyticWriteDataRequest analyticWriteDataRequest) {
		AnalyticWriteDataResponse analyticWriteDataResponse = new AnalyticWriteDataResponse();
		analyticWriteDataResponse.setOrchestrationExecutionContext(analyticWriteDataRequest.getOrchestrationExecutionContext());
		analyticWriteDataResponse.getField().addAll(analyticWriteDataRequest.getField());

		Map<String, String> customAttributes = convertAttributes(analyticWriteDataRequest.getCustomAttributes());
		Map<String, String> systemAttributes = convertAttributes(analyticWriteDataRequest.getSystemAttributes());

		LOGGER.info("Processing write request...");
		LOGGER.info("data source id : " + analyticWriteDataRequest.getDataSourceId());
		LOGGER.info("custom attributes : " + customAttributes.toString());
		LOGGER.info("system attributes : " + systemAttributes.toString());

		boolean isGenericSchema = isGenericSchema(customAttributes);
		try {
			List<Field> fieldList = analyticWriteDataResponse.getField();
			for (Field field : fieldList) {
				insertOrUpdateFieldWithData(field, analyticWriteDataRequest.getOrchestrationExecutionContext(), isGenericSchema);
			}
		} catch (Exception e) {
			String message = format("Unknown error occured {0}", e.getMessage());
			LOGGER.error(message, e);
			analyticWriteDataResponse.setErrorResponse(createErrorResponse(ErrorCode.GENERAL_EXCEPTION, message));
		}
		return analyticWriteDataResponse;
	}

	private Map<String, String> convertAttributes(Object customObject) {
		if (customObject == null) {
			return new HashMap<>();
		}
		return (Map<String, String>) customObject;
	}

	private void insertOrUpdateFieldWithData(Field field, OrchestrationExecutionContext orchestrationExecutionContext, boolean isGenericSchema) {
		try {
			int[] rowsUpdatedOrInserted = updateOrInsertFieldData(field, orchestrationExecutionContext, isGenericSchema);
			LOGGER.info(format("{0} rows inserted/updated for field {1}", rowsUpdatedOrInserted[0], field.getFieldId()));
		} catch (Exception e) {
			String message = format("Unable to update field {0}. {1}", field.getFieldId(), e.getMessage());
			LOGGER.error(message, e);
			field.setErrorResponse(createErrorResponse(ErrorCode.FIELD_EXCEPTION, message));
		}
	}

	private void retrieveDataForField(Field field, OrchestrationExecutionContext orchestrationExecutionContext, boolean isGenericSchema) {
		try {
			List<List<Object>> dbResult = queryFieldData(field, orchestrationExecutionContext, isGenericSchema);
			field.setData(dbResult);
		} catch (Exception e) {
			String message = format("Unable to retrieve field {0}. {1}", field.getFieldId(), e.getMessage());
			LOGGER.error(message, e);
			field.setErrorResponse(createErrorResponse(ErrorCode.FIELD_EXCEPTION, message));
		}
	}

	private int[] updateOrInsertFieldData(final Field field, OrchestrationExecutionContext orchestrationExecutionContext, boolean isGenericSchema) {
		SQLBuilder requestBuilder;
		if (isGenericSchema) {
			requestBuilder = new SQLBuilderForGenericSchema(orchestrationExecutionContext, field.getQueryCriteria(), SQLBuilder.StatementType.INSERT_OR_UPDATE, (List<Object>) field.getData(), field);
		} else {
			requestBuilder = new SQLBuilderForNormalizedSchema(orchestrationExecutionContext, field.getQueryCriteria(), SQLBuilder.StatementType.INSERT_OR_UPDATE, (List<Object>) field.getData());
		}
		final String[] sql = requestBuilder.buildInsertOrUpdateSql();

		return jdbcTemplate.batchUpdate(sql);
	}

	private List<List<Object>> queryFieldData(final Field field, OrchestrationExecutionContext orchestrationExecutionContext, boolean isGenericSchema) {
		SQLBuilder requestBuilder;
		if (isGenericSchema) {
			requestBuilder = new SQLBuilderForGenericSchema(orchestrationExecutionContext, field.getQueryCriteria(), SQLBuilder.StatementType.SELECT, (List<Object>) field.getData(), field);
		} else {
			requestBuilder = new SQLBuilderForNormalizedSchema(orchestrationExecutionContext, field.getQueryCriteria(), SQLBuilder.StatementType.SELECT, (List<Object>) field.getData());
		}
		final String sql = requestBuilder.buildSelectSql();
		final Collection<Object> columns = requestBuilder.getSelectColumns();

		RowMapper<List<Object>> artifactFieldsMapper = new RowMapper<List<Object>>() {
			@Override public List<Object> mapRow(ResultSet resultSet, int i) throws SQLException {
				List<Object> turbineTuple = new ArrayList<>();
				for (Object columnName : columns) {
					turbineTuple.add(resultSet.getObject((String) columnName));
				}
				return turbineTuple;
			}
		};
		return jdbcTemplate.query(sql, artifactFieldsMapper);
	}

	private ErrorResponse createErrorResponse(ErrorCode code, String message) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(code.name());
		errorResponse.setMessage(message);
		return errorResponse;
	}

	private boolean isGenericSchema(Map<String, String> customAttributes) {
		LOGGER.info("**** isGenericSchemaAttrValue ****");
		String isGenericSchemaAttrValue = customAttributes.get("IS_GENERIC_SCHEMA");
		if ("TRUE".equalsIgnoreCase(isGenericSchemaAttrValue)) {
			return true;
		}
		return false;
	}

}
