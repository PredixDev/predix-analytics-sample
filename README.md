# Predix Analytics Samples

A collection of samples for use with Predix Analytics services.

## Sample Analytics

These analytics are implementations of the same logic (adding 2 numbers together and returning the result) developed in different languages.

1. **[demo-adder-java](demo-adder-java)** - sample adder analytic in Java
2. **[demo-adder-matlab](demo-adder-matlab)** - sample adder analytic in Matlab 
3. **[demo-adder-py](demo-adder-py)** - sample adder analytic in Python 

The expected JSON input data format is as follows:

`{"number1": 123, "number2": 456}`

For more information on developing analytics for use with Predix Analytics, see "Analytic Development" in the Predix Analytics Services documentation on Predix IO. 

## Sample Orchestration Workflows

**[Sample Orchestration Workflows](orchestrations)**

For more information on running orchestrations in Predix Analytics, see "Using the Analytics Runtime Service" in the Predix Analytics Services documentation on Predix IO.

## Postman Collections

**[Sample Postman Collections](postman)**

Once you have created your instance of either the Analytics Catalog or Analytics Runtime service, you can use the sample Postman collections to customize your REST requests and test them out to aid in implementing your applications. 