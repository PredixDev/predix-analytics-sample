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

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.jolbox.bonecp.BoneCPDataSource;

class DataSourceConfig {

	private final static Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

	@Configuration
	@Profile("cloud")
	static class CloudConfiguration {

		@Bean
		public Cloud cloud() {
			return new CloudFactory().getCloud();
		}

		@Bean
		public DataSource dataSource() {
			return cloud().getSingletonServiceConnector(DataSource.class, null);
		}

	}

	@Configuration
	@Profile("default")
	static class LocalConfiguration {

		@Autowired
		private Environment environment;

		@Bean
		public DataSource dataSource() {
			BoneCPDataSource dataSource = new BoneCPDataSource();
			setDataSourceProperties(dataSource, environment);
			LOGGER.info("Using BoneCP DataSource");
			return dataSource;
		}

	}

	private static void setDataSourceProperties(BoneCPDataSource dataSource, Environment environment) {
		LOGGER.info("Initializing DataSourceProperties - default profile");
		dataSource.setDriverClass(environment.getRequiredProperty("turbine.connector.db.driver"));
		dataSource.setJdbcUrl(environment.getRequiredProperty("turbine.connector.db.url"));
		dataSource.setUsername(environment.getRequiredProperty("turbine.connector.db.username"));
		dataSource.setPassword(environment.getRequiredProperty("turbine.connector.db.password"));
		LOGGER.info("BoneCP Configuration (default) : " + dataSource);
	}

}
