#demo-adder-java

A java-based sample analytic for Predix Analytics.

## Compiled binaries
Refer to the Releases page for compiled binaries you can upload directly to Predix Analytics.

## Pre-requisites
To build and run this analytic, you will need to have the following:
- JDK 1.7+
- Maven 3+

## Building, deploying and running the analytic
1. From the demo-analytics/demo-adder-java directory, run the `mvn clean install` command to build and perform the component test.
2. Create an analytic in Analytics Catalog with the name "Demo Adder Java" and the version "v1".
3. Upload the jar file demo-adder-java-1.0.0-SNAPSHOT.jar from the demo-adder-java/target directory and attach it to the created analytic entry.
4. Deploy and test the analytic on Predix Analytics platform.

## Input format
The expected JSON input data format is as follows:
`{"number1": 123, "number2": 456}`

## Output format
The JSON output format from the analytic is as follows:
`{"result":579}`

## Testing the analytic locally
1. From the demo-analytics/demo-adder-java directory, run the `mvn spring-boot:run` command to run the analytic locally.
2. The analytic should come up using the TCP port defined in demo-analytics/demo-adder-java/src/main/resource/application.properties file.
3. Using Chrome POSTMAN or curl, POST the REST request to http://localhost:9090/api/v1/demo_adder_java/execution. Set "Accept" header to "application/json". Set "Content-Type" header to "application/json". Specify the request body as defined in "Input format" section.

## Developing a java-based analytic
Define the REST API with the URL of api/{version}/{analytic_name}/execution where
- "api" is the URL prefix expected by Predix Analytics
- {version} is based on the version of the analytic to be defined in the Analytic Catalog
- {analytic_name} is based on the name of the analytic to be defined in the Analytic Catalog
- "execution" is the REST resource name expected by Predix Analytics


Note: The version of the analytic to be included in the URL should match that defined in the Analytic Catalog with the following modifications

- Substitute dots '.' with underscores '_'
- Convert all letters to lower case


Note: The name of the analytic to be included in the URL should match that defined in the Analytic Catalog with the following modifications

- Substitute spaces ' ' with underscores '_'
- Convert all letters to lower case


For example, suppose you defined the following analytic in the analytic catalog

- Name: Anomaly Detection
- Version: v1.0

Then define the REST URL as api/v1_0/anomaly_detection/execution


For more information, see "Analytic Development" in the Predix Analytics Services documentation on Predix IO.