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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ge.predix.insight.dto.customdataprovider.OrchestrationExecutionContext;

public class SQLBuilderForNormalizedSchema extends SQLBuilderBase implements SQLBuilder {

	public SQLBuilderForNormalizedSchema(OrchestrationExecutionContext orchestrationExecutionContext, Object queryCriteria, StatementType statementType, List<Object> dataValues) {
		this.orchestrationExecutionContext = orchestrationExecutionContext;
		this.queryCriteria = queryCriteria;
		this.statementType = statementType;
		this.dataValues = dataValues;
	}

	public SQLBuilderForNormalizedSchema(Object queryCriteria, StatementType statementType) {
		this.queryCriteria = queryCriteria;
		this.statementType = statementType;
	}

	protected String getMapData(Map eachCondition, String key) {
		return (String) eachCondition.get(key);
	}

	@Override public String[] buildInsertOrUpdateSql() {
		selectColumns = buildSelectColumns();
		Collection<SelectCondition> conditions = buildConditions();
		String table = (String) ((Map) queryCriteria).get("table");
		if (conditions.isEmpty()) {
			List<String> insertStatements = new ArrayList<>();
			// insert
			selectColumns.add(0, "asset_id");
			for (Object value : dataValues) {
				String insertStmt = "";
				List<Object> listValue = (List) value;
				listValue.set(0, "to_timestamp(" + listValue.get(0) + "::double precision/1000)");
				listValue.add(0, "'" + getAssetId() + "'");
				insertStmt = insertStmt + " insert into " + table + "(" + StringUtils.join(selectColumns, ", ") + ")" + " values( " + StringUtils.join(listValue, ", ") + " )";
				LOGGER.info("insert SQL: " + insertStmt);
				insertStatements.add(insertStmt);
			}
			return insertStatements.toArray(new String[0]);
		} else {
			// update
			List<String> updateStatements = update(conditions, table);
			return updateStatements.toArray(new String[0]);
		}
	}

}
