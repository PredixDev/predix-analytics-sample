-- Copyright (c) 2015 - 2016 General Electric Company. All rights reserved.

-- The copyright to the computer software herein is the property of
-- General Electric Company. The software may be used and/or copied only
-- with the written permission of General Electric Company or in accordance
-- with the terms and conditions stipulated in the agreement/contract
-- under which the software has been supplied.

-- ****************************************************
--  DDL FOR TABLE ANALYTIC CATALOG
-- ****************************************************

CREATE TABLE Sensor_Data (
    ID SERIAL NOT NULL,
	asset_id varchar(250) NOT NULL,
	field_id varchar(250) NULL,
	data_value real NULL,
	recorded_at timestamp default CURRENT_TIMESTAMP,
	updated_at timestamp default CURRENT_TIMESTAMP
);

--------------------------------------------------------
--  Constraints for Table ANALYTIC CATALOG
--------------------------------------------------------
ALTER TABLE Sensor_Data ADD CONSTRAINT PK_Sensor_Data PRIMARY KEY ( ID );