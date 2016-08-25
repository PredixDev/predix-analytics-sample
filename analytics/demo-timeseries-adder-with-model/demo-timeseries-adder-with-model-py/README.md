#demo-timeseries-adder-with-model-py

A python-based sample analytic that adds two timeseries arrays using a trained model for the Predix Analytics platform.

## Compiled binaries
Refer to the [Releases](https://github.com/PredixDev/predix-analytics-sample/releases) page for compiled binaries you can upload directly to Predix Analytics.

## Pre-requisites
To run this analytic locally, you will need to have the following:

- Python 2.7+
- Flask 0.10+

## Building, deploying and running the analytic
1. Zip the contents of this directory, or just get the latest demo-adder-py binary from the [Releases](https://github.com/PredixDev/predix-analytics-sample/releases) page.
2. Create an analytic in Analytics Catalog with the name "demo-timeseries-adder-with-model-py" and the version "v1".
3. Upload the zip file and attach it to the created analytic.
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

## Developing a Python-based analytic
1. Implement the analytic (and test functions) according to your development guidelines.
2. Create an entry method in your analytic class. The entry method signature must be in one of the following two formats:
 * For analytics that do not use trained models, use the following signature for your entry method:
  `def entry_method(self, inputJson):`
 * For analytics that use trained models, use the following signature for your entry method:
  `def entry_method(self, inputJson, inputModels):`
 * In either case, the `entry_method` can be any method name. `inputJson` is the JSON string input that will be passed to the analytic. The output of this method must also be a JSON string.
 * `inputModels` contains a dict() of trained models as defined in the port-to-field map. The entry method should properly handle the case of an empty dict.
3. Create a config.json file in the top level of the project directory. Specify the entry method in the format of `<subdirectory>.<className>.<methodName>`, conda-libs, and non-conda-libs.
4. Package all the analytic files and the config.json file into a ZIP file.

For more information, see [Python Analytic Development](https://www.predix.io/docs#alaepr9P) in the Predix Analytics Services documentation on Predix IO.
