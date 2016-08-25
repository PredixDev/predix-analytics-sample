#demo-timeseries-adder-with-model-java

A java-based sample analytic that adds two timeseries arrays using a trained model for the Predix Analytics platform.

## Compiled binaries
Refer to the [Releases](https://github.com/PredixDev/predix-analytics-sample/releases) page for compiled binaries you can upload directly to Predix Analytics.

## Pre-requisites
To build and run this analytic, you will need to have the following:

- JDK 1.7+
- Maven 3+

## Building, deploying and running the analytic
1. From the demo-timeseries-adder-java directory, run the `mvn clean package` command to build and perform the component test.
2. Create an analytic in Analytics Catalog with the name "Demo Timeseries Adder Java", the supported language "Java" and the version "v1".
3. Upload the jar file demo-timeseries-adder-with-model-java-1.0.0.jar from the demo-timeseries-adder-with-model-java/target directory and attach it to the created analytic entry.
4. Deploy and test the analytic on Predix Analytics platform.

## Analytic template
This analytic takes in 2 timeseries arrays and returns their sum, augmented by a "threshold" model. This structure is outlined in this [analytic template](../demo-timeseries-adder-with-model-template.json).

## Input format
The expected JSON input data format is as follows:
```json
{
  "data": {
    "time_series": {
      "numberArray1": [
        1.0,
        2.0,
        3.0
      ],
      "numberArray2": [
        100.0,
        200.0,
        300.0
      ],
      "time_stamp": [
        "1455733669601",
        "1455733669602",
        "1455733669603"
      ]
    }
  }
}
```

## Output format
The JSON output format from the analytic is as follows:
```json
{
  "data": {
    "time_series": {
      "time_stamp": [
        "1455733669601",
        "1455733669602",
        "1455733669603"
      ],
      "sum": [
        101.0,
        202.0,
        303.0
      ]
    }
  }
}
```

## Developing a Java-based analytic
1. Implement the analytic (and test functions) according to your development guidelines.
2. Create an entry-point method.  The entry method signature must be in one of the following two formats:
 * For analytics that do not use trained models, use the following signature for your entry method:
  `public String entry_method(String inputJson)`
 * For analytics that use trained models, use the following signature for your entry method:
  `public String entry_method(String inputJson, Map<String, byte[]> inputModels)`
 * In either case, the `entry_method` can be any method name. `inputJson` is the JSON string input that will be passed to the analytic. The output of this method must also be a JSON string.
 * `inputModels` contains a map of trained models as defined in the port-to-field map. The entry method should properly handle the case of an empty map.
3. Create the JSON configuration file `src/main/resources/config.json` containing the className and MethodName definitions that instruct the generated wrapper code to call your designated entry point method with the request payload.
4. Build and prepare the analytic jar file including `config.json` file and dependent jar files. See [sample pom.xml](pom.xml) for reference.

In this example, the entry-point is `add2NumberArrays` in the [DemoJavaAdderWithTimeseriesEntryPoint](src/main/java/com/ge/predix/analytics/demo/java/DemoJavaAdderWithTimeseriesEntryPoint.java ) class.
[config.json](src/main/resources/config.json) properly maps the entry point to the `add2NumberArrays` method of the `DemoJavaAdderWithTimeseriesEntryPoint` class.
This method takes in a JSON String, parses the JSON (see line 35-48), performs the computation (see line 50-57), and returns the result as a new JSON String (see line 59-69).

## Deploying the analytic to the Predix Cloud
Create an analytic entry and upload the analytic jar file as 'executable' artifact in Predix Analytics Catalog. Predix Analytics Catalog service will generate code necessary to run the analytic as a service on Predix platform. The analytic service exposes a service endpoint via a URI derived from the analytic name and version.  When the analytic service endpoint is invoked with a request payload, the entry-point method designated in `config.json` will be called with request payload as a parameter.


