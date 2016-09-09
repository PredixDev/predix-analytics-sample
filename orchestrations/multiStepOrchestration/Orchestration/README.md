## Step Miners Rule orchestration

	- The first step adds 2 vibration values together
	- The second step runs Miners Rule against 2 other sensors and the sum from the first step
	- The CDM for the analytic has one set of values for all assets in the Turbine asset group and a special set of values for asset with id Asset32
	
## File list
| File | Description |
| ---- | ----------- |
|DemoAdderPortToFieldMap.json|The port to field map for adding the vibration sensors.|
|MinersRulePortToFieldMap.json|The port to field map for running Miners Rule on the stress1 and stress2 sensors and vibeSum the life limits coming in as a model. |
|MinersRuleOrchestration.bpmn20.xml|The bpmn xml file for this orchestration|
|TurbineCDMModel.txt|the model file for Turbines|
|Asset32CDMModel.txt|the model file for asset32 (asset id: /assets/minersRuleOrch32-UUID)
