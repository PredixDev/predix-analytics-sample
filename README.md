# Predix Analytics Samples

A collection of samples for use with [Predix Analytics](https://www.predix.io/docs/#EG3xVdLg) services.

## Sample Analytics

These analytics are implementations of the same logic (adding 2 numbers together and returning the result) developed in different languages.

1. **[demo-adder-java](demo-adder-java)** - sample adder analytic in Java
2. **[demo-adder-matlab](demo-adder-matlab)** - sample adder analytic in Matlab 
3. **[demo-adder-py](demo-adder-py)** - sample adder analytic in Python 

The expected JSON input data format is as follows:

`{"number1": 123, "number2": 456}`

## R analytic
1. **[r-demo-analytic](r-demo-analytic)** - sample Linear Regression analytic in R

The expected JSON input data format is as follows:

`{"x": [4,4,7,7,8,9,10,10,10,11,11,12,12,12,12,13,13,13,13,14,14,14,14,15,15,15,16,16,17,17,17,18,18,18,18,19,19,19,20,20,20,20,20,22,23,24,24,24,24,25],"y": [2,10,4,22,16,10,18,26,34,17,28,14,20,24,28,26,34,34,46,26,36,60,80,20,26,54,32,40,32,40,50,42,56,76,84,36,46,68,32,48,52,56,64,66,54,70,92,93,120,85]}`

For more information on developing analytics for use with Predix Analytics, see [Analytic Development](https://www.predix.io/docs/#Qd2kPYb7) on Predix IO. 

## Sample Orchestration Workflows

**[Sample Orchestration Workflows](orchestrations)**

For more information on running orchestrations in Predix Analytics, see [Using the Analytics Runtime Service](https://www.predix.io/docs/#pM5fe0l) on Predix IO.

## Postman Collections

**[Sample Postman Collections](postman)**

Once you have created your instance of either the Analytics Catalog or Analytics Runtime service, you can use the sample Postman collections to customize your REST requests and test them out to aid in implementing your applications. 
