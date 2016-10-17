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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.predix.insight.dto.customdataprovider.Field;
import com.ge.predix.insight.dto.customdataprovider.OrchestrationExecutionContext;

public class SQLBuilderBase {

	protected final static Logger LOGGER = LoggerFactory.getLogger(SQLBuilderBase.class);

	protected final Pattern columnNamePattern = Pattern.compile("\\$\\{([a-zA-Z0-9_]+)\\}");

	protected Object queryCriteria;
	protected SQLBuilder.StatementType statementType;
	protected OrchestrationExecutionContext orchestrationExecutionContext;
	protected List<Object> selectColumns;
	protected List<Object> dataValues;
	protected Field field;

	protected String getAssetId() {

		String assetId = this.orchestrationExecutionContext.getAssetId();
		if (assetId == null) {
			return "";
		} else {
			return assetId;
		}
	}

	protected List<Object> buildSelectColumns() {
		//noinspection unchecked
		List<String> columns = (List<String>) ((Map) queryCriteria).get("columns");
		List<Object> selectColumns = new ArrayList<>();
		for (Object column : columns) {
			selectColumns.add(column);
		}
		return selectColumns;
	}

	protected String getMapData(Map eachCondition, String key) {
		return (String) eachCondition.get(key);
	}

	public List<Object> getSelectColumns() {
		return selectColumns;
	}

	protected Collection<SelectCondition> buildConditions() {
		//noinspection unchecked
		List<Map> conditionList = (List<Map>) ((Map) queryCriteria).get("conditions");
		if (conditionList != null) {
			Collection<SelectCondition> conditions = new ArrayList<>();
			for (Map eachCondition : conditionList) {
				String value = getMapData(eachCondition, "value");
				value = value.replaceAll("[$][{]ASSET_ID[}]", getAssetId());
				conditions.add(new SelectCondition(getMapData(eachCondition, "key"),
					getMapData(eachCondition, "relation"), value, getMapData(eachCondition, "valueType")));
			}
			return conditions;
		}
		//noinspection unchecked
		return Collections.EMPTY_LIST;
	}

	public String buildSelectSql() {
		selectColumns = buildSelectColumns();
		Collection<SelectCondition> conditions = buildConditions();
		String table = (String) ((Map) queryCriteria).get("table");

		String query = "select" + " " +
			StringUtils.join(selectColumns, ", ") +
			" from " + table + " where " +
			StringUtils.join(conditions, " and ");

		LOGGER.info("select query : " + query);
		return query;
	}

	protected List<String> update(Collection<SelectCondition> conditions, String table) {
		List<String> whereFieldsList = new ArrayList<>();
		int index = -1;
		for (SelectCondition selectCondition : conditions) {
			index++;
			CharSequence value = (CharSequence) selectCondition.value;
			Matcher matcher = columnNamePattern.matcher(value);
			if (matcher.matches()) {
				String group = matcher.group(1);
				whereFieldsList.add(group);
			}
		}

		List<String> updateStatements = new ArrayList<>();
		for (Object value : dataValues) {
			StringBuilder updateStmt = new StringBuilder();
			Collection<SelectCondition> newConditions = new ArrayList<>();
			updateStmt.append("update ").append(table).append(" set ");
			List listValue = (List) value;
			for (int i = 0, selectColumnsSize = selectColumns.size(); i < selectColumnsSize; i++) {
				Object column = selectColumns.get(i);
				if (!whereFieldsList.contains(column)) {
					updateStmt.append(column).append("=").append(listValue.get(i)).append(",");
				}
				if (whereFieldsList.contains(column)) {
					for (SelectCondition selectCondition : conditions) {
						SelectCondition newCondition = new SelectCondition(selectCondition.key, selectCondition.relation, selectCondition.value, selectCondition.valueType);
						newConditions.add(newCondition);
						Matcher matcher = columnNamePattern.matcher((CharSequence) selectCondition.value);
						if (matcher.matches()) {
							newCondition.value = listValue.get(i);
						}
					}
				}
			}
			updateStmt.deleteCharAt(updateStmt.lastIndexOf(","));
			updateStmt.append(" where ").append(StringUtils.join(newConditions, " and "));
			String updateSql = updateStmt.toString();
			LOGGER.info("update SQL : " + updateSql);
			updateStatements.add(updateSql);
		}
		return updateStatements;
	}

	protected static class SelectCondition {

		protected String relation;
		protected Object value;
		protected String key;
		protected String valueType;

		public SelectCondition(String key, String relation, Object value, String valueType) {
			this.relation = relation;
			this.value = value;
			this.key = key;
			this.valueType = valueType;
		}

		public String toString() {
			Object newValue = this.value;
			if ("string".equalsIgnoreCase(valueType)) {
				newValue = "'" + this.value + "'";
			}
			if ("timestamp".equalsIgnoreCase(valueType)) {
				newValue = "to_timestamp(" + newValue + "::double precision/1000)";
			}
			return " " + this.key + " " + this.relation + " " + newValue;
		}
	}
}
