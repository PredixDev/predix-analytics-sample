# Demo Adder Multi-step Orchestration
    - The demo adder analytic has been modified to take as input a trained model. This model is in the form of a threshold value. The addition is performed and if the result is greater than the threshold value,
      a -1 value will be written to the output.
	- The first step adds KW and vibration tag values and writes output to bearing temperature tag in timeseries.
	- The second step adds bearing temperature from first step output with vibration tag value and writes output to bearing temperature final tag in timeseries.
	
# File list
| File/Directory | Description |
| -------------- | ----------- |
| InitialData | The data that needs to be loaded into Timeseries to support the orchestration and the asset group and tag map queries that will used by the runtime.|
| Orchestration | The orchestration configuration files. |

# Steps to load and run the orchestration

1. Load the data into time series
	- Use the websocket connection in the Predix Tool Kit (basic: https://predix-starter.run.aws-usw02-pr.ice.predix.io/#!/wsClient) to load the sample data into your Predix Timeseries instance.
		1. log in as a user from your UAA
		2. use Time series ingest to load the data from the following files in the InitialData folder
			- rawTimeSeriesData_KW.json
			- rawTimeSeriesData_vibration.json
			- predix-zone-id is your Time series guid (zone id/instance id)
			1. open the socket
			2. use the contents of rawTimeSeriesData_KW.json as the request body and send the message
			3. repeat for the rest of the timeseries data files listed above
			4.  close the socket
		3.  use get timeseries tag values request from validateDataLoaded folder in the postman collection to verify that timeseries data has been loaded.

2. Load the Timeseries Demo Adder with model Analytic to your Analytics Catalog
	1. get the timeseries-adder-java code from https://github.com/PredixDev/predix-analytics-sample/tree/master/analytics/demo-timeseries-adder-with-model/demo-timeseries-adder-with-model-java
	2. build it (mvn clean install)
	3. use the requests from addAnalytics folder in the postman collection or the UI to create the catalog entry and upload the jar and template file  (see instructions for adding an analytic to the catalog)
	4. update Orchestration/TwoStepOrchestration.bpmn20.xml to reference this analytic
	 	*  update  analyticInstanceId, analyticName, and analyticVersion in the demoAdder serviceTask with the catalog entry instance id, entry name and version
	5. test the analytic (this will deploy it) (see Predix IO Analytics Catalog docs for instructions on deploying an analytic) (sample data for testing the analytic is included in the analytic's github repository)

3. Load the orchestration configuration
	1. create the orchestration entry using the Create Orchestration Configuration Entry request in addOrchestrationConfiguration folder in the postman collection.
	2. validate the bpmn using the Validate Orchestration request in addOrchestrationConfiguration folder in the postman collection.
		* the bpmn file should have been updated with the catalog entry id for the analytic (Both steps use same catalog entry id)
		* the expected response should appear as show below (the analyticIds will be the catalog entry ids from the bpmn)
		* if the response does not contain status "200" for each analytic, the analytic is not running or the runtime is not set up properly.  Redeploy the analytic and validate the runtime setup. 
<pre>
{
  "analyticValidationStatusMap": {
    "http://analyticId.grc-apps.svc.ice.ge.com/api/v1/analytic/execution": "200",
    "http://analyticId.grc-apps.svc.ice.ge.com/api/v1/analytic/execution": "200"
  },
  "id": "<responseId>"
}
</pre>
	3. upload the bpmn using Create Orchestration BPMN Artifact request in the addOrchestrationConfiguration postman folder
	4. upload the first step's port to field map using Create Orchestration Port to Field Map Artifact request in the addOrchestrationConfiguration postman folder after updating the request as follows:
		* fill in the orchestrationEntryId
		* set the name to the step name from the bpmn (sid-10001)
		* select the Orchestration/step1-portToFieldMap.json
	5. upload the second step's port to field map using Create Orchestration Port to Field Map Artifact request in the addOrchestrationConfiguration postman folder after updating the request as follows:
		* fill in the orchestrationEntryId
		* set the name to the step name from the bpmn (sid-10002)
		* select the Orchestration/step2-portToFieldMap.json
	6. verify that the configuration has been loaded using the Get Artifact Metadata for an Orchestration Configuration request in the addOrchestrationConfiguration postman folder
		* update the <orchestration entry id> in the url with the id from step 8.i above
		* the response should contain entries for the bpmn and 2 port to field maps
	7. upload the threshold model using the postman Upload Model request in the addOrchestrationConfiguration postman folder after updating the request as follows:
		* set modelKey to 'model-key'
		* select the thresholdModel.json file in the Orchestrations directory
	8. validate that the models have been uploaded with the Get Orchestrations Models in the addOrchestrationConfiguration postman folder

4. Run the orchestration
	* use the postman Run Orchestration request in the postman folder after updating the request as follows:
		* put the following json in the request body.  Updating the orchestrationEntryId with the orchestration entry id from step 3.i above.
<pre>
{
     "orchestrationConfigurationId": "<orchestrationEntryId>",
     "assetId": null,
     "assetGroup": null,
     "assetDataFieldsMap": {"KW": "KW", "vibration": "vibration", "bearing_temperature" : "bearing_temperature", "bearing_temperature_final" : "bearing_temperature_final"},
     "modelGroupKey":"model-key"
 }
</pre> 
	* note down the orchestration request id in the response

5. View the orchestration status using the Orchestration Status request in the postman folder.
	* replace orchestrationRequestId in the uri with the orchestration request id from the response in step 4.

# Congratulations!  You have a 2 step orchestration running!
