
# Simple Linear Regression


A Python3-based sample analytic performing simple linear regression computation which can be deployed to Predix Analytics platform.
## Pre-requisites

To run this analytic locally, you will need to have the following:

- Python 3.6+
- scipy 0.19.1

## Running unit tests

```bash
    $ cd <location>/simple-linear-regression/analytics
    $ python -m unittest test_simple_linear_regression.py
    
```

## Building, deploying and running the analytic

1. Zip the contents of this directory or if you have Maven3 installed and configured, do a `mvn clean install` to generate the `py3-simple-linear-regression-0.1-bin.zip` in the `target` folder.
2. Create an analytic in Analytics Catalog with the name "simple-linregress", the version "v1" and the supported language to "PYTHON_3".
3. Upload the zip file and attach it to the created analytic.
4. Deploy and test the analytic on Predix Analytics platform.

## Analytic template
This analytic takes in 2 arrays and returns the p-value, r-value, slope, intercept and standard error. This structure is outlined in this [analytic template](simple_linear_regression_template.json).

### Input format

The expected JSON input data format is as follows

```json
{ 
    "y": [19.0, 20.3, 20.5, 21.5, 22.45, 23.0, 23.0, 25.5, 24.0],
    "x": [1, 2, 3, 4, 5, 6, 7, 8, 9]
}
```

### Output format
The JSON output format from the analytic is as follows:

```json
{
  "slope": 0.7166666666666667,
  "intercept": 18.472222222222225,
  "r_value": 0.9559490311973318,
  "p_value": 5.6576124923241295e-05,
  "std_err": 0.08317445171439007
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

For more information on developing analytics for use with the Predix Analytics platform, please visit the **[Analytic Development](https://docs.predix.io/en-US/content/service/analytics_services/analytics_framework/analytic-development)** section of the Predix Analytics Services documentation on predix.io. 


