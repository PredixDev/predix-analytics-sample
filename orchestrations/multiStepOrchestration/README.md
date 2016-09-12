# Step Miners Rule orchestration
	-The first step adds 2 vibration values together
	-The second step runs Miners Rule against 2 other sensors and the sum from the first step
	-The CDM for the analytic has one set of values for all assets in the turbine AssetGroup and a special set of values for asset32
	
# File list
| File/Directory | Description |
| -------------- | ----------- |
| InitialData | The data that needs to be loaded into Asset and Timeseries to support the orchestration and the asset group and tag map queries that will used by the runtime.|
| Orchestration | The orchestration configuration files. |

# Steps to load and run the orchestration

1. load the asset model: 
	1. use the 'add asset data' request from the assetData folder in the postman collection.  
		- paste the contents of InitialData/asset-model.json into the body of the request
		- submit the request
	2. verify that the asset data was loaded by running the 'asset query for 2 step orchestration asset model' request in the validateDataLoaded folder in the postman collection.  The results should match the body of the 'add asset data' request
2. verify the asset group query
	- run the 'asset group query' request from validateDataLoaded folder in the postman collection. The response should contain:
<pre>
[
  {
    "uri": "/assets/minersRuleOrch32-UUID"
  },
  {
    "uri": "/assets/minersRuleOrch37-UUID"
  },
  {
    "uri": "/assets/minersRuleOrch38-UUID"
  }
]
</pre>
3. load the data into time series
	- Use the websocket connection in the Predix Tool Kit (basic: https://predix-starter.run.aws-usw02-pr.ice.predix.io/#!/wsClient) to load the sample data into your Predix Timeseries instance.
		1. log in as a user from your UAA
		2. use Time series ingest to load the data from the following files in the InitialData folder
			- asset32-bearing-temperature-timeseries-data.json
			- asset32-kw-timeseries-data.json
			- asset32-vibration1-timeseries-data.json
			- asset32-vibration-timeseries-data.json
			- asset37-bearing-temperature-timeseries-data.json
			- asset37-kw-timeseries-data.json
			- asset37-vibration1-timeseries-data.json
			- asset37-vibration-timeseries-data.json
			- asset38-bearing-temperature-timeseries-data.json
			- asset38-kw-timeseries-data.json
			- asset38-vibration1-timeseries-data.json
			- asset38-vibration-timeseries-data.json
            - asset32-CDM-timeseries-data.json
            - asset37-CDM-timeseries-data.json
            - asset38-CDM-timeseries-data.json
			-  predix-zone-id is your Time series guid (zone id/instance id)
			1. open the socket
			2. use the contents of asset32-bearing-temperature-timeseries-data.json as the request body and send the message
			3. repeat for the rest of the timeseries data files listed above
			4.  close the socket
		3.  use get timeseries tag values request from validateDataLoaded folder in the postman collection to verify that 32_bearing_temperature_36-UUID has been loaded.  The response should contain the same values as in asset32-bearing-temperature-timeseries-data.json
4. verify the tag map query: use the 'asset tag map query validation' request from validateDataLoaded folder in the postman collection.  The response should contain a list of (name, timeseries_tag_id) maps for asset38 as shown below.
<pre>
[
  {
    "name": "KW",
    "timeseries_tag_id": "38_KW_50-UUID"
  },
  {
    "name": "vibration",
    "timeseries_tag_id": "38_vibration_51-UUID"
  },
  {
    "name": "local Rh",
    "timeseries_tag_id": "38_local_rh_52-UUID"
  },
  {
    "name": "bearing temperature",
    "timeseries_tag_id": "38_bearing_temperature_53-UUID"
  },
  {
    "name": "currentCDM",
    "timeseries_tag_id": "38_CDM_54-UUID"
  },
  {
    "name": "vibration1",
    "timeseries_tag_id": "38_vibration1_55-UUID"
  }
]
</pre>
5. load the default tag map query. Use the 'create default tag map query' request in the postman collection
6. load the Timeseries Demo Adder to your Analytics Catalog
	1. get timeseries-adder-java from https://github.com/PredixDev/predix-analytics-sample/tree/master/analytics/demo-timeseries-adder-java
	2. build it (mvn clean install)
	3. use the requests from addAnalytics folder in the postman collection or the UI to create the catalog entry and upload the jar and template file  (see instructions for adding an analytic to the catalog)
	4. update Orchestration/MinersRuleOrchestration.bpmn20.xml to reference this analytic
	 	*  update  analyticInstanceId, analyticName, and analyticVersion in the demoAdder serviceTask with the catalog entry instance id, entry name and version
	 5. test the analytic (this will deploy it) (see Predix IO Analytics Catalog docs for instructions on deploying an analytic) (sample data for testing the analytic is included in the analytic's github repository)
7. load the Miners Rule v2 analytic to the catalog
	1. get Miners Rule v2 from https://github.com/PredixDev/predix-analytics-sample/tree/master/analytics/miners-rule
	2. build it (mvn clean install)
	3. use requests from addAnalytics folder in the postman collection or the UI to create the catalog entry and upload the jar and template file (see instructions for adding an analytic to the catalog)
	4. update Orchestration/MinersRuleOrchestration.bpmn20.xml to reference this analytic
	 	*  update analyticInstanceId, analyticName, and analyticVersion in the minersRule serviceTask with the catalog entry instance id, entry name and version
 	5.  deploy the analytic (you cannot directly test analytics that require trained models)  (see Predix IO Analytics Catalog docs for instructions on deploying an analytic)
8. load the orchestration configuration
	1. create the orchestration entry using the Create Orchestration Configuration Entry request in addOrchestrationConfiguration folder in the postman collection.
	2. validate the bpmn using the Validate Orchestration request in addOrchestrationConfiguration folder in the postman collection.
		* the bpmn file should have been updated with the catalog entry ids for the two analytics as instructed in steps 6 and 7 above
		* the expected response should appear as show below (the analyticIds will be the catalog entry ids from the bpmn)
		* if the response does not contain status "200" for each analytic, the analytic is not running or the runtime is not set up properly.  Redeploy the analytic and validate the runtime setup. 
<pre>
{
  "analyticValidationStatusMap": {
    "http://anlayticId.grc-apps.svc.ice.ge.com/api/v1/analytic/execution": "200",
    "http://analyticId.grc-apps.svc.ice.ge.com/api/v1/analytic/execution": "200"
  },
  "id": "<responseId>"
}
</pre>
	2. upload the bpmn using Create Orchestration BPMN Artifact request in the addOrchestrationConfiguration postman folder
	3. upload the demo adder port to field map using Create Orchestration Port to Field Map Artifact request in the addOrchestrationConfiguration postman folder after updating the request as follows:
		* fill in the orchestrationEntryId
		* set the name to the step name from the bpmn (demoAdder)
		* select the Orchestration/DemoAdderPortToFieldMap.json
	4. upload the miners rule port to field map using Create Orchestration Port to Field Map Artifact request in the addOrchestrationConfiguration postman folder after updating the request as follows:
		* fill in the orchestrationEntryId
		* set the name to the step name from the bpmn (minersRule)
		* select the Orchestration/MinersRulePortToFieldMap.json
	5. verify that the configuration has been loaded using the Get Artifact Metadata for an Orchestration Configuration request in the addOrchestrationConfiguration postman folder 
		* update the <orchestration entry id> in the url with the id from step 8.i above
		* the response should contain entries for the bpmn and 2 port to field maps
	6. upload the turbine cdm model using the postman Upload Miners Rule Model request in the addOrchestrationConfiguration postman folder after updating the request as follows:
		* set modelKey to 'turbine'
		* select the TurbineCDMModel.txt file in the Orchestrations directory
	7. upload the asset32 cdm model using the postman Upload Miners Rule Model request in the addOrchestrationConfiguration postman folder after updating the request as follows:
		* set modelKey to '/assets/minersRuleOrch32-UUID'
		* select the Asset32CDMModel.txt file in the Orchestrations directory
	8. validate that the models have been uploaded with the Get Orchestrations Models in the addOrchestrationConfiguration postman folder
		* the request should contain entries for the 2 asset models loaded in 8.vii and 8.viii
9. run the orchestration for turbines
	* use the postman Run Orchestration request in the postman folder after updating the request as follows:
		* put the following json in the request body.  Updating the orchestrationEntryId with the orchestration entry id from step 8.i above.
<pre>
{
     "orchestrationConfigurationId": "<orchestrationEntryId>",
     "assetGroup": {
         "dataSourceId": "PredixAsset",
         "assetSelectionFilter": "/assets?filter=classification=/classifications/turbine:uri=/assets/minersRuleOrch*&fields=uri"
     },
     "modelGroupKey":"turbine"
 }
</pre> 
	* note down the orchestration request id in the response
10. view the orchestration status using the Orchestration Status request in the postman folder.
	* replace orchestrationRequestId in the uri with the orchestration request id from the response in step 9

# Congratulations!  You have a 2 step orchestration running!
