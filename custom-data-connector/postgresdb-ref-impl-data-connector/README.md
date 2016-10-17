#Reference Data Connector for PostgreSQL

Custom Data Connectors allow the Predix Analytics service to read and write the analytic data from the external data sources.
This implementation is a reference implementation for reading or writing data to a Postgres data source.

The service must implement the following three Analytics Runtime APIs.

1. HEALTHCHECK (/api/v1/analytics/customdata/healthcheck) — used to check to the health of your Custom Data Connector service before executing an orchestration.
2. READ (/api/v1/analytics/customdata/read) — used to read data passed to the analytic (input data) from the external data source.
3. WRITE (/api/v1/analytics/customdata/write) — used to write the data produced by an analytic (output data) to the external data source.

The reference implementation includes support for the read, write and healthcheck APIs.

Perform following steps to install the Reference PostgreSQL Data Connector:

1. mvn clean install
2. In postgresdb-ref-impl-data-connector/pom.xml, configure uaa_client_id, uaa_client_secret and uaa_check_token_url properties.
3. mvn spring-boot:run to run the Reference PostgreSQL Data Connector. This will also create the normalized schema and the generic schemas and insert required data into it. The schema and data files can be found under
postgresdb-ref-impl-data-connector/src/main/resources folder.  
The default port is 18888. This can be changed through the custom-connector-application.properties file under
postgresdb-ref-impl-data-connector/src/main/resources folder. Also, the Postgres database connection details can be  specified in this file as:  
```
turbine.connector.db.url=jdbc:postgresql://localhost:5432/analytics?searchpath=turbine
turbine.connector.db.username=turbine
turbine.connector.db.password=turbine123
```

This reference data connector service has the implementations for generic schema and normalized schema definitions.

In a generic schema definition, we have “field_id" and “data_value” columns to store the sensor data.  The data values
for different fields are stored in the same column “data_value”.  The “field_id” column differentiates the type of the
 data value.

In a normalized schema definition, we have a column to store each type of sensor data.  For example, “exhaust_gas_temp”
and “vibration”.

The selection of which schema is to be used can be done through the custom attributes in orchestration
execution request as specified in the section below.

##Generic Schema (sensor_data)


Schema for sensor_data table:
```
id serial NOT NULL,
asset_id character varying(250) NOT NULL,
field_id character varying(250),
data_value real,
recorded_at timestamp without time zone DEFAULT now(),
updated_at timestamp without time zone DEFAULT now(),
```


Example Custom Data Connector Read Request would be as follows:

Data Connector's read API endpoint:
```
POST /api/v1/analytics/customdata/read
```

Data Connector's read API request body:
```
{
    "field": [
        {
            "fieldId": "bearing temperature final",
            "fullyQualifiedPortName": "a.b.c.somefield",
            "dataType": null,
            "engUnit": null,
            "data": null,
            "queryCriteria": {
                "columns": [
                    "recorded_at",
                    "data_value"
                ],
                "table": "sensor_data",
                "conditions": [
                    {
                        "key": "asset_id",
                        "value": "${ASSET_ID}",
                        "valueType": "string",
                        "relation": " = "
                    },
                    {
                        "key": "recorded_at",
                        "value": "current_timestamp",
                        "valueType": "none",
                        "relation": " < "
                    },
                    {
                        "key": "field_id",
                        "value": "bearing temperature final",
                        "valueType": "string",
                        "relation": " = "
                    }
                ]
            },
            "errorResponse": null
        }
    ],
    "customAttributes": {
        "IS_GENERIC_SCHEMA": "TRUE"
    },
    "systemAttributes": null,
    "orchestrationExecutionContext": {
        "assetId": "<asset_Id>",
        "orchestrationConfigurationID": null,
        "orchestrationExecutionRequestID": null,
        "analyticId": null,
        "analyticName": null,
        "analyticVersion": null,
        "analyticExecutionRequestID": null
    },
    "dataSourceId": "Postgres Reference External Data Connector"
}
```


The corresponding select query would be as follows:
```
 select recorded_at, data_value from sensor_data where  asset_id  =  '@asset_id' and  recorded_at  <  current_timestamp and  field_id  =  'bearing temperature final'
```

Read Response:
```
{
    "field": [
        {
            "fieldId": "bearing temperature final",
            "fullyQualifiedPortName": "a.b.c.somefield",
            "data": [
                [
                    1473366334967,
                    2632.99
                ],
                [
                    1473366334968,
                    4732.99
                ],
                [
                    1473366334969,
                    6832.99
                ]
            ],
            "queryCriteria": {
                "columns": [
                    "recorded_at",
                    "data_value"
                ],
                "table": "sensor_data",
                "conditions": [
                    {
                        "key": "asset_id",
                        "value": "${ASSET_ID}",
                        "valueType": "string",
                        "relation": " = "
                    },
                    {
                        "key": "recorded_at",
                        "value": "current_timestamp",
                        "valueType": "none",
                        "relation": " < "
                    },
                    {
                        "key": "field_id",
                        "value": "bearing temperature final",
                        "valueType": "string",
                        "relation": " = "
                    }
                ]
            }
        }
    ],
    "orchestrationExecutionContext": {
        "assetId": "/assets/37-babd4023-2bfb-450e-a63d-1025c81636f5"
    }
}
```

Custom Data Connector Write Request would be as follows:

Data Connector's write API endpoint:
```
POST /api/v1/analytics/customdata/write
```

Data Connector's write API request body:
```
{
  "field": [
    {
      "fieldId": "bearing temperature final",
      "fullyQualifiedPortName": "data.time_series.sum",
      "dataType": "DOUBLE_ARRAY",
      "engUnit": "Celsius",
      "data": [
        [
          1473366334967,
          2632.99,
          "2"
        ],
        [
          1473366334968,
          4732.99,
          "2"
        ],
        [
          1473366334969,
          3588.66,
          "2"
        ]
      ],
      "queryCriteria": {
        "columns": [
          "field_id",
          "recorded_at",
          "data_value"
        ],
        "table": "sensor_data"
      },
      "errorResponse": null
    }
  ],
  "customAttributes": {
    "IS_GENERIC_SCHEMA": "TRUE"
  },
  "systemAttributes": null,
  "orchestrationExecutionContext": {
    "assetId": "<asset_id>",
    "orchestrationConfigurationID": "<Orchestration Configuration Id>",
    "orchestrationExecutionRequestID": "<Orchestration Request Id>",
    "analyticId": null,
    "analyticName": null,
    "analyticVersion": null,
    "analyticExecutionRequestID": null
  },
  "dataSourceId": "Postgres Reference External Data Connector"
}
```

The corresponding SQL insert statement would be like follows:
```
insert into sensor_data(asset_id, field_id, recorded_at, data_value) values( '@asset_id', 'bearing temperature final', to_timestamp(1473366334967::double precision/1000), 2632.99 )
```

Write Response:
```
{
  "field": [
    {
      "fieldId": "bearing temperature final",
      "fullyQualifiedPortName": "data.time_series.sum",
      "dataType": "DOUBLE_ARRAY",
      "engUnit": "Celsius",
      "data": [
        [
          1473366334967,
          2632.99,
          "2"
        ],
        [
          1473366334968,
          4732.99,
          "2"
        ],
        [
          1473366334969,
          6832.99,
          "2"
        ]
      ],
      "queryCriteria": {
        "columns": [
          "field_id",
          "recorded_at",
          "data_value"
        ],
        "table": "sensor_data"
      }
    }
  ],
  "orchestrationExecutionContext": {
    "assetId": "<asset_id>",
    "orchestrationConfigurationID": "<Orchestration Configuration Id>",
    "orchestrationExecutionRequestID": "<Orchestration Request Id>"
  }
}
```

##Normalized Schema (turbine_compressor)

Schema for turbine_compressor table:
```
id serial NOT NULL,
asset_id character varying(36) NOT NULL,
exhaust_gas_temp real,
vibration real,
recorded_at timestamp without time zone,
```

Custom data connector Read Request:

Data Connector's read API endpoint:
```
POST /api/v1/analytics/customdata/read
```

Data Connector's read API request body:
```
{
	"field":[
		{
			"fieldId":"vibration",
			"fullyQualifiedPortName":"a.b.c.somefield",
			"queryCriteria": {

				"columns":["recorded_at", "vibration"],
				"table":"turbine_compressor",
				"conditions" : [
					{
						"key":"asset_id",
						"value":"ASSET1",
						"valueType":"string",
						"relation":" = "
					},
					{
						"key":"recorded_at",
						"value":"current_timestamp",
						"valueType":"none",
						"relation":" < "
					}
				]
			}

		},
		{
			"fieldId":"exhaust_gas_temp",
			"fullyQualifiedPortName":"a.b.c.somefield",
			"queryCriteria": {

				"columns":["recorded_at", "exhaust_gas_temp"],
				"table":"turbine_compressor",
				"conditions" : [
					{
						"key":"asset_id",
						"value":"ASSET1",
						"valueType":"string",
						"relation":" = "
					},
					{
						"key":"recorded_at",
						"value":"current_timestamp",
						"valueType":"none",
						"relation":" < "
					}
				]
			}
		}

	],
	"orchestrationExecutionContext" : {
		"assetId":"ASSET1"
	}
}
```


The corresponding select query would be as follows:
```
select recorded_at, vibration from turbine_compressor where  asset_id  =  '@asset_id' and  recorded_at  <  current_timestamp
select recorded_at, vibration from turbine_compressor where  asset_id  =  '@asset_id' and  recorded_at  <  current_timestamp
```

Read response:
```
{
  "field": [
    {
      "fieldId": "vibration",
      "fullyQualifiedPortName": "a.b.c.somefield",
      "data": [
        [
          1398445998435,
          1000.33
        ],
        [
          1398445999436,
          3000.33
        ],
        [
          1398446000437,
          2000.33
        ]
      ],
      "queryCriteria": {
        "columns": [
          "recorded_at",
          "vibration"
        ],
        "table": "turbine_compressor",
        "conditions": [
          {
            "key": "asset_id",
            "value": "ASSET1",
            "valueType": "string",
            "relation": " = "
          },
          {
            "key": "recorded_at",
            "value": "current_timestamp",
            "valueType": "none",
            "relation": " < "
          }
        ]
      }
    },
    {
      "fieldId": "exhaust_gas_temp",
      "fullyQualifiedPortName": "a.b.c.somefield",
      "data": [
        [
          1398445998435,
          200.42
        ],
        [
          1398445999436,
          120.39
        ],
        [
          1398446000437,
          110.18
        ]
      ],
      "queryCriteria": {
        "columns": [
          "recorded_at",
          "exhaust_gas_temp"
        ],
        "table": "turbine_compressor",
        "conditions": [
          {
            "key": "asset_id",
            "value": "ASSET1",
            "valueType": "string",
            "relation": " = "
          },
          {
            "key": "recorded_at",
            "value": "current_timestamp",
            "valueType": "none",
            "relation": " < "
          }
        ]
      }
    }
  ],
  "orchestrationExecutionContext": {
    "assetId": "ASSET1"
  }
}
```

Custom Data Connector Write Request would be as follows:

Data Connector's write API endpoint:
```
POST /api/v1/analytics/customdata/write
```

Data Connector's write API request body:
```
{
  "field": [
    {
      "fieldId": "vibration",
      "fullyQualifiedPortName": "a.b.c.somefield",
      "data": [
        [
          1473366334970,
          1999.33
        ]

      ],
      "queryCriteria": {

	      "columns": [
	        "recorded_at",
	        "vibration"
	      ],
	      "table": "turbine_compressor",
	      "conditions": []
	    }

    }
  ],
  "orchestrationExecutionContext": {
    "assetId": "ASSET1"
  }
}
```

The corresponding SQL insert statement would be like follows:
```
insert into turbine_compressor(asset_id, recorded_at, vibration) values( '@asset_id', to_timestamp(1473366334969::double precision/1000), 1000.33)
```

Write Response:

```
{
  "field": [
    {
      "fieldId": "vibration",
      "fullyQualifiedPortName": "a.b.c.somefield",
      "data": [
        [
          "'ASSET1'",
          "to_timestamp(1473366334970::double precision/1000)",
          1999.33
        ]
      ],
      "queryCriteria": {
        "columns": [
          "recorded_at",
          "vibration"
        ],
        "table": "turbine_compressor",
        "conditions": []
      }
    }
  ],
  "orchestrationExecutionContext": {
    "assetId": "ASSET1"
  }
}
```
##Port-to-Field Map
When this data connector service is integrated with Predix Analytics service, the port-to-field maps of the orchestrations should be configured to point to this datasource.  The port-to-field map for the orchestration which needs to use a custom data connector would be specified
as follows:

```
{
  "analyticName": "java-timeseries-demo-adder",
  "analyticVersion": "1.0",
  "orchestrationStepId": "sid-10001",
  "iterations": [
    {
      "inputMaps": [
        {
          "valueSourceType": "DATA_CONNECTOR",
          "fullyQualifiedPortName": "data.time_series.numberArray1",
          "fieldId": "KW",
          "queryCriteria": {
            "columns": [
              "recorded_at",
              "data_value"
            ],
            "table": "sensor_data",
            "conditions": [
              {
                "key": "asset_id",
                "value": "${ASSET_ID}",
                "valueType": "string",
                "relation": " = "
              },
              {
                "key": "recorded_at",
                "value": "current_timestamp",
                "valueType": "none",
                "relation": " < "
              },
              {
                "key": "field_id",
                "value": "KW",
                "valueType": "string",
                "relation": " = "
              }
            ]
          },
          "engUnit": "kw",
          "required": true,
          "dataSourceId": "Postgres Reference External Data Connector"
        },
        {
          "valueSourceType": "DATA_CONNECTOR",
          "fullyQualifiedPortName": "data.time_series.numberArray2",
          "fieldId": "vibration",
          "queryCriteria": {
            "columns": [
              "recorded_at",
              "data_value"
            ],
            "table": "sensor_data",
            "conditions": [
              {
                "key": "asset_id",
                "value": "${ASSET_ID}",
                "valueType": "string",
                "relation": " = "
              },
              {
                "key": "recorded_at",
                "value": "current_timestamp",
                "valueType": "none",
                "relation": " < "
              },
              {
                "key": "field_id",
                "value": "vibration",
                "valueType": "string",
                "relation": " = "
              }
            ]
          },
          "engUnit": "hertz",
          "required": true,
          "dataSourceId": "Postgres Reference External Data Connector"
        }
      ],
      "outputMaps": [
        {
          "fullyQualifiedPortName": "data.time_series.sum",
          "fieldId": "bearing temperature",
          "engUnit": "Celsius",
          "dataSourceId": "Postgres Reference External Data Connector",
          "queryCriteria": {

            "columns": [
              "field_id",
              "recorded_at",
              "data_value"
            ],
            "table": "sensor_data"
          }
        }
      ]
    }
  ]
}
}
```

##Orchestration Execution Request
The payload for the orchestration Execution Request would be as follows:  
[Note the use of custom attributes to pass to the external data connector implementation. This would let the 
orchestration engine know whether to use the generic or the normalized schema]
```
{
    "orchestrationConfigurationId": "<Orchestration Configuration Id>",
    "assetId": null,
    "assetDataFieldsMap": null,
    "assetGroup": {
        "dataSourceId": "PredixAsset",
        "assetSelectionFilter": "/assets?filter=classification=/classifications/turbine:name=15sl-46606c64-619d-4db0-a059-bc2d879640ca<turbine_type"
    },
    "modelGroupKey": null,
    "dataSource": [
        {
            "dataSourceId": "Postgres Reference External Data Connector",
            "apiVersion": "v1",
            "baseUri": "http://localhost:18888"
        }
    ],
    "customAttributes": {
        "IS_GENERIC_SCHEMA": "TRUE"
    }
}
```

##Generating skeleton implementation

This reference implementation is based on the source code generated from the CustomDataProviderServiceImpl.wadl file located under /src/main/resources/wadl.  To generate the source code from the wadl file, please use ‘mvn clean install -P wadl2java’ command.  NOTE: Running this command would overwrite the existing add-on implementation with the generated code.  Please use with care.

The skeleton implementation can be generated with 'mvn clean install -P wadl2java’ command.  The generated source code includes:
* Data Transfer Objects
* Skeleton REST resource class