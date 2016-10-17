-- Copyright (c) 2015 - 2016 General Electric Company. All rights reserved.

-- The copyright to the computer software herein is the property of
-- General Electric Company. The software may be used and/or copied only
-- with the written permission of General Electric Company or in accordance
-- with the terms and conditions stipulated in the agreement/contract
-- under which the software has been supplied.


-- ****************************************************
--  DDL FOR TABLE ANALYTIC CATALOG
-- ****************************************************



CREATE TABLE TURBINE_COMPRESSOR (
  ID SERIAL NOT NULL,
	ASSET_ID varchar(36) NOT NULL,
	EXHAUST_GAS_TEMP real NULL,
	VIBRATION real NULL,
	RECORDED_AT timestamp NULL
);


--------------------------------------------------------
--  Constraints for Table ANALYTIC CATALOG
--------------------------------------------------------
ALTER TABLE TURBINE_COMPRESSOR ADD CONSTRAINT PK_TURBINE_COMPRESSOR PRIMARY KEY ( ID );

