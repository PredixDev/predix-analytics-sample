# Single Step Orchestration Sample
---
## Overview
---
This repository contains sample configuration, data and postman files for creating and running an orchestration in your instance of the Predix Analytics Runtime as per the 'Running an Orchestration Using Predix Time Series Tags' roadmap (https://www.predix.io/docs#ogUlV1fl).  The orchestration runs the demo-timeseries-adder-java analytic.  It take 2 sets of (aligned) timeseries data as input and produces their row-wise sum and writes the output to a new Timeseries tag.
## Files in the repository
Sample data files:

* data files with the timeseries inputs for the two addends,
* the expected output and 
* sample data files for validating that the demo-timeseries-adder-java analytic has been properly deploy to your Predix Analytics Catalog.

A postman collections containing requests for 
* getting a user token
* loading the orchestration configuration
* running the orchestration

Configuration files for the sample orchestration

## Usage
---
Follow the instructions below to set up the orchestration and trigger its execution in your Predix Analytics environment. 
### Task Roadmap Prerequisites
* Create subscriptions to: 
    * Predix Analytics Catalog, 
    * Predix Analytics Runtime,
    * Predix Analytics UI, 
    * Predix Timeseries and 
    * Predix UAA

    (see the Getting Started instructions for each service in https://www.predix.io/docs#OqtMIsCd)
    
* Use the websocket connection in the Predix Tool Kit (for basic:https://predix-starter.run.aws-usw02-pr.ice.predix.io/#!/wsClient, for select: ????) to load the sample data into your Predix Timeseries instance.
    1. log in as a user from your UAA
    2. use Time series ingest to load the data from supportingDataFiles/time-series-tag-A-data.json and suportingDataFiles/time-series-tag-B-data.json:
        *  predix-zone-id is your Time series guid (zone id/instance id)
        1. open the socket
        2. use supportingDataFiles/time-series-tag-A-data.json as the request body and send the message
        3.  use supportingDataFiles/time-series-tag-B-data.json as the request body and send the message
        4.  close the socket
        5.  use Time series query, request: Time Bounded Request to verify that tag-A and tag-B have been loaded.  Request bodies can be found in:
            * supportingDataFiles/tag-A-time-bounded-request.json
            * supportingDataFiles/tag-B-time-bounded-request.json

* Setup Postman
     * Install Postman (from the chrome web store, https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en)
     * Open Postman and import 'SingleStepOrchestrationDemoUsingTagMap.postman_collection'
     * Define the Postman environment (AnalyticsDemo) 
        * **uaa_uri**: the uri for your UAA instance
        * **uaa_client_id**: the uaa client id for your Analytics and Time Series instances
        * **uaa_authorization_id**: the base64 encoding of <uaa client id>:<uaa client secret> from your UAA instance
        * **userId**: a user id from the UAA instance
        * **userPassword**: the password for the userId
        * **user_token**: a valid UAA token from your Predix UAA service for your Predix Analytics and Time Series instances.  
            * Use the user token returned on the log in when you ingested tag-A and tag-B into Predix Time Series or use 'Request user token' from the postman collection to get a new user token 
        * **catalog_uri**, **config_uri**, **execution_uri**: from running 'cf env <your app>' after binding your Predix Analytics instances to <your app>. (See the Getting Started guides)
        * **runtime-zone-id**: the guid for your Predix Analytics Runtime
* Deploy the demo-timeseries-adder-java  analytic (https://github.com/PredixDev/predix-analytics-sample/tree/master/analytics/demo-timeseries-adder-java) to your Analytics Catalog (using your Analytics UI)
    1. add the demo-timeseries-adder-java.jar and demo-timeseries-adder-template.json as 'Executable' and 'template' attachments
    2. deploy and test the analytic using supportingDataFiles/analytic-input-for-demo-timeseries-adder.json
    
## Task Roadmap Instructions
1.  Create the orchestration configuration file from orchestration/ConfigurationFiles/orchestration-workflow.xml by updating the following entries in line 19: 
    * **analytic Catalog Entry Id** is catalog's guid for the analytic. The guid can be found on the URI for the Analytic Detail as follows:  in the Analytic UI, go to the Analytic Detail page for the demo adder analytic, the guid is after  '.../view/' in the uri.
    * **Analytic Name** is the name you entered when you created the analytic in the Analytics Catalog
    * **Analytic Version** is the version you entered when you created the analytic in the Analytics Catalog 
    
2. Validate the Orchestration Workflow File - use the 'Workflow Validation' request from the Postman collection
    * choose the updated bpmn.xml file in the Body of the request
    * submit the request and look for 'Status: 200 OK'
    
3. Create a port-to-field Map - use orchestrationConfigurationFiles/port-to-field-map-for-demoTimeseriesAdder.json.  You can use this file as is, no changes are needed.

4. Create an Orchestration Configuration Entry - use the following Postman requests
    1. 'Create Orchestration Configuration Entry' request.  Note down the 'id' in the response - this is the orchestration entry id.
    2. 'Upload Orchestration Workflow File' request - change <orchestration entry id> to the orchestration entry id from step i and chose the orchestrationConfigurationFiles/orchestration-workflow.xml in the body of the request before sending it.
    3. 'Upload port-to-field Map for Orchestration Step' request - change <orchestration entry id> to the orchestration entry id from step i and chose the orchestrationConfigurationFiles/port-to-field-map-for-demoTimeseriesAdder.json before sending the request.
    
5. Run the orchestration using the 'Run Single Step Orchestration Using Tag Map' request - change <orchestration entry id> to the orchestration entry  id from the prior step before submitting the request.

---
## Congratulations! 
You have just created and run an orchestration.

---
# Explaining the file contents
The following highlights important entries in the sample files and how values are related across the files.
## Loading values into Time Series 
time-series-tag-A-data.json contains:

'''
{
  "messageId": "1453338376222",
  "body": [
    {
      **"name": "tag-A"**,
      "datapoints": [
        [
          1453338376200,
          1,
          3
        ],
        [
          1453338376201,
          2,
          3
        ],
  ...
'''

Note: the **\"name\" : \"tag-A\"**  part of the json object.  This causes Time Series to store the data under the **tag-A** key when Time Series ingests it.

## Mapping data values from tag-A and tag-B to the analytic input json 

The port to field map file ( port-to-field-map-for-demo-TimeseriesAdder.json), defines the input as coming from fieldIds **temperature sensor** and **vibration sensor** (see below). And the orchestration request (in postman) maps **temperature sensor** to **tag-A** and **vibration sensor** to **tag-B**.  So **tag-A's** values are read for the **temperature sensor** and **tag-B's** values are read for the **vibration sensor**. 

port to field map entry:

'''
"inputMaps": [
    {"valueSourceType": "DATA_CONNECTOR",
    "fullyQualifiedPortName": "data.time_series.numberArray1",
    **"fieldId": "temperature sensor"**,
    "queryCriteria": {"start": 0, "end": -1},
    "dataSourceId": "PredixTimeSeries"
    },
    {"valueSourceType": "DATA_CONNECTOR",
    "fullyQualifiedPortName": "data.time_series.numberArray2",
    **"fieldId": "vibration sensor"**,
    "queryCriteria": {"start": 0, "end": -1},
    "dataSourceId": "PredixTimeSeries"
    }
],
'''

orchestration run request:

'''
"assetDataFieldsMap": {
    **"temperature sensor": "tag-A"**,
    **"vibration sensor": "tag-B"**,
    "demo sum": "tag-C"
},
'''

The port to field map entries:

**\"fullyQualifiedPortName\": \"data.time_series.numberArray1\"** and 
**\"fullyQualifiedPortName\": \"data.time_series.numberArray2\"**

tell the processing to put the values in the input json object as:

'''
{"data" : 
    {"time_series" : 
        {"numberArray1" : [<values from tag-A>],
         "numberArray2" :  [<values from tag-A>]
         }
    }
}
'''

## Mapping the output to Time Series tag-C
This is essentially the same as the input mapping.  The orchestration run request maps **tag-C** with **demo sum**.  The port to field map maps **demo sum** to the value from the json **data.time_series.sum** object in the analytic output.

## Associating the port to field map with the orchestration step

When a port to field map is uploaded to the system, the request will contain a **name** field (see the request in postman).  This value must match the **\<serviceTask ... id="\<value\>" ... \\>** in the orchestration's bpmn specification.


