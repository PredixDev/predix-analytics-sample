## Sample Orchestration Workflow Files

A collection of simple orchestration workflow files for use with Predix Analytics.

1. **[Orchestration with one Catalog analytic](OrchestrationWithOneAnalytic.xml)**
2. **[Orchestration with one third-party analytic](OrchestrationWithThirdPartyAnalytic.xml)**
3. **[Orchestration with one Catalog analytic and one third-party analytic](OrchestrationWithTwoAnalytics.xml)**

A Catalog analytic is one that has been uploaded to the Analytic Catalog and deployed into Cloud Foundry.

## Complete Sample Orchestrations

Sample orchestrations that include all supporting files, such as orchestration workflow (BPMN), port-to-field maps, input/output data, and Postman collections

1. **[One step orchestration](oneStepOrchestration):** An orchestration using a single timeseries demo adder to add 2 timeseries arrays
2. **[Multi step orchestration with Demo Adder](demoAdderMultiStepOrchestration):** An orchestration that adds 3 timeseries arrays by running the timeseries demo adder twice
3. **[Multi step orchestration with Miner's Rule Analytic](multiStepOrchestration):** An orchestration that runs a Miner's rule operation on pre-processed output from a timeseries demo adder analytic


For more information on running orchestrations in Predix Analytics, see [Using the Analytics Runtime Service](https://www.predix.io/docs/#pM5fe0l) on Predix IO. 