# Predix Analytics Samples

A collection of samples for use with [Predix Analytics](https://docs.predix.io/en-US/content/service/analytics_services/analytics_framework/) services.

## Sample Analytics

These are sample analytics written for use with Predix Analytics:

- **[demo-adder](analytics/demo-adder):** A simple analytic that takes 2 numbers as input and returns their sum. It has been implemented in Java, Matlab (r2011b), and Python.
- **[demo-timeseries-adder](analytics/demo-timeseries-adder-java):** Takes 2 arrays of timeseries data and returns a timeseries array that contains the sums at each timestamp. Currently available in Java.
- **[demo-timeseries-adder-with-model](analytics/demo-timeseries-adder-with-model):** Takes 2 arrays of timeseries data and returns a timeseries array that contains the sums at each timestamp, adjusted by a threshold value provided in a trained model. Currently available in Java and Python.
- **[demo-RTM-loco](analytics/demo-RTM-loco):** A reference analytic that is used to calculate locomotive efficiency using a linear regression model. It has been implemented in Java, Matlab (r2011b), and Python.
- **[miners-rule](analytics/miners-rule):** A sample analytic that performs a Miner's Rule operation on 2 timeseries arrays and returns a timeseries array. Currently only available in Java.

For more information on developing analytics for use with Predix Analytics, see [Analytic Development](https://docs.predix.io/en-US/content/service/analytics_services/analytics_framework/analytic-development) on Predix IO. 

## Sample Orchestration Workflows

**[Sample Orchestration Workflows](orchestrations)**

You can find more information on [configuring](https://docs.predix.io/en-US/content/service/analytics_services/analytics_framework/orchestration-configuration) and [running](https://docs.predix.io/en-US/content/service/analytics_services/analytics_framework/orchestration-execution) orchestrations on Predix IO.

## Postman Collections

**[Sample Postman Collections](postman)**

Once you have created your instance of either the Analytics Catalog or Analytics Runtime service, you can use the sample Postman collections to customize your REST requests and test them out to aid in implementing your applications.

## Custom Data Connector

**[Sample Custom Data Connectors](custom-data-connector)**

These are sample custom data connector implementations to connect to various data sources
