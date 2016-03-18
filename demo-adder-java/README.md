#demo-adder-java

A java-based sample analytic for the Predix Analytics platform.

## Compiled binaries
Refer to the [Releases](https://github.com/PredixDev/predix-analytics-sample/releases) page for compiled binaries you can upload directly to Predix Analytics.

## Pre-requisites
To build and run this analytic, you will need to have the following:

- JDK 1.7+
- Maven 3+

## Building, deploying and running the analytic
1. From the demo-analytics/demo-adder-java directory, run the `mvn clean package` command to build and perform the component test.
2. Create an analytic in Analytics Catalog with the name "Demo Adder Java" and the version "v1".
3. Upload the jar file demo-adder-java-1.0.0.jar from the demo-adder-java/target directory and attach it to the created analytic entry.
4. Deploy and test the analytic on Predix Analytics platform.

## Input format
The expected JSON input data format is as follows:
`{"number1": 123, "number2": 456}`

## Output format
The JSON output format from the analytic is as follows:
`{"result":579}`

## Developing a java-based analytic
1. Create an entry-point method which takes in the input data as a String and returns the output as a String.
2. Create the JSON configuration file `src/main/resources/config.json` containing the className and MethodName definitions that instruct the generated wrapper code to call your designated entry point method with the request payload.

In this example, the entry-point is `add2Numbers` in the [DemoAdderJavaEntryPoint](src/main/java/com/ge/predix/analytics/demo/java/DemoAdderJavaEntryPoint.java) class.
[config.json](src/main/resources/config.json) properly maps the entry point to the `add2Numbers` method of the `DemoAdderJavaEntryPoint` class. 
This method takes in a JSON String, maps it to a HashMap (see line 19), performs the computation, and returns the result as a new JSON String (see line 27).

## Deploying the analytic to the Predix Cloud
When you upload the jar file as an 'Executable' artifact the platform wraps the executable as a web service exposing the analytic via a URI derived from the analytic name. 
Requests made to this generated URI will be passed to the entry point method.



For more information, see [Java Analytic Development](https://www.predix.io/docs#S1Wl9PHG) in the Predix Analytics Services documentation on Predix IO.