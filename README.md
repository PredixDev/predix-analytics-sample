# Predix Analytics Samples

A collection of samples for use with [Predix Analytics](https://www.predix.io/docs/#EG3xVdLg) services.

## Sample Analytics

These are sample analytics written for use with Predix Analytics:

- **[demo-adder](analytics/demo-adder):** A simple analytic that takes 2 numbers as input and returns their sum. It has been implemented in Java, Matlab (r2011b), and Python.
- **[demo-timeseries-adder](analytics/demo-timeseries-adder-java):** Takes 2 arrays of timeseries data and returns a timeseries array that contains the sums at each timestamp. Currently available in Java.
- **[demo-timeseries-adder-with-model](analytics/demo-timeseries-adder-with-model):** Takes 2 arrays of timeseries data and returns a timeseries array that contains the sums at each timestamp, adjusted by a threshold value provided in a trained model. Currently available in Java and Python.
- **[demo-RTM-loco](analytics/demo-RTM-loco):** A reference analytic that is used to calculate locomotive efficiency using a linear regression model. It has been implemented in Java, Matlab (r2011b), and Python.
- **[miners-rule](analytics/miners-rule):** A sample analytic that performs a Miner's Rule operation on 2 timeseries arrays and returns a timeseries array. Currently only available in Java.

For more information on developing analytics for use with Predix Analytics, see [Analytic Development](https://www.predix.io/docs/#Qd2kPYb7) on Predix IO. 

## Sample Orchestration Workflows

**[Sample Orchestration Workflows](orchestrations)**

For more information on running orchestrations in Predix Analytics, see [Using the Analytics Runtime Service](https://www.predix.io/docs/#pM5fe0l) on Predix IO.

## Postman Collections

**[Sample Postman Collections](postman)**

## Custom Data Connector

**[Sample Custom Data Connectors](custom-data-connector)

These are sample custom data connector implementations to connect to various data sources

Once you have created your instance of either the Analytics Catalog or Analytics Runtime service, you can use the sample Postman collections to customize your REST requests and test them out to aid in implementing your applications. 
